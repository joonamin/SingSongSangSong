# 싱송생송: SSAFY 10기 특화 프로젝트

### SSAFY 10기 2학기 특화 프로젝트

 - 개발 기간 : 24.02.26 ~ 24.04.05 (6주)
 - 참여 인원 : 총 6명 (Frontend 1 / Backend 4 / Infra 1)

 > 우리는 아름다운 노래를 많이 듣습니다. <br>
 > 지금 이 순간에도 많은 노래들이 나오고 있죠. <br>
 > 내가 만든 작품을 여럿이 즐겼으면 하는 마음, <br>
 > 모두가 즐길 수 있는 컨텐츠를 생산하고자하는 욕구, <br>
 > 그 마음을 채워주고자 했습니다.

[ 📑 발표 자료 ](https://www.canva.com/design/DAGBKBggqRw/qPOQpYoUrUSIzJm5w3vW6Q/view?utm_content=DAGBKBggqRw&utm_campaign=designshare&utm_medium=link&utm_source=editor) [ 🎬 관련 영상 ](https://www.youtube.com/watch?v=CUqH7gllveE)


## 서비스 상세 구현

### Spring Boot
 > 사용자를 위한 서비스 로직 구현책 입니다.
 - 구현 기능 : 사용자 관리 기능, 서비스 전체 표출 로직, 이미지/곡 리소스 관리, 보안 설정, 데이터 분산 처리
 - 관련 상세 정보는 [이곳](./backend/python/singsongsangsong/README.md)에서 확인 가능합니다.

### FastAPI
 > 곡 분석을 위한 서비스 로직 구현책 입니다.
 - 구현 기능 : 오디오 파일에 대한 벡터 연산, 곡 분석 결과 연산
 - 관련 상세 정보는 [이곳](./backend/spring/SingSongSangSong/README.md)에서 확인 가능합니다.

### React
 > 사용자 UI에 대한 구현책 입니다.
 - 구현 기능 : 모든 기능에 대한 사용자 인터페이스
  - 관련 상세 정보는 [이곳](./frontend/README.md)에서 확인 가능합니다.

## 전체 환경 구성

|아키텍처|ERD|
|:--:|:----:|
|![프로젝트 아키텍처](/uploads/3248c6618ae9de6c66c40734a698db23/image.png)|![프로젝트 ERD](/uploads/8af5feb3b2dd0cb1008786802b156bc8/ERD.svg) |

## 서비스 소개

### 트렌드 확인

싱송차트, 연령 별 인기곡, 분위기 별 인기곡 등 주간 동향 파악이 가능한 지표 표출

|금주의 싱송차트|성별과 연령으로 알아보는 트렌드|장르별 랭킹|
|:--:|:--:|:--:|
|![금주의 싱송차트](/uploads/8424700965c7dc43cc72f65f11a93e15/d02943dca5.gif)|![성별과 연령으로 알아보는 트렌드](/uploads/45d2fe71edc00fbb917281a407be990b/czM6Ly9tZWRpYS1wcml2YXRlLmNhbnZhLmNvbS9jcDNmay9NQUdCYUpjcDNmay8xL3AucG5n.webp)| ![장르별 랭킹](/uploads/3ca355f61accbe21e9a82f5781c82413/e2ef565358.gif) |

| 세계와 한국인이 선택한 노래 | 감정 극대화 곡 |
|:--:|:--:|
| ![세계와 한국인이 선택한 노래](/uploads/ca460633688f0a84dda351cdf5d9f442/7f6bfeaca6.gif) | ![감정 극대화 곡](/uploads/da2749046ca798d674b772e4eab19840/czM6Ly9tZWRpYS1wcml2YXRlLmNhbnZhLmNvbS9fTnVKOC9NQUdCYUZfTnVKOC8xL3AucG5n.webp) |

### 둘러보기

원하는 분위기, 장르 등을 기반으로 곡을 검색하고 감상

![둘러보기](/uploads/b86830c1bbceae723a450487d6758043/cd004c29f0.gif)

### 분석하기

MFCC 기반의 정밀 분석을 기반으로 한 나의 곡이 가진 분위기, 장르, 유사곡 표출

|분석하기 페이지|MFCC 결과|
|:--:|:--:|
|![분석하기 페이지](/uploads/ad0f27b70875cbe26ef3f15cde43f4ec/czM6Ly9tZWRpYS1wcml2YXRlLmNhbnZhLmNvbS9xQlZYUS9NQUdCYU1xQlZYUS8xL3AucG5n.webp)|![MFCC 결과](/uploads/0b3a3af6ea8e9c9d53741eb88231530a/czM6Ly9tZWRpYS1wcml2YXRlLmNhbnZhLmNvbS96ZWdUNC9NQUdCYU96ZWdUNC8xL3AucG5n.webp)|

|구간 분석 결과|유사도 분석 결과|
|:--:|:--:|
|![구간 분석 결과](/uploads/944c85042e84ab33578ff40ec302f8da/czM6Ly9tZWRpYS1wcml2YXRlLmNhbnZhLmNvbS96UEVZRS9NQUdCYUF6UEVZRS8xL3AucG5n.webp)|![유사도 분석 결과](/uploads/c21d398d7d951cf02ca9bad23e45e432/czM6Ly9tZWRpYS1wcml2YXRlLmNhbnZhLmNvbS9Sa0lLby9NQUdCYUpSa0lLby8xL3AucG5n.webp)|
