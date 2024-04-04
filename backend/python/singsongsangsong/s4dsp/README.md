# 음악 특성 분석

이 README는 작성한 기능과 사용하는 방법을 문서화하는 데 도움이 될 수 있도록 작성되었습니다

## 기능 목록

- [x] 박자 (tempo) 분석 ([tempo.py](./tempo.py))
- [x] 조성 (다단조, 사장조) 분석 ([key.py](./key.py))
- [x] 장르 분석 ([genre.py](./genre.py))
- [x] 분위기 분석 ([mood.py](./mood.py))
- [x] 구조 분석 ([structure.py](./structure.py))
- [ ] 유사도 분석

## 사용방법

### Python 환경 설정

상위 프로젝트에서 사용하는 `pipenv`로 환경을 설정합니다

```sh
cd backend/python/singsongsangsong/
pipenv install
```

아래 기능 별 가이드는 환경 설정을 완료하고 명령 실행 시 `pwd`가 상위 프로젝트의 경로임을 가정합니다

```sh
cd backend/python/singsongsangsong/
```

### 박자 분석

박자 분석은 기본적으로 `s4dsp/data/audio.wav`에서 음원을 불러오므로 음원을 기본 경로에 배치하거나 아래 예시와 같이 `tempo.py`를 수정하여 다른 경로를 지정합니다

```diff
-    FILENAME = "s4dsp/data/audio.wav"
+    FILENAME = "s4dsp/data/test-audio.flac"
```

`tempo.py`를 실행합니다

```sh
pipenv run python s4dsp/tempo.py
```

### 조성 분석

박자 분석은 기본적으로 `s4dsp/data/audio.wav`에서 음원을 불러오므로 음원을 기본 경로에 배치하거나 아래 예시와 같이 `key.py`를 수정하여 다른 경로를 지정합니다

```diff
-    FILENAME = "s4dsp/data/audio.wav"
+    FILENAME = "s4dsp/data/test-audio.flac"
```

`key.py`를 실행합니다

```sh
pipenv run python s4dsp/key.py
```

### 장르 분석

장르 분석을 위해 기본적으로 `s4dsp/data/audio.wav`에서 음원을 불러오므로 음원을 기본 경로에 배치하거나 아래 예시와 같이 `mood.py`를 수정하여 다른 경로를 지정합니다

```diff
-    FILENAME = "s4dsp/data/audio.wav"
+    FILENAME = "s4dsp/data/test-audio.flac"
```

`genre.py`를 실행합니다

```sh
pipenv run python s4dsp/genre.py
```

`genre.py`는 아래의 파일들을 필요로 하며 `s4dsp/data/models/` 내에 존재하지 않을 시 자동으로 다운로드를 진행합니다

- [`discogs-effnet-bs64-1.pb`](https://essentia.upf.edu/models/music-style-classification/discogs-effnet/discogs-effnet-bs64-1.pb)
- [`genre_discogs400-discogs-effnet-1.pb`](https://essentia.upf.edu/models/classification-heads/genre_discogs400/genre_discogs400-discogs-effnet-1.pb)
- [`genre_discogs400-discogs-effnet-1.json`](https://essentia.upf.edu/models/classification-heads/genre_discogs400/genre_discogs400-discogs-effnet-1.json)

### 분위기 분석

분위기 분석을 위해 기본적으로 `s4dsp/data/audio.wav`에서 음원을 불러오므로 음원을 기본 경로에 배치하거나 아래 예시와 같이 `mood.py`를 수정하여 다른 경로를 지정합니다

```diff
-    FILENAME = "s4dsp/data/audio.wav"
+    FILENAME = "s4dsp/data/test-audio.flac"
```

`mood.py`를 실행합니다

```sh
pipenv run python s4dsp/mood.py
```

`mood.py`는 아래의 파일들을 필요로 하며 `s4dsp/data/models/` 내에 존재하지 않을 시 자동으로 다운로드를 진행합니다

- [`discogs-effnet-bs64-1.pb`](https://essentia.upf.edu/models/music-style-classification/discogs-effnet/discogs-effnet-bs64-1.pb)
- [`mtg_jamendo_moodtheme-discogs-effnet-1.pb`](https://essentia.upf.edu/models/classification-heads/mtg_jamendo_moodtheme/mtg_jamendo_moodtheme-discogs-effnet-1.pb)
- [`mtg_jamendo_moodtheme-discogs-effnet-1.json`](https://essentia.upf.edu/models/classification-heads/mtg_jamendo_moodtheme/mtg_jamendo_moodtheme-discogs-effnet-1.json)

### 구조 분석

구조 분석 실행 시 기본적으로 `s4dsp/data/audio.wav`에서 음원을 불러오므로 음원을 기본 경로에 배치하거나 아래 예시와 같이 `structure.py`를 수정하여 다른 경로를 지정합니다

```diff
-    FILENAME = "s4dsp/data/audio.wav"
+    FILENAME = "s4dsp/data/test-audio.flac"
```

`structure.py`를 실행합니다

```sh
pipenv run python s4dsp/structure.py
```
