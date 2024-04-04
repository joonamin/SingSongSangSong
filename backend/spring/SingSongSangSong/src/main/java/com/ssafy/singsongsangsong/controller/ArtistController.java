package com.ssafy.singsongsangsong.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.singsongsangsong.dto.ArtistDetailDto;
import com.ssafy.singsongsangsong.dto.EmotionsDto;
import com.ssafy.singsongsangsong.dto.FollowerCountResponse;
import com.ssafy.singsongsangsong.dto.GuestJoinRequestDto;
import com.ssafy.singsongsangsong.dto.JoinResponseDto;
import com.ssafy.singsongsangsong.dto.MyProfileResponse;
import com.ssafy.singsongsangsong.dto.SimpleSongDto;
import com.ssafy.singsongsangsong.security.ArtistPrincipal;
import com.ssafy.singsongsangsong.service.artist.ArtistService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/artist")
public class ArtistController {
	private final ArtistService artistService;

	@PostMapping("/join")
	public JoinResponseDto join(@AuthenticationPrincipal ArtistPrincipal user, GuestJoinRequestDto dto) throws
		IOException {
		log.info("register request ==> nickname: {}, introduction: {}", dto.getNickname(), dto.getIntroduction());
		artistService.join(user.getUsername(), dto);
		return new JoinResponseDto();
	}

	@GetMapping("{id}")
	public ArtistDetailDto getArtistInfo(@PathVariable(value = "id") Long id) {
		return artistService.getArtistInfo(id);
	}

	@GetMapping("/song/{id}")
	public List<SimpleSongDto> getPublishedSong(@PathVariable Long id) {
		return artistService.getPublishedSong(id);
	}

	@PostMapping("/follow/{id}")
	public void followArtist(@AuthenticationPrincipal ArtistPrincipal user, @PathVariable Long id) {
		artistService.toggleFollowArtist(user.getUsername(), id);
	}

	@GetMapping("/emotions/{id}")
	public EmotionsDto getEmotions(@PathVariable Long id) {
		return artistService.getEmotions(id);
	}

	@GetMapping("/followers/{artistId}/count")
	public FollowerCountResponse getFollowerCount(@PathVariable Long artistId) {
		return artistService.getFollowerCount(artistId);
	}

	@GetMapping("/profile/me")
	public MyProfileResponse getMyProfile(@AuthenticationPrincipal ArtistPrincipal loginUser) {
		return artistService.getMyProfile(loginUser.getId());
	}
}
