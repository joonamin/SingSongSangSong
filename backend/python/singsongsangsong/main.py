# -*- coding: utf-8 -*-

"""
싱송생송 DSP API
===============
"""

from typing import Annotated
import os
from dotenv import load_dotenv
from pydantic import BaseModel
from fastapi import (
    BackgroundTasks,
    FastAPI,
    Path,
    Query,
    Response,
    status
)
from fastapi.middleware.cors import CORSMiddleware
from minio.error import S3Error
import uvicorn
import file_server
import database
import vector_database
from process import analyse

class SimilarityResult(BaseModel):
    """유사도 검색 결과 model입니다
    
    Attributes
    ----------
    id : int
        곡 ID
    distance : float
        데이터베이스 내 거리 계산 결과
    """

    id: int
    distance: float

class SimilarityResultResponse(BaseModel):
    """유사도 검색 결과 응답 model입니다

    Attributes
    ----------
    data : list[SimilarityResult]
        유사도 검색 결과
    """

    data: list[SimilarityResult]

load_dotenv()

app = FastAPI(
    title="싱송생송 DSP API"
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=[
        "http://localhost:3000",
        "https://www.singsongsangsong.com"
        "https://api.singsongsangsong.com"
    ],
    allow_methods=["GET", "POST"]
)

@app.post(
    "/song/{id}",
    summary="곡 분석 요청",
    response_model=None,
    responses={
        202: {
            "description": "요청 확인, 분석 진행 예정",
            "content": None
        },
        404: { "description": "존재하지 않는 `id` 또는 파일 정보가 올바르지 않은 곡의 `id` 제공" },
        422: { "description": "올바르지 않은 형태의 `id` 제공" },
        500: { "description": "예상치 못한 오류 발생" }
    },
    status_code=202
)
def request_song_analysis(
    song_id: Annotated[
        int,
        Path(
            alias="id",
            description="분석할 곡 ID"
        )
    ],
    background_tasks: BackgroundTasks
):
    """지정한 `id`를 가진 곡에 대한 분석을 진행합니다

    분석 완료 후 데이터베이스에 분석 결과를 저장한 뒤 API 서버의 callback endpoint에 분석 완료를 알립니다
    """

    # 데이터베이스에서 파일 정보 얻어오기
    filename = database.get_filename(song_id)

    # 존재하지 않을 시 HTTP 404 응답
    if filename is None:
        return Response(status_code=status.HTTP_404_NOT_FOUND)

    original, saved = filename

    # 음원이 파일 서버에 존재하는지 확인
    client = file_server.get_client()
    try:
        client.stat_object("audio", saved)
    except S3Error:
        # 존재하지 않을 시 HTTP 404 응답
        return Response(status_code=status.HTTP_404_NOT_FOUND)

    background_tasks.add_task(analyse, song_id, saved, original)
    return Response(status_code=status.HTTP_202_ACCEPTED)

@app.post(
    "/similarity",
    summary="곡 유사도 정보 저장",
    response_model=None,
    responses={
        204: { "description": "데이터베이스 삽입 완료" },
        404: { "description": "존재하지 않는 `id` 제공" },
        422: { "description": "올바르지 않은 형태의 `id` 제공" },
        500: { "description": "예상치 못한 오류 발생" }
    },
    status_code=204
)
def save_similarity_data(
    song_id: Annotated[
        int,
        Query(
            alias="id",
            description="유사도 비교를 위한 곡 ID"
        )
    ]
):
    """지정한 `id`를 가진 곡의 정보를 유사도 비교를 위한 데이터베이스에 저장합니다"""

    try:
        vector_database.set_searchable(song_id)
    except ValueError:
        return Response(status_code=status.HTTP_404_NOT_FOUND)

    return Response(
        status_code=status.HTTP_204_NO_CONTENT,
        headers={
            "Location": f"{os.environ.get('BASE_URI')}/similarity/{song_id}"
        }
    )

@app.get(
    "/similarity/{id}",
    summary="곡 유사도 조회",
    response_model=SimilarityResultResponse,
    responses={
        200: { "description": "유사도 조회 완료" },
        404: { "description": "존재하지 않는 `id` 제공" },
        422: { "description": "올바르지 않은 형태의 `id` 제공" },
        500: { "description": "예상치 못한 오류 발생" }
    },
    status_code=200
)
def check_similarity(
    song_id: Annotated[
        int,
        Path(
            alias="id",
            description="조회할 곡 ID"
        )
    ]
):
    """지정한 id의 곡에 대한 유사도 정보를 조회합니다"""

    try:
        return { "data": vector_database.search_similarity(song_id) }
    except ValueError:
        return Response(status_code=status.HTTP_404_NOT_FOUND)

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
