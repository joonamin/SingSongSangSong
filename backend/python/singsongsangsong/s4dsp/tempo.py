# -*- coding: utf-8 -*-

"""
템포 측정
========

`s4dsp.tempo`는 음원에 대한 템포 측정 기능을 제공합니다

Utility functions
-----------------
`get_tempo`
    템포를 측정합니다
"""

import numpy as np
import librosa

def get_tempo(y: np.ndarray, sr: float) -> float:
    """지정된 음원에 대한 템포를 측정합니다

    Parameters
    ----------
    y : np.ndarray
        음원 정보
    sr : float
        Sample rate

    Returns
    -------
    float
        음원에 대한 BPM 형식의 템포
    """
    return librosa.beat.beat_track(y=y, sr=sr)[0]

if __name__ == "__main__":
    FILENAME = "s4dsp/data/audio.wav"
    audio, sample_rate = librosa.load(FILENAME)

    tempo = librosa.beat.beat_track(y=audio, sr=sample_rate)[0]
    print(f"Tempo: {tempo:.2f}")
