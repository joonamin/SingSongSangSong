# -*- coding: utf-8 -*-

import librosa
import librosa.display
import matplotlib.pyplot as plt

def plot_mfcc(mfccs, file_name):
    # MFCC 그리기
    plt.figure(figsize=(12, 2))
    librosa.display.specshow(mfccs, x_axis="time", cmap="afmhot")
    plt.colorbar(format="%+2.0f dB")
    plt.title("MFCC")
    plt.xlabel("Time")
    plt.ylabel("MFCC Coefficient")

    # 파일 저장
    plt.savefig(file_name)
    # 파일 확인
    # plt.show()

if __name__ == "__main__":
    # 입력값
    FILENAME = "s4dsp/data/audio.wav"
    DESTINATION = "s4dsp/data/savefig_default.png"
    audio, sample_rate = librosa.load(FILENAME, mono=True)
    mfcc = librosa.feature.mfcc(y=audio, sr=sample_rate, n_mfcc=20)

    plot_mfcc(mfcc, DESTINATION)
