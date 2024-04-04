# -*- coding: utf-8 -*-

"""
장르 예측
========

`s4dsp.genre`는 음원에 대한 장르 예측 기능을 제공합니다

Utility functions
-----------------
`predict_genre`
    장르를 예측합니다
"""

from itertools import islice
import json
import numpy as np
from essentia.standard import ( # pylint: disable=no-name-in-module
    MonoLoader,
    TensorflowPredictEffnetDiscogs,
    TensorflowPredict2D
)
from s4dsp._dependencies import require

# 장르 분석에 필요한 파일 정보
require(
    "s4dsp/data/models/discogs-effnet-bs64-1.pb",
    url="https://essentia.upf.edu/models/music-style-classification/discogs-effnet/"
    "discogs-effnet-bs64-1.pb",
    sha256sum="3ed9af50d5367c0b9c795b294b00e7599e4943244f4cbd376869f3bfc87721b1"
)
require(
    "s4dsp/data/models/genre_discogs400-discogs-effnet-1.pb",
    url="https://essentia.upf.edu/models/classification-heads/genre_discogs400/"
    "genre_discogs400-discogs-effnet-1.pb",
    sha256sum="3885ba078a35249af94b8e5e4247689afac40deca4401a4bc888daf5a579c01c"
)
require(
    "s4dsp/data/models/genre_discogs400-discogs-effnet-1.json",
    url="https://essentia.upf.edu/models/classification-heads/genre_discogs400/"
    "genre_discogs400-discogs-effnet-1.json",
    sha256sum="2d367319d9b782ffa10f69abf0e805b3ac4e10899025e5bdbaceda3919b243e0"
)

# Metadata 파일에서 장르 이름 불러오기
with open(
    "s4dsp/data/models/genre_discogs400-discogs-effnet-1.json",
    encoding="utf-8"
) as metadata_file:
    _metadata = json.load(metadata_file)
    _genre_names = _metadata.get("classes")
    del _metadata

def predict_genre(audio: np.ndarray) -> dict[str, float]:
    """장르를 예측합니다

    Essentia의 Discogs400 모델을 사용하여 장르를 예측합니다

    Parameters
    ----------
    audio : np.ndarray
        essentia.MonoLoader로 불러온 음원 데이터

    Returns
    -------
    dict[str, float]
        장르 이름과 연관성이 연관성 내림차순으로 정렬된 dictionary
    """

    # 장르 분석에 필요한 모델 불러오기
    embedding_model = TensorflowPredictEffnetDiscogs(
        graphFilename="s4dsp/data/models/discogs-effnet-bs64-1.pb",
        output="PartitionedCall:1"
    )
    prediction_model = TensorflowPredict2D(
        graphFilename="s4dsp/data/models/genre_discogs400-discogs-effnet-1.pb",
        input="serving_default_model_Placeholder",
        output="PartitionedCall:0"
    )

    # Discogs-EffNet 기반 모델로 embeddings 생성
    embeddings = embedding_model(audio)

    # Genre Discogs400 모델로 장르 예측 및 평균값 계산
    predictions = prediction_model(embeddings)
    predictions_mean = np.mean(predictions, axis=0)

    # 높은 순으로 장르 예측 값 정렬
    genres = { _genre_names[i]: predictions_mean[i] for i in range(len(_genre_names)) }
    genres = dict(sorted(genres.items(), key=lambda item: item[1], reverse=True))

    return genres

if __name__ == "__main__":
    FILENAME = "s4dsp/data/audio.wav"

    reference_audio = MonoLoader(filename=FILENAME, sampleRate=16000, resampleQuality=4)()
    predicted_genres = predict_genre(reference_audio)

    # 상위 5개의 장르 출력
    print("Genre classification result")
    for genre, value in islice(predicted_genres.items(), 5):
        print(f"  {genre}: {value:.2f}")
