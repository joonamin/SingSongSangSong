# -*- coding: utf-8 -*-

"""
싱송생송 음악 특성 분석
===============

곡의 특징 및 유사도를 분석하는 기능을 제공합니다

Utility functions
-----------------
`get_tempo`
    템포를 측정합니다
`find_key`
    조성을 계산합니다
`predict_genre`
    장르를 예측합니다
`predict_mood`
    분위기를 예측합니다
`find_structure`
    구조를 분할하여 특성을 인식합니다
`get_embedding`
    PANNs를 사용하여 음원의 패턴을 확인합니다
`plot_mfcc`
    음원에서 생성한 MFCC 그래프를 저장합니다
`draw_spectrum`
    음원의 waveform과 분할된 구조를 그린 그래프를 저장합니다
"""

from s4dsp.tempo import get_tempo
from s4dsp.key import find_key
from s4dsp.genre import predict_genre
from s4dsp.mood import predict_mood
from s4dsp.structure import find_structure
from s4dsp.similarity import get_embedding
from s4dsp.mfcc import plot_mfcc
from s4dsp.segment import draw_spectrum
