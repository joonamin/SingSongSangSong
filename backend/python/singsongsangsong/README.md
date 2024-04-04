# 싱송생송 DSP API

![skill.png](/uploads/e4035e295c8d2e96e775a25bac8288ff/image.png)

다음과 같은 목적을 위해 개발되었습니다.

 - 곡 유사도 검사
 - 곡 장르 분석
 - 곡 분위기 분석
 - 곡 템포/조성 분석
 - 곡 구간 분할

## 연결 정보
현재 코드를 기반하여 제공되는 서비스는 다음과 같습니다.

|구분|연결|
|:--:|:--|
| Swagger |https://dsp.singsongsangsong.com/docs|
| Redoc |https://dsp.singsongsangsong.com/redoc|
| Attu Zilliz |http://j10e206a.p.ssafy.io:8080/|

## 제공 API
FastAPI에서 제공하는 서비스는 다음과 같습니다.

|Method|URI|Summary|
|:--:|:--|:--|
| POST | /song/{id} | 곡 분석 요청 |
| POST | /similarity | 곡 유사도 정보 저장 |
| GET | /similarity/{id} | 곡 유사도 조회 |

## 실행방법

```sh
pipenv install
pipenv run gunicorn main:app --workers 4 --worker-class uvicorn.workers.UvicornWorker --bind 0.0.0.0:8000 --log-level debug --daemon --capture-output --enable-stdio-inheritance
```

## 참고자료

### 1) 템포 (BPM)
 - **Librosa**: [librosa.beat.beat_track](https://librosa.org/doc/latest/generated/librosa.beat.beat_track.html#librosa-beat-beat-track)


### 2) 조성

 -  Krumhansl-Schmuckler key-finding algorithm :  [Key-finding algorithm](https://rnhart.net/articles/key-finding/)
 - **GitHub**: [jackmcarthur/musical-key-finder](https://github.com/jackmcarthur/musical-key-finder) 


### 3) 분위기

 - **Essentia**: [MTG-Jamendo mood and theme](https://essentia.upf.edu/models.html#mtg-jamendo-mood-and-theme)

### 4) 장르
 - **Essentia**: [Genre Discogs400](https://essentia.upf.edu/models.html#genre-discogs400)

### 5) 구간 분할

 - [Automatic Song Structure Detection Program](https://vivianschen.github.io/ASSDP/)
 - [Vivianschen/ASSDP](https://github.com/vivianschen/ASSDP)

### 6) 유사도 분석
 - **Zilliz**: [zilliz-bootcamp/audio_search (Audio search system with Milvus)](https://github.com/zilliz-bootcamp/audio_search)
 - **PANNs** : [qiuqiangkong/audioset_tagging_cnn](https://github.com/qiuqiangkong/audioset_tagging_cnn)
 - [Milvus](https://milvus.io/)
