# -*- coding: utf-8 -*-

import datetime as dt
import numpy as np
import matplotlib.pyplot as plt
import librosa.display

def get_color(label):
    color_map = {
        "intro": "wheat",
        "verse": "lightgreen",
        "chorus": "lightcoral",
        "transition": "thistle",
        "outro": "lightblue"
    }
    return color_map.get(label, "lightgrey")

def draw_spectrum(y, sr, file_name, label_timestamp):
    # -1 ~ 1 사이 값을 -100 ~ 100으로 확장
    y *= 100
    duration_seconds = len(y) / sr
    time_interval = 20

    # 시간 값 생성
    timestamps = [dt.timedelta(seconds=i) for i in range(0, int(duration_seconds)+1, time_interval)]
    start_time = dt.datetime.now().replace(hour=0, minute=0, second=0, microsecond=0)
    datetimes = [start_time + ts for ts in timestamps]

    # figure 생성
    plt.figure(figsize=(12, 2))


    # 구분 배경 범위 지정
    for entry in label_timestamp:
        start_index = int(entry["start"] * sr)
        end_index = int(entry["end"] * sr)
        color = get_color(entry["label"])

        plt.fill_between(
            range(start_index, end_index),
            y[start_index:end_index],
            color=color,
            alpha=1.0
        )

    # 스펙트럼 값 입력
    plt.plot(y, color="midnightblue", alpha=0.2)

    # 시간 레이블 지정
    plt.xticks(
        np.arange(0, len(y), sr*time_interval),
        [dt.strftime("%M:%S") for dt in datetimes],
        rotation=0
    )

    # 색상 설명 추가
    plt.legend(
        handles=[
            plt.Rectangle((0, 0), 1, 1, fc="wheat", edgecolor="none"),
            plt.Rectangle((0, 0), 1, 1, fc="lightgreen", edgecolor="none"),
            plt.Rectangle((0, 0), 1, 1, fc="lightcoral", edgecolor="none"),
            plt.Rectangle((0, 0), 1, 1, fc="thistle", edgecolor="none"),
            plt.Rectangle((0, 0), 1, 1, fc="lightblue", edgecolor="none")
        ],
        labels=["intro", "verse", "chorus", "transition", "outro"],
        loc="center left",
        bbox_to_anchor=(1, 0.5)
    )

    # 파일 저장
    plt.savefig(file_name)
    # 파일 확인
    # plt.show()

if __name__ == "__main__":
    # 입력값 모음
    audio, sample_rate = librosa.load("s4dsp/data/audio.wav", mono=True)
    DESTINATION = "s4dsp/data/savefig_default.png"
    timestamp_labels = [
        {"start": 5, "end": 10, "label": "outro"},
        {"start": 15, "end": 20, "label": "transition"},
        {"start": 20, "end": 30, "label": "chorus"},
        {"start": 30, "end": 50, "label": "verse"},
        {"start": 60, "end": 120, "label": "intro"}
    ]
    draw_spectrum(audio, sample_rate, DESTINATION, timestamp_labels)
