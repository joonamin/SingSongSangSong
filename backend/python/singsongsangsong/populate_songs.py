# -*- coding: utf-8 -*-

import itertools
import os
import mimetypes
import shutil
import uuid
import requests
from requests.exceptions import HTTPError
from dotenv import load_dotenv
import pandas as pd
import database
import file_server

# 시험 목적으로 실행합니다
# (완료 시 실행 내용을 되돌립니다)
DRY_RUN = False

# 데이터베이스 삽입 시 시작 및 종료할 ID를 명시합니다
SONG_FROM = 1
SONG_UNTIL = 999
FILE_START_FROM = 7638

def download(url: str, path: str):
    """URL로부터 지정한 파일을 `path`에 저장합니다

    Parameters
    ----------
    url : str
        URL
    path : str
        저장할 파일의 경로
    """

    request = requests.get(url, stream=True, timeout=8)
    request.raise_for_status()

    with open(path, "wb") as file:
        shutil.copyfileobj(request.raw, file)

def update_image_url(url: str) -> str:
    """FMA의 이미지 URL을 현재 형식으로 변경합니다

    https://github.com/mdeff/fma/issues/51

    Parameters
    ----------
    url : str
        이전 형식의 URL

    Returns
    -------
    str
        현재 형식의 URL
    """
    old_prefix = "https://freemusicarchive.org/file/"
    new_prefix = "https://freemusicarchive.org/image?file="
    new_suffix = "&width=290&height=290&type=image"

    assert url.startswith(old_prefix)

    return f"{new_prefix}{url[len(old_prefix):]}{new_suffix}"

def filename_from_id(song_id: int) -> str:
    """곡 ID를 이용하여 파일 이름을 생성합니다

    Parameters
    ----------
    song_id : int
        곡 ID

    Returns
    -------
    str
        파일 이름
    """

    assert 0 <= song_id < 1000000
    return f"{song_id:06}.mp3"

# .env를 읽습니다
load_dotenv()

# 삽입할 곡의 정보를 읽습니다
tracks = pd.read_csv(
    "dataset/fma/data/fma_metadata/raw_tracks.csv",
    skipinitialspace=True,
    usecols=[
        "track_id",
        "artist_id",
        "track_date_created",
        "track_image_file",
        "track_information",
        "track_title"
    ]
)

insert = []
image_insert = []
audio_insert = []
file_insert_id = itertools.count(FILE_START_FROM)

for index, row in tracks.iterrows():
    track_id = int(row["track_id"])

    if track_id < SONG_FROM:
        continue

    # 음원 파일 설정을 준비합니다
    audio_filename = filename_from_id(track_id)
    audio_path = os.path.join(
        "dataset",
        "fma",
        "data",
        "fma_full",
        audio_filename[:3],
        audio_filename
    )

    if not os.path.exists(audio_path):
        continue

    # 앨범 이미지를 다운로드하기 전 URL을 정리합니다
    image_url = str(row["track_image_file"]) # pylint: disable=invalid-name

    image_filename = image_url[
        len("https://freemusicarchive.org/file/images/albums/"):
    ] if image_url.startswith(
        "https://freemusicarchive.org/file/images/albums/"
    ) else None
    image_url = update_image_url(
        image_url
    ) if image_url.startswith(
        "https://freemusicarchive.org/file/images/albums/"
    ) else None

    # 유효한 이미지가 주어진 경우 다운로드를 진행합니다
    if image_filename is not None:
        image_path = os.path.join("dataset", "images", "albums", image_filename)
        image_extension = os.path.splitext(image_path)[1]

        try:
            # 파일이 존재하지 않을 시에만 진행합니다
            if not os.path.exists(image_path) and image_url is not None:
                print(f"Downloading from {image_url}")
                download(image_url, image_path)
            else:
                print(f"Using downloaded {image_filename}")

            assert mimetypes.guess_type(image_path)[0].startswith("image/")

            # file entity에 삽입할 데이터를 준비합니다
            file_id = next(file_insert_id)
            image_insert.append(
                {
                    "id": file_id,
                    "owner_id": row["artist_id"],
                    "saved_file_name": str(uuid.uuid4()),
                    "original_file_name": f"{str(uuid.uuid4())}{image_extension}",
                    "file_path": image_path
                }
            )
        except HTTPError:
            print(f"  Downloading {image_filename} failed")
            file_id = None # pylint: disable=invalid-name
    else:
        file_id = None # pylint: disable=invalid-name

    # 음원 정보 데이터를 준비합니다
    audio_insert.append(
        {
            "id": next(file_insert_id),
            "owner_id": row["artist_id"],
            "saved_file_name": audio_filename,
            "original_file_name": audio_filename,
            "file_path": audio_path
        }
    )

    # 삽입할 데이터를 준비합니다
    insert.append(
        {
            "id": track_id,
            "artist_id": row["artist_id"],
            "album_image_id": file_id,
            "created_date": row["track_date_created"],
            "modified_date": row["track_date_created"],
            "music_file_name": audio_filename,
            "song_description": str(row["track_information"]),
            "title": str(row["track_title"])
        }
    )

    # 시험 목적으로 실행 시 일부 데이터만 가져옵니다
    if track_id >= SONG_UNTIL:
        break

# MinIO client를 준비합니다
minio_client = file_server.get_client()
saved_images = []
saved_audio = []

# MySQL (MariaDB) 데이터베이스에 연결합니다
try:
    with database.get_connection() as connection:
        with connection.cursor() as cursor:
            connection.autocommit = False
            cursor.execute("set @@session.sql_mode = ''")

            print("Inserting image records")

            # 이미지 파일 정보를 삽입합니다
            cursor.executemany(
                "insert into file "
                "(id, owner_id, saved_file_name, original_file_name) "
                "values (%s, %s, %s, %s)",
                [
                    (
                        row["id"],
                        row["owner_id"],
                        row["saved_file_name"],
                        row["original_file_name"]
                    ) for row in image_insert
                ]
            )

            print("Inserting audio records")

            # 음원 파일 정보를 삽입합니다
            cursor.executemany(
                "insert into file "
                "(id, owner_id, saved_file_name, original_file_name) "
                "values (%s, %s, %s, %s)",
                [
                    (
                        row["id"],
                        row["owner_id"],
                        row["saved_file_name"],
                        row["original_file_name"]
                    ) for row in audio_insert
                ]
            )

            print("Inserting song records")

            # 아티스트 정보를 삽입합니다
            cursor.executemany(
                "insert into song "
                "(bpm, download_count, duration, like_count, play_count, "
                "weekly_download_count, weekly_like_count, weekly_play_count, "
                "album_image_id, artist_id, created_date, id, modified_date, "
                "music_file_name, song_description, title) "
                "values "
                "(0, 0, 0, 0, 0, "
                "0, 0, 0, "
                "%s, %s, %s, %s, %s, "
                "%s, %s, %s)",
                [
                    (
                        row["album_image_id"],
                        row["artist_id"],
                        row["created_date"],
                        row["id"],
                        row["modified_date"],
                        row["music_file_name"],
                        row["song_description"],
                        row["title"]
                    ) for row in insert
                ]
            )

            print("Updating artists' profile images")

            # 이미지 파일을 client에 업로드합니다
            for image_file in image_insert:
                print(f"Uploading {image_file['file_path']} ({image_file['saved_file_name']})")

                file_server.upload(
                    image_file["file_path"],
                    "image",
                    image_file["saved_file_name"],
                    minio_client
                )
                saved_images.append(image_file["saved_file_name"])

            print("Uploading audio")

            # 음원 파일을 client에 업로드합니다
            for audio_file in audio_insert:
                print(f"Uploading {audio_file['file_path']} ({audio_file['saved_file_name']})")

                file_server.upload(
                    audio_file["file_path"],
                    "audio",
                    audio_file["saved_file_name"],
                    minio_client
                )
                saved_audio.append(audio_file["saved_file_name"])

            # NaN 값을 NULL로 변경합니다
            # (더 좋은 방법이 있을 것 같으나 시간 문제로 진행하지 않습니다)
            cursor.execute(
                "update song set song_description = null where song_description = 'nan'"
            )

            # 시험 목적으로 사용 시 commit을 하지 않습니다
            if not DRY_RUN:
                connection.commit()

except Exception as exception: # pylint: disable=broad-exception-caught
    for image_file in saved_images:
        file_server.delete("image", image_file, minio_client)
    for audio_file in saved_audio:
        file_server.delete("audio", audio_file, minio_client)

    raise exception

# 시험 목적으로 사용 시 업로드한 이미지를 삭제합니다
if DRY_RUN:
    for image_file in saved_images:
        file_server.delete("image", image_file, minio_client)
    for audio_file in saved_audio:
        file_server.delete("audio", audio_file, minio_client)
