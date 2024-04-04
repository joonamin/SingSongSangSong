# -*- coding: utf-8 -*-

"""
분위기 예측
==========

`s4dsp.mood`는 음원에 대한 분위기 예측 기능을 제공합니다

Utility functions
-----------------
`predict_mood`
    분위기를 예측합니다
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

# 곡 분위기 분석에 필요한 파일 정보
require(
    "s4dsp/data/models/discogs-effnet-bs64-1.pb",
    url="https://essentia.upf.edu/models/music-style-classification/discogs-effnet/"
    "discogs-effnet-bs64-1.pb",
    sha256sum="3ed9af50d5367c0b9c795b294b00e7599e4943244f4cbd376869f3bfc87721b1"
)
require(
    "s4dsp/data/models/mtg_jamendo_moodtheme-discogs-effnet-1.pb",
    url="https://essentia.upf.edu/models/classification-heads/mtg_jamendo_moodtheme/"
    "mtg_jamendo_moodtheme-discogs-effnet-1.pb",
    sha256sum="03f2b047020aee4ab39f8880da7bdae2a36d06a1508d656c6d424ad4d6de07a9"
)
require(
    "s4dsp/data/models/mtg_jamendo_moodtheme-discogs-effnet-1.json",
    url="https://essentia.upf.edu/models/classification-heads/mtg_jamendo_moodtheme/"
    "mtg_jamendo_moodtheme-discogs-effnet-1.json",
    sha256sum="d62cd90263e4d613fa7fcce7a831e339450394794af63685f96e065c1a896ab0"
)

# Metadata 파일에서 장르 이름 불러오기
with open(
    "s4dsp/data/models/mtg_jamendo_moodtheme-discogs-effnet-1.json",
    encoding="utf-8"
) as metadata_file:
    _metadata = json.load(metadata_file)
    _mood_names = _metadata.get("classes")
    del _metadata

def predict_mood(audio: np.ndarray) -> dict[str, float]:
    """분위기를 예측합니다

    Essentia의 MTG-Jamendo mood and theme 모델을 사용하여 장르를 예측합니다

    Parameters
    ----------
    audio : np.ndarray
        essentia.MonoLoader로 불러온 음원 데이터

    Returns
    -------
    dict[str, float]
        분위기 이름과 연관성이 연관성 내림차순으로 정렬된 dictionary
    """

    # 분위기 분석에 필요한 모델 불러오기
    embedding_model = TensorflowPredictEffnetDiscogs(
        graphFilename="s4dsp/data/models/discogs-effnet-bs64-1.pb",
        output="PartitionedCall:1"
    )
    prediction_model = TensorflowPredict2D(
        graphFilename="s4dsp/data/models/mtg_jamendo_moodtheme-discogs-effnet-1.pb",
        output="model/Sigmoid"
    )

    # Discogs-EffNet 기반 모델로 embeddings 생성
    embeddings = embedding_model(audio)

    # MTG-Jamendo mood and theme 모델로 장르 예측 및 평균값 계산
    predictions = prediction_model(embeddings)
    predictions_mean = np.mean(predictions, axis=0)

    # 높은 순으로 장르 예측 값 정렬
    moods = { _mood_names[i]: predictions_mean[i] for i in range(len(_mood_names)) }
    moods = dict(sorted(moods.items(), key=lambda item: item[1], reverse=True))

    return moods

if __name__ == "__main__":
    FILENAME = "s4dsp/data/audio.wav"

    reference_audio = MonoLoader(filename=FILENAME, sampleRate=16000, resampleQuality=4)()
    predicted_moods = predict_mood(reference_audio)

    # 상위 5개의 분위기 출력
    print("Mood classification result")
    for mood, value in islice(predicted_moods.items(), 5):
        print(f"  {mood}: {value:.2f}")
