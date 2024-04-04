package com.ssafy.singsongsangsong.controller;

import java.io.IOException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.singsongsangsong.constants.FileType;
import com.ssafy.singsongsangsong.dto.PublishSongRequest;
import com.ssafy.singsongsangsong.dto.SimpleSongDto;
import com.ssafy.singsongsangsong.dto.UploadMainPageDto;
import com.ssafy.singsongsangsong.exception.song.AlreadyCompletedException;
import com.ssafy.singsongsangsong.security.ArtistPrincipal;
import com.ssafy.singsongsangsong.service.analyze.AnalyzeService;
import com.ssafy.singsongsangsong.service.file.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/analyze")
@RequiredArgsConstructor
public class AnalyzeController {

	private final AnalyzeService analyzeService;
	private final FileService fileService;

	@PatchMapping("/song/{id}")
	// @CrossOrigin(origins = corsConfig.getAllowedOrigins())
	public void completeAnalyze(@PathVariable Long id) throws AlreadyCompletedException {
		analyzeService.completeAnalyze(id);
	}

	@GetMapping("/status")
	public UploadMainPageDto getUploadMainPage(
		@AuthenticationPrincipal ArtistPrincipal user) {
		return analyzeService.getUploadStatus(user.getId());
	}

	@PostMapping("/{songId}")
	public void requestAnalyze(@AuthenticationPrincipal ArtistPrincipal user,
		@PathVariable Long songId) {
		analyzeService.requestAnalyze(user.getId(), songId);
	}

	/**
	 * @param user
	 * @param fileData
	 * @throws IOException
	 */
	@PostMapping("/upload")
	public void uploadMusic(@AuthenticationPrincipal ArtistPrincipal user,
		@RequestBody MultipartFile fileData) throws IOException {
		// check if MEDIA_TYPE is valid
		fileService.saveFile(user.getId(), FileType.AUDIO, fileData);
	}

	@PutMapping("/publish")
	public void publishSong(@AuthenticationPrincipal ArtistPrincipal user,
		@RequestBody PublishSongRequest publishSongRequest) {
		// todo: 자기 자신만 publish song request를 보낼 수 있도록, 관련 인가 로직 구현
		analyzeService.registerPublishedInformation(publishSongRequest);
		analyzeService.publishSong(publishSongRequest.getSongId());
	}

	@GetMapping("/{songId}")
	public SimpleSongDto getSongsAnalistics(@AuthenticationPrincipal ArtistPrincipal user, @PathVariable Long songId) {
		// todo: 자기 자신만 get songs analistics 확인 가능하도록, 관련 인가 로직 구현
		return analyzeService.getSongAnalistics(songId);
	}
}
