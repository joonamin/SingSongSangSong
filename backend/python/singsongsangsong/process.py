# -*- coding: utf-8 -*-

"""
곡 분석 작업 처리
================

Controller에서 받은 곡 분석 요청을 처리합니다
"""

import os
from tempfile import TemporaryDirectory
from itertools import islice
import uuid
import logging
import requests
import librosa
from essentia.standard import MonoLoader # pylint: disable=no-name-in-module
import file_server
import database
import vector_database
import s4dsp

def analyse(song_id: int, audio_path: str, audio_filename: str): # pylint: disable=too-many-locals,too-many-statements
    """곡 분석을 진행합니다

    Parameters
    ----------
    song_id : int
        곡 ID
    audio_path : str
        MinIO 내 음원 파일 경로
    audio_filename : str
        실제 음원 파일 이름

    Raises
    ------
    Exception
        내부 작업 중 오류 발생
    """
    logging.info("Starting analysis of song %s", song_id)

    try:
        # 분석 작업을 위한 임시 directory 생성
        with TemporaryDirectory() as pwd:
            path: str = pwd + "/{}"

            # MinIO client 생성
            client = file_server.get_client()

            # 음원 파일 다운로드
            os.mkdir(path.format("audio"))
            file_server.download(
                "audio",
                audio_path,
                path.format(f"audio/{audio_filename}"),
                client
            )

            # 음원 파일 불러오기
            reference_path = path.format(f"audio/{audio_filename}")
            y, sr = librosa.load(reference_path, mono=True)
            reference_audio = MonoLoader(
                filename=reference_path,
                sampleRate=16000,
                resampleQuality=4
            )()

            # 음원 분석 0 - duration
            duration = int(y.shape[0] / sr)

            # 음원 분석 1 - tempo
            tempo = int(s4dsp.get_tempo(y, sr))

            # 음원 분석 2 - key
            key = s4dsp.find_key(y, sr)[0]["key"]

            # 음원 분석 3 - genre
            genres = s4dsp.predict_genre(reference_audio)

            # 음원 분석 4 - mood
            moods = s4dsp.predict_mood(reference_audio)

            # 음원 분석 5 - structure
            structure = [
                {
                    "start": int(time[0]),
                    "end": int(time[1]),
                    "label": label
                } for time, label in s4dsp.find_structure(reference_path).items()
            ]

            # 음원 분석 6 - 그래프 파일 생성
            mfcc_filename = str(uuid.uuid4())
            waveform_filename = str(uuid.uuid4())
            mfcc_path = path.format(f"{mfcc_filename}.svg")
            waveform_path = path.format(f"{waveform_filename}.png")

            s4dsp.plot_mfcc(librosa.feature.mfcc(y=y, sr=sr, n_mfcc=20), mfcc_path)
            s4dsp.draw_spectrum(y, sr, waveform_path, structure)

            # 음원 분석 7 - 유사도 분석을 위한 embedding 생성
            embedding = s4dsp.get_embedding(y)

            # 음원 분석 완료 - 분석 결과 저장 시도
            saved_files = []
            try:
                with database.get_connection() as connection:
                    connection.autocommit = False

                    with connection.cursor() as cursor:
                        # 아티스트 ID 받아오기
                        artist_id = database.get_song_artist(song_id, cursor)

                        # 장르 데이터 준비
                        genre_data = []
                        for genre_str, correlation in islice(genres.items(), 5):
                            [genre, subgenre] = genre_str.split("---")

                            # song_id, main_category, sub_category, correlation
                            genre_data.append((song_id, genre, subgenre, correlation * 100.0))

                        # 분위기 데이터 준비
                        mood_data = [
                            # song_id, atmosphere, correlation
                            (
                                song_id,
                                mood,
                                correlation * 100.0
                            ) for mood, correlation in islice(moods.items(), 5)
                        ]

                        # 장르, 분위기 삽입
                        cursor.executemany(
                            "insert into genre (song_id, main_category, sub_category, correlation) "
                            "values (%s, %s, %s, %s)",
                            genre_data
                        )
                        cursor.executemany(
                            "insert into atmosphere (song_id, atmosphere, correlation) "
                            "values (%s, %s, %s)",
                            mood_data
                        )

                        # 곡 구조 삽입
                        cursor.executemany(
                            "insert into structure (song_id, start_time, end_time, label) "
                            "values (%s, %s, %s, %s)",
                            [
                                (
                                    song_id,
                                    entry["start"],
                                    entry["end"],
                                    entry["label"]
                                ) for entry in structure
                            ]
                        )

                        # 파일 데이터 업로드
                        file_server.upload(mfcc_path, "image", mfcc_filename, client)
                        saved_files.append(mfcc_filename)
                        cursor.execute(
                            "insert into file (owner_id, saved_file_name, original_file_name) "
                            "values (%s, %s, %s)",
                            (artist_id, mfcc_filename, f"mfcc-{song_id}.svg")
                        )
                        mfcc_insert_id = cursor.lastrowid

                        file_server.upload(waveform_path, "image", waveform_filename, client)
                        saved_files.append(waveform_filename)
                        cursor.execute(
                            "insert into file (owner_id, saved_file_name, original_file_name) "
                            "values (%s, %s, %s)",
                            (artist_id, waveform_filename, f"spectrum-{song_id}.png")
                        )
                        waveform_insert_id = cursor.lastrowid

                        # 나머지 분석 데이터 삽입
                        cursor.execute(
                            "update song "
                            "set duration = %s, bpm = %s, chord = %s, "
                            "mfcc_image_id = %s, spectrum_image_id = %s "
                            "where id = %s",
                            (
                                duration,
                                tempo,
                                key,
                                mfcc_insert_id,
                                waveform_insert_id,
                                song_id
                            )
                        )

                        # 유사도 비교 데이터 삽입
                        vector_database.insert_embedding(song_id, embedding)

                        connection.commit()
            except Exception as exception:
                for saved in saved_files:
                    file_server.delete("image", saved)

                raise exception

            # 작업 완료 - API 서버에 처리 완료 통보
            requests.patch(
                f"{os.environ.get('API_SERVER_URI')}/analyze/song/{song_id}",
                timeout=10
            )
    except Exception as exception: # pylint: disable=broad-exception-caught
        logging.error("Error analyzing song %s", song_id, exc_info=exception)

    logging.info("Analysis of song %s finished", song_id)
