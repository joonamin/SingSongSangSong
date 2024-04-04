# -*- coding: utf-8 -*-

"""
유사도 분석
==========

`s4dsp.similarity`는 음원을 유사도를 분석할 수 있는 형태로 변환합니다

Utility functions
-----------------
`get_embedding`
    PANNs를 사용하여 음원의 패턴을 확인합니다
"""

import os
import time
from dotenv import load_dotenv
import numpy as np
import librosa
from panns_inference import AudioTagging
from pymilvus import MilvusClient, DataType
from s4dsp._dependencies import require

# 유사도 분석에 필요한 파일 정보
require(
    "s4dsp/data/models/Cnn14_mAP=0.431.pth",
    url="https://zenodo.org/record/3987831/files/Cnn14_mAP%3D0.431.pth?download=1",
    sha256sum="0dc499e40e9761ef5ea061ffc77697697f277f6a960894903df3ada000e34b31"
)

def get_embedding(y: np.ndarray) -> list[float]:
    """PANNs를 사용하여 음원의 패턴을 확인합니다

    Parameters
    ----------
    y : np.ndarray
        모노 음원

    Returns
    -------
    list[float]
        embedding
    """

    source = y[None, :]

    tag = AudioTagging(checkpoint_path="s4dsp/data/models/Cnn14_mAP=0.431.pth")
    embedding = tag.inference(source)[1]

    embedding = embedding / np.linalg.norm(embedding)

    return embedding.tolist()[0]

if __name__ == "__main__":
    load_dotenv()

    # Parameters
    REFERENCE_PATH = "s4dsp/data/train/"

    client = MilvusClient(
        uri=os.environ.get("MILVUS_URI")
    )

    schema = MilvusClient.create_schema(
        enable_dynamic_field=False
    )
    schema.add_field(
        field_name="song_name",
        datatype=DataType.VARCHAR,
        max_length=128,
        is_primary=True
    )
    schema.add_field(
        field_name="vector",
        datatype=DataType.FLOAT_VECTOR,
        dim=2048
    )

    index_parameters = client.prepare_index_params()
    index_parameters.add_index(
        field_name="song_name",
        index_type="Trie"
    )
    index_parameters.add_index(
        field_name="vector",
        index_type="IVF_FLAT",
        metric_type="IP",
        params={ "nlist": 16384 }
    )

    client.create_collection(
        collection_name="similarity_test",
        schema=schema,
        index_params=index_parameters
    )

    print(client.get_load_state(collection_name="similarity_test"))

    entities = []

    with os.scandir(REFERENCE_PATH) as it:
        for entry in it:
            if entry.name.startswith(".") or not entry.is_file():
                continue

            print(f"Reading {entry.name}")

            entities.append(
                {
                    "song_name": entry.name,
                    "vector": get_embedding(
                        librosa.load(entry.path, sr=22050, mono=True)[0]
                    )
                }
            )

    print(f"Adding {len(entities)} songs to collection")

    add_start = time.perf_counter_ns()
    client.insert(
        collection_name="similarity_test",
        data=entities
    )
    add_end = time.perf_counter_ns()

    print(f"Adding took {(add_end - add_start) // 1000000} milliseconds")

    FILENAME = "s4dsp/data/audio.wav"

    audio = librosa.load(FILENAME, sr=22050, mono=True)[0]
    audio_embedding = get_embedding(librosa.load(FILENAME, sr=22050, mono=True)[0])

    print("Searching similarity")

    search_start = time.perf_counter_ns()
    results = client.search(
        collection_name="similarity_test",
        data=[audio_embedding],
        limit=3,
        search_params={ "nprobe": 32 }
    )
    search_end = time.perf_counter_ns()

    print(f"Similarity search took {(search_end - search_start) // 1000000} milliseconds")

    for result in results[0]:
        print(
            f"  {result['id']}"
            f" - Distance: {result['distance']}"
        )

    client.drop_collection(collection_name="similarity_test")
