package com.ssafy.singsongsangsong.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.singsongsangsong.dto.PostCommentsDto;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;
import com.ssafy.singsongsangsong.security.ArtistPrincipal;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LoginController {
	private ArtistRepository artistRepository;

	@GetMapping("/")
	public PostCommentsDto main(@AuthenticationPrincipal ArtistPrincipal user) {
		if (user == null) {
			log.error("user 정보가 전달되지 않았습니다");
		} else {
			log.info("currnet user ===> id: {}, username: {}, nickname: {}", user.getId(), user.getUsername(),
				user.getNickname());
		}
		return new PostCommentsDto(1L, "abc");
	}

}
