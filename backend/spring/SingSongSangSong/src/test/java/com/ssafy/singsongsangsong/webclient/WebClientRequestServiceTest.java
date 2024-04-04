package com.ssafy.singsongsangsong.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class WebClientRequestServiceTest {

	@Autowired
	WebClientRequestService webClientRequestService;

	// TODO: Implement test cases
	// 현재 파이썬 서버에 POST 요청만 있어서 테스트케이스를 작성하기 힘듬, DELETE 요청도 만들어주었으면..!
}