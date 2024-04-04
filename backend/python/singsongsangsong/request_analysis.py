# -*- coding: utf-8 -*-

from dotenv import load_dotenv
import pandas as pd
import vector_database
import process

# 곡 분석 시 시작 및 종료할 ID를 명시합니다
SONG_FROM = 48
SONG_UNTIL = 137

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
    usecols=["track_id"]
)

for index, row in tracks.iterrows():
    track_id = int(row["track_id"])
    filename = filename_from_id(track_id)

    # 지정한 ID부터 진행합니다
    if track_id < SONG_FROM:
        continue

    print(f"Requesting analysis of song {track_id}")

    # 곡 분석을 요청합니다
    process.analyse(track_id, filename, filename)

    try:
        # Milvus에 검색 가능 여부를 지정합니다
        vector_database.set_searchable(track_id)
    except ValueError as exception:
        print(exception)

    # 지정한 ID까지 진행합니다
    if track_id >= SONG_UNTIL:
        break
