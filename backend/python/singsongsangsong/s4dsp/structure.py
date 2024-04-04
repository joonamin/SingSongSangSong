# -*- coding: utf-8 -*-

"""
구조 분석
========

`s4dsp.structure`는 음원에 대한 구조를 분할하고 각 구조에 대한 특성을 인식합니다

Utility functions
-----------------
`find_structure`
    구조를 분할하여 특성을 인식합니다
"""

import numpy as np
import scipy.signal
from scipy.spatial.distance import cdist
import librosa
from pychorus import create_chroma
from pychorus.similarity_matrix import TimeTimeSimilarityMatrix, TimeLagSimilarityMatrix, Line
import msaf

# Denoising size (초 단위)
_SMOOTHING_SIZE_SECONDS = 2.5

# Line 겹침 확인 시 허용하는 오차
_LINE_OVERLAP_MARGIN = 0.2

def _local_maxima_rows(time_lag_similarity):
    """국부 최댓값인 열 확인"""
    row_sums = np.sum(time_lag_similarity, axis=1)
    divisor = np.arange(row_sums.shape[0], 0, -1)

    normalized_rows = row_sums / divisor

    local_minima_rows = scipy.signal.argrelextrema(normalized_rows, np.greater)
    return local_minima_rows[0]

def _detect_lines_helper(time_lag_matrix, rows, threshold, min_length_samples):
    """min_length_samples가 threshold 이상인 line 인식"""
    num_samples = time_lag_matrix.shape[0]
    line_segments = []
    segment_start = None
    for row in rows:
        if row < min_length_samples:
            continue
        for col in range(row, num_samples):
            if time_lag_matrix[row, col] > threshold:
                if segment_start is None:
                    segment_start = col
            else:
                if (
                    segment_start is not None
                ) and (col - segment_start) > min_length_samples:
                    line_segments.append(Line(segment_start, col, row))
                segment_start = None
    return line_segments

def _detect_lines(time_lag_similarity, rows, min_length_samples):
    """Time lag matrix에서 line 인식"""
    line_threshold = 0.15
    minimum_lines = 10
    iterations = 20

    threshold = line_threshold
    for throwaway in range(iterations): # pylint: disable=unused-variable
        line_segments = _detect_lines_helper(
            time_lag_similarity,
            rows,
            threshold,
            min_length_samples
        )
        if len(line_segments) >= minimum_lines:
            return line_segments
        threshold *= 0.95

    return line_segments

def _count_overlapping_lines(lines, margin, min_length_samples):
    """모든 line 쌍을 확인하여 겹치는지 확인"""
    line_scores = {}
    for line in lines:
        line_scores[line] = 0

    # Line 쌍 확인
    for line_1 in lines:
        for line_2 in lines:
            lines_overlap_vertically = (
                line_2.start < (line_1.start + margin)
            ) and (
                line_2.end > (line_1.end - margin)
            ) and (
                abs(line_2.lag - line_1.lag) > min_length_samples
            )

            lines_overlap_diagonally = (
                (line_2.start - line_2.lag) < (line_1.start - line_1.lag + margin)
            ) and (
                (line_2.end - line_2.lag) > (line_1.end - line_1.lag - margin)
            ) and (
                abs(line_2.lag - line_1.lag) > min_length_samples
            )

            if lines_overlap_vertically or lines_overlap_diagonally:
                line_scores[line_1] += 1

    return line_scores

def _sort_segments(line_scores):
    """Line을 chorus 및 길이로 정렬"""
    to_sort = [(line, line_scores[line], line.end - line.start) for line in line_scores]
    to_sort.sort(key=lambda x: (x[1], x[2]), reverse=True)

    return to_sort

def _dtw(x, y, dist, warp=1):
    """MFCC를 기준으로 dynamic time warping을 사용하여 주어진 구간의 유사도 확인"""
    assert len(x)
    assert len(y)

    if np.ndim(x) == 1:
        x = x.reshape(-1, 1)
    if np.ndim(y) == 1:
        y = y.reshape(-1, 1)

    r, c = len(x), len(y)

    distance_0 = np.zeros((r + 1, c + 1))
    distance_0[0, 1:] = np.inf
    distance_0[1:, 0] = np.inf
    distance_1 = distance_0[1:, 1:]
    distance_0[1:, 1:] = cdist(x, y, dist)

    for i in range(r):
        for j in range(c):
            min_list = [distance_0[i, j]]
            for k in range(1, warp + 1):
                min_list += [distance_0[min(i + k, r), j], distance_0[i, min(j + k, c)]]
            distance_1[i, j] += min(min_list)

    return distance_1[-1, -1] / sum(distance_1.shape)

def find_structure(path: str) -> dict[tuple[float, float], str]: # pylint: disable=too-many-locals,too-many-branches,too-many-statements
    """구조를 분할하여 특성을 인식합니다
    
    Parameters
    ----------
    path : str
        음원 파일의 경로
    
    Returns
    -------
    dict[tuple[float, float], str]
        분할된 구조의 시작과 종료 지점 및 label이 포함된 dictionary
    """
    # Power spectrogram을 활용하여 chromagram 생성
    chromagram, y, sr, duration = create_chroma(path)

    # MSAF를 활용하여 구간 분할
    processed_boundaries, processed_labels = msaf.process(
        path,
        feature="mfcc",
        boundaries_id="foote",
        labels_id="fmc2d",
        out_sr=sr
    )

    boundaries = []
    labels = []
    mfccs = []
    for i in range(len(processed_boundaries) - 1):
        # 5초보다 긴 구간들을 선택하여 MFCC 계산
        if processed_boundaries[i + 1] - processed_boundaries[i] >= 5:
            segment = y[
                librosa.time_to_samples(processed_boundaries[i], sr=sr):
                librosa.time_to_samples(processed_boundaries[i + 1], sr=sr)
            ]
            mfcc = librosa.feature.mfcc(y=segment, sr=sr)

            boundaries.append(processed_boundaries[i])
            labels.append(processed_labels[i])
            mfccs.append(np.average(mfcc, axis=0))

    num_samples = chromagram.shape[1]

    # Time time similarity matrix, time lag similarity matrix 생성
    time_time_similarity = TimeTimeSimilarityMatrix(chromagram, sr)
    time_lag_similarity = TimeLagSimilarityMatrix(chromagram, sr)

    chromagram_sr = num_samples / duration
    clip_length = 10
    smoothing_size_samples = int(_SMOOTHING_SIZE_SECONDS * chromagram_sr)

    # Time lag similarity matrix에 대한 denoising 진행
    time_lag_similarity.denoise(time_time_similarity.matrix, smoothing_size_samples)

    clip_length_samples = clip_length * chromagram_sr

    candidate_rows = _local_maxima_rows(time_lag_similarity.matrix)

    # Time lag similarity matrix에서 line 인식
    lines = _detect_lines(time_lag_similarity.matrix, candidate_rows, clip_length_samples)

    assert len(lines) != 0

    # 겹치는 line 정렬
    line_scores = _count_overlapping_lines(
        lines,
        _LINE_OVERLAP_MARGIN * clip_length_samples,
        clip_length_samples
    )

    choruses = _sort_segments(line_scores)

    # 구간의 시작과 종점 계산
    chorus_times = [
        (chorus[0].start / chromagram_sr, chorus[0].end / chromagram_sr) for chorus in choruses
    ]

    # 구간 시간별 정렬
    chorus_times.sort(key=lambda x: x[0])

    sorted_chorus_times = [chorus_times[0]]
    # 겹치는 구간 제거
    for i in range(1, len(chorus_times)):
        if (chorus_times[i][0] - sorted_chorus_times[-1][0]) >= clip_length:
            sorted_chorus_times.append(chorus_times[i])

    max_onset = 0
    best_chorus = []

    # 10초와 30초 사이 길이의 chorus 후보를 지정한 뒤 onset 인식으로 best chorus 지정
    for time in sorted_chorus_times:
        if 10 <= (time[1] - time[0]) and (time[1] - time[0]) <= 30:
            y_chorus = y[int(time[0] * sr) : int(time[1] * sr)]
            onset_detect = librosa.onset.onset_detect(y=y_chorus, sr=sr)
            if np.mean(onset_detect) >= max_onset:
                max_onset = np.mean(onset_detect)
                best_chorus = y_chorus

    # Best chorus의 MFCC 계산
    chorus_mfcc = np.average(librosa.feature.mfcc(y=best_chorus, sr=sr), axis=0)

    structure_labels = [""] * len(labels)

    # 각 구간과 chorus로 인식된 구간 사이의 dynamic time warping (DTW) 계산
    max_dist = 0
    min_dist = 100
    similarity_measures = []
    def euclidean_norm(x, y):
        return np.abs(x - y)

    for x in range(len(boundaries)):
        dist = _dtw(mfccs[x], chorus_mfcc, dist=euclidean_norm)
        similarity_measures.append(dist)
        if dist > max_dist:
            max_dist = dist
        if dist < min_dist:
            min_dist = dist

    # Normalize 후 정렬
    normalized = [float(i) / max(similarity_measures) for i in similarity_measures]
    sorted_norms = sorted(normalized)

    # Threshold normalization
    bottom = []
    if max_dist - min_dist <= 2:
        bottom = sorted_norms[int(len(sorted_norms) * 0) : int(len(sorted_norms) * 0.5)]
    else:
        bottom = sorted_norms[int(len(sorted_norms) * 0) : int(len(sorted_norms) * 0.40)]

    # DTW 유사도가 normalize 된 threshold보다 작을 시 그 구간은 chorus
    for x in range(len(structure_labels)): # pylint: disable=consider-using-enumerate
        if normalized[x] <= bottom[-1]:
            structure_labels[x] = "chorus"

    # verse는 chorus가 아닌 반복되는 구간
    # transition은 곡 중반에서 고유한 구간
    # intro, outro는 각각 곡 처음과 끝 부분의 고유한 구간
    for x in range(len(structure_labels)): # pylint: disable=consider-using-enumerate
        found_match = False

        for y in range(x + 1, len(structure_labels)):
            if (
                labels[x] == labels[y]
            ) and (
                structure_labels[y] == ""
            ) and (
                structure_labels[x] == ""
            ):
                found_match = True
                structure_labels[x] = "verse"
                structure_labels[y] = "verse"

        if not found_match and structure_labels[x] == "":
            if x == 0:
                structure_labels[x] = "intro"
            elif x == (len(boundaries) - 1):
                structure_labels[x] = "outro"
            else:
                structure_labels[x] = "transition"

    segments = {}

    for i, segment_start in enumerate(boundaries):
        if i < len(boundaries) - 1:
            segment_end = boundaries[i + 1]
        else:
            segment_end = duration

        segments[(segment_start, segment_end)] = structure_labels[i]

    return segments

if __name__ == "__main__":
    FILENAME = "s4dsp/data/audio.wav"

    structure = find_structure(FILENAME)

    print("Structure detection result")
    for boundary, label in structure.items():
        print(f"  {boundary[0]:.2f}-{boundary[1]:.2f}: {label}")
