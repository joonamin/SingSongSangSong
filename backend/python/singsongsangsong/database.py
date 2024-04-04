# -*- coding: utf-8 -*-

"""
데이터베이스 관리
================

MySQL (MariaDB) 데이터베이스와 관련된 기능을 제공합니다
"""

import os
from typing import Union
from dotenv import load_dotenv
from mysql import connector
from mysql.connector.abstracts import MySQLConnectionAbstract
from mysql.connector.pooling import PooledMySQLConnection
from mysql.connector.cursor import MySQLCursorAbstract

load_dotenv()

def get_connection() -> Union[PooledMySQLConnection, MySQLConnectionAbstract]:
    """MySQL (MariaDB)에 연결합니다

    Returns
    -------
    PooledMySQLConnection | MySQLConnectionAbstract
        데이터베이스 연결 객체
    """

    return connector.connect(
        host=os.environ.get("MYSQL_HOST"),
        port=os.environ.get("MYSQL_PORT"),
        user=os.environ.get("MYSQL_USER"),
        password=os.environ.get("MYSQL_PASSWORD"),
        database=os.environ.get("MYSQL_DATABASE")
    )

def get_song_path(song_id: int, cursor: MySQLCursorAbstract=None) -> Union[str, None]:
    """지정한 `song_id`의 ID를 가진 곡의 음원 파일 경로를 반환합니다

    Returns
    -------
    str | None
        음원 파일 경로; 없을 시 `None`
    """

    if cursor is None:
        connection = get_connection()
        cursor = connection.cursor()

    cursor.execute(
        "select id, music_location from song where id = %s",
        (song_id,)
    )

    for (selected_id, music_location) in cursor:
        if song_id == selected_id:
            return music_location

    return None

def get_song_artist(song_id: int, cursor: MySQLCursorAbstract=None) -> Union[str, None]:
    """지정한 `song_id`의 ID를 가진 곡의 아티스트 ID를 반환합니다

    Returns
    -------
    int | None
        아티스트 ID; 없을 시 `None`
    """

    if cursor is None:
        connection = get_connection()
        cursor = connection.cursor()

    cursor.execute(
        "select id, artist_id from song where id = %s",
        (song_id,)
    )

    for (selected_id, artist_id) in cursor:
        if song_id == selected_id:
            return artist_id

    return None

def get_filename(song_id: int, cursor: MySQLCursorAbstract=None) -> Union[tuple[str, str], None]:
    """지정한 `song_id`의 곡이 가진 파일 이름과 MinIO 저장소에서 가진 `saved_file_name`을 반환합니다

    Parameters
    ----------
    song_id : int
        곡 ID
    cursor : MySQLCursorAbstract | None
        MySQL cursor 객체; 없을 시 생성

    Returns
    -------
    tuple[str, str] | None
        저장되어 있는 파일 이름; 없을 시 `None`
    """

    if cursor is None:
        connection = get_connection()
        cursor = connection.cursor()

    cursor.execute(
        "select music_file_name from song where id = %s",
        (song_id,)
    )
    for (original_file_name,) in cursor:
        cursor.execute(
            "select saved_file_name from file where original_file_name = %s",
            (original_file_name,)
        )
        for (saved_file_name,) in cursor:
            return (original_file_name, saved_file_name)

    return None
