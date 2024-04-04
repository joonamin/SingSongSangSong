# -*- coding: utf-8 -*-

"""
외부 의존 파일 관리
=================

`s4dsp._dependencies`는 이 프로젝트가 외부로부터 의존하는 파일을 관리합니다

Utility functions
-----------------
`require`
    프로그램 작동에 필요한 파일을 명시합니다
"""

import os.path
import hashlib
from hmac import compare_digest
import shutil
import requests

def require(path: str, url: str=None, sha256sum: str=None):
    """프로그램 작동에 필요한 파일을 명시합니다

    지정한 경로에 파일이 존재하는 지 확인하고 그렇지 않을 시 다운로드를 진행합니다

    Parameters
    ----------
    path : str
        필요한 파일의 경로
    url : str|None
        파일이 존재하지 않을 시 내려받을 경로
    sha256sum : str|None
        파일 무결성을 확인하기 위한 SHA256 해시

    Raises
    -------
    FileNotFoundError
        파일과 URL이 모두 존재하지 않는 경우
    RuntimeError
        Checksum이 일치하지 않는 경우
    """

    if not os.path.exists(path):
        if url is None:
            raise FileNotFoundError(f"File {path} does not exist")

        response = requests.get(url, stream=True, timeout=(10, 82))

        with open(path, "wb") as file:
            shutil.copyfileobj(response.raw, file)

    if sha256sum is not None:
        with open(path, "rb") as file:
            h = hashlib.sha256(file.read())

            if not compare_digest(h.hexdigest(), sha256sum):
                raise RuntimeError(f"Checksum for file {path} does not match")
