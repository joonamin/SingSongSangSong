# 싱송생송 API

> 2024.04.08 최신화		 @강민준

## 곡 분석

다음과 같은 목적을 위해 개발되었습니다.

* 곡 분석 상태 확인
	* polling 방식으로, 클라이언트단에서 현재 곡의 업로드 상태를 확인합니다.
* 곡 분석 요청
	* 싱송생송 DSP API로 server-to-server 요청을 보냅니다.
	* 실패할 경우, 실패 상태를 반환하고 일정 주기로 Retry합니다.
* 곡 분석 결과 확인
	* 현재 `songId` 에 해당하는 분석 결과를 조회합니다.
	* `곡의 정보`, `mfcc 분석`, `spectrum`, `유사도가 가장 높은 음악 정보` 를 표시합니다.
* 곡 publish
	* 분석 결과를 publish하여, aggregation의 대상으로 선정합니다.
	* publish된 곡은 다른 사용자들이 접근하여 평가할 수 있습니다.
* 곡 분석 완료 callback
	* 싱송생송 DSP에서 분석 완료 이후에, 콜백으로 호출하는 url 입니다.
	* 보안 상의 이유로, `싱송생송 DSP` 외의 다른 cross-origin-request를 거부합니다.
* 곡 업로드 및 분석 결과 이미지 다운로드
	* 기존 `sftp` 를 이용한 파일 다운로드 인터페이스를 준수하여 작성하였습니다.
	* 벡터 이미지 형식인 `mfcc image` 형식에 대해서는 다른 응답 헤더(`content-type`)이 필요하여, 추가적으로 API를 구현하였습니다.



## 아티스트 상세

다음과 같은 목적을 위해 개발되었습니다.

* 아티스트의 정보 조회
	* 아티스트의 `id`, `username`, `nickname`, `profileImage`, `introduction`
	* 발매된 곡의 갯수
	* 해당 아티스트가 올린 곡들에 대한 장르별 선호도를 표시합니다.
	* 해당 아티스트가 올린 곡들에 대한 주요 분위기를 표시합니다.

* 아티스트의 게시 곡 조회
	* 해당 아티스트가 게시한 곡의 목록을 표시합니다.
* 아티스트가 받은 emotion 조회
	* 아티스트가 발매한 모든 곡들에 대한 emotion count를 가져옵니다.
* 현재 아티스트의 정보 조회
	* `Authentication`가 완료된 `현재 사용자의 정보` 를 가져옵니다.
	* `jwt` 에 담긴 인증 정보를 이용하여 현재 사용자를 인증합니다. 



## 곡 상세

다음과 같은 목적을 위해 개발되었습니다.

* 곡에 대한 감정 남기기
	* 특정 아티스트의 곡에 대해서 emotion을 남깁니다.
* 댓글 남기기
	* 특정 아티스트의 곡에 대해서 댓글을 남깁니다.
* 곡 정보 불러오기
	* 해당 곡에 대한 `제목`, `author`, `song`, `emotion_count`, `가사`, `chord`, `bpm`, `댓글` 등을 불러옵니다.
* 분위기/장르 분석 결과 조회
	* 해당 노래에 해당하는 분위기와 장르에 대한 분석 결과를 기반으로 `유사도` 를 불러옵니다.
	* 상위 $n$ 개의 높은 연관성을 가진 분위기와 장르를 가져옵니다.
* 유사곡 조회
	* 해당 노래와 유사한 노래들을 $n$ 개 불러옵니다.
	* 벡터 기반의 db(`milvus`)를 이용하여 실시간 처리가 가능하도록 하였습니다.
* 곡의 구간 분석
	* 각 구간이 곡의 어떤 부분에 해당하는지 분석한 결과를 표시합니다. (e.g.`chorus`, `intro` , ...)
* 곡의 spectrum 가져오기
	* 구간 분석 결과를 기반으로, 곡의 전체 구간에 대해 다른 색으로 spectrum을 표현합니다.





## 회원 관리

다음과 같은 목적을 위해 개발되었습니다.

- 유저의 인증, 권한 관리
	- Spring Security Oauth2 Login
		- 외부 소셜(Google, Kakao) 계정을 기반으로 유저 인증을 진행합니다.
		- 참고 파일
			- `/config/SecurityConfig.java`
			- `/handler/Oauth2LoginSuccessHandler.java` ,`/handler/Oauth2FailureHandler.java`
	- Jwt Provider
		- 외부 소셜 서비스에서 받은 OauthAttributes를 기반으로 accessToken을 생성합니다.
		- 참고 파일
			- `/service/jwt/CustomOAuth2UserService.java`
			- `/service/jwt/JwtService.java`
	- Jwt Authentication Filter
		- Jwt가 유효한지 확인하고 유효하다면 contextHolder에 authentication을 저장합니다.
		- 참고 파일
			- `/service/jwt/JwtService.java`
			- `/filter/JwtAuthenticationProcessingFilter`



## Trend 분석

- Spring Batch

  - 빅데이터 분산 처리를 위해 사용
  - 사용자 행동 로그를 csv 파일로 받아온 후 주차별 분류 작업 실행
  	- 사용자와의 Interaction을 통해, 서비스를 개선하기 위하여 수행하였습니다.
  

  ![image](https://raw.githubusercontent.com/joonamin/UpicImageRepo/master/uPic/8781a5e5-3390-4f83-837d-9a6ff5af3b7c.png)
  
  - Chunk 처리 방식 사용
  - Spring Batch의 Scheduler 기능을 사용하여 매주 분류 작업 실행을 자동화
