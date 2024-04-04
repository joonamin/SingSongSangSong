# -*- coding: utf-8 -*-

"""
조성 측정
========

`s4dsp.key`는 음원에 대한 조성 계산 기능을 제공합니다

Utility functions
-----------------
`find_key`
    조성을 계산합니다
"""

import numpy as np
import librosa

def find_key(y: np.ndarray, sr: float):
    """지정된 음원에서 조성을 계산합니다

    Krumhansl-Schmuckler key-finding algorithm을 사용하여 조성을 계산합니다

    파라미터
    --------
    y : np.ndarray
        Waveform 데이터
    sr : float
        Sampling rate
    
    반환 값
    -------
    key : dict[string, Any]
        계산된 조성 및 연관성
    alternative_key : dict[string, Any] | None
        ``key``의 ``correlation``에 근접한 조성이 있을 경우의 조성 및 연관성
    """

    # 음원에서 harmonic 요소 추출
    y_harmonic = librosa.effects.hpss(y)[0]

    # Waveform에서 chromagram 생성
    chromagram = librosa.feature.chroma_cqt(y=y_harmonic, sr=sr, bins_per_octave=24)
    assert len(chromagram) == 12, "Chromagram에는 12개의 pitch가 존재해야 합니다"

    # Chromagram에서 각각의 pitch(C, D#, ...)가 존재하는 정도 기록
    chroma_values = []
    for i in range(12):
        chroma_values.append(np.sum(chromagram[i]))

    # Key의 이름을 chroma_values의 값과 연결
    pitches = ["C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"]
    key_frequencies = { pitches[i]: chroma_values[i] for i in range(12) }

    # 가능한 결과 24개의 목록
    keys = [pitches[i] + " major" for i in range(12)] + [pitches[i] + " minor" for i in range(12)]

    # Krumhansl-Schmuckler key-finding algorithm을 사용할 때 쓰이는 프로필
    # 장조와 단조가 보편적으로 가지는 chroma_values의 값
    major_profile = [6.35, 2.23, 3.48, 2.33, 4.38, 4.09, 2.52, 5.19, 2.39, 3.66, 2.29, 2.88]
    minor_profile = [6.33, 2.68, 3.52, 5.38, 2.60, 3.53, 2.54, 4.75, 3.98, 2.69, 3.34, 3.17]

    # 12개의 pitch에서 각각 시작하여 프로필과의 연관성 계산
    major_key_correlations = []
    minor_key_correlations = []
    for i in range(12):
        key_test = [key_frequencies.get(pitches[(i + j) % 12]) for j in range(12)]
        major_key_correlations.append(round(np.corrcoef(major_profile, key_test)[1, 0], 3))
        minor_key_correlations.append(round(np.corrcoef(minor_profile, key_test)[1, 0], 3))

    # 모든 장조 및 단조와 연관성 저장
    key_dictionary = {
        **{ keys[i]: major_key_correlations[i] for i in range(12) },
        **{ keys[i + 12]: minor_key_correlations[i] for i in range(12) }
    }

    # 연관성 데이터에서 최대값 (알고리즘이 인지한 곡의 조성) 저장
    key = {
        "key": max(key_dictionary, key=key_dictionary.get),
        "correlation": max(key_dictionary.values())
    }

    # 연관성 데이터에서 최대값과 근사한 값이 존재하는 경우 저장
    alternative_key = None

    # 연관성이 최대값의 90% 이상인 경우 대체 조성 저장
    for test_key, test_correlation in key_dictionary.items():
        if test_correlation > key.get("correlation") * 0.9 and test_key != key.get("key"):
            alternative_key = {
                "key": test_key,
                "correlation": test_correlation
            }

    return key, alternative_key

if __name__ == "__main__":
    FILENAME = "s4dsp/data/audio.wav"

    audio, sample_rate = librosa.load(FILENAME)
    primary, alternative = find_key(audio, sample_rate)

    print(f"Key: {primary.get('key')}")
    print(f"Correlation: {primary.get('correlation'):.2f}")

    if alternative is not None:
        print(f"Alternative key: {alternative.get('key')}")
        print(f"Alternative correlation: {alternative.get('correlation'):.2f}")
