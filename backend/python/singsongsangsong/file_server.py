# -*- coding: utf-8 -*-

"""
MinIO 관리
=========

MinIO 객체 저장소와 관련된 기능을 제공합니다
"""

import os
import mimetypes
from dotenv import load_dotenv
from minio import Minio

load_dotenv()

def get_client() -> Minio:
    """MinIO client를 생성합니다
    
    Returns
    -------
    Minio
        MinIO client
    """
    return Minio(
        os.environ.get("MINIO_ENDPOINT"),
        access_key=os.environ.get("MINIO_ACCESS_KEY"),
        secret_key=os.environ.get("MINIO_SECRET_KEY"),
        secure=False
    )

def download(bucket: str, source: str, destination: str, client: Minio=None):
    """MinIO client로부터 지정한 파일을 다운로드합니다

    Parameters
    ----------
    bucket : str
        다운로드 받을 bucket의 이름
    source : str
        bucket 내 다운로드 받을 파일의 이름
    destination : str
        다운로드 시 저장할 파일 이름
    client : Minio
        MinIO client (미지정 시 생성)
    """
    if client is None:
        client = get_client()
    client.fget_object(bucket, source, destination)

def upload(source: str, bucket: str, destination: str, client: Minio=None):
    """MinIO client로 지정한 파일을 업로드합니다

    Parameters
    ----------
    source : str
        업로드할 파일 경로
    bucket : str
        업로드할 파일이 존재할 bucket의 이름
    destination : str
        bucket 내 업로드할 파일의 이름
    client : Minio
        MinIO client (미지정 시 생성)
    """
    if client is None:
        client = get_client()
    client.fput_object(
        bucket,
        destination,
        source,
        mimetypes.guess_type(source)[0]
    )

def delete(bucket: str, object_name: str, client: Minio=None):
    """MinIO client로 지정한 파일을 삭제합니다

    Parameters
    ----------
    bucket : str
        삭제할 파일이 존재하는 bucket 이름
    object_name : str
        삭제할 파일 이름
    client : Minio
        MinIO client (미지정 시 생성)
    """

    if client is None:
        client = get_client()
    client.remove_object(bucket, object_name)
