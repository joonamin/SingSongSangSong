package com.ssafy.singsongsangsong.controller;

import java.io.IOException;
import java.util.List;

import org.simpleframework.xml.Path;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.singsongsangsong.constants.EmotionsConstants;
import com.ssafy.singsongsangsong.dto.AnalyzeGenreAndAtmosphereResponse;
import com.ssafy.singsongsangsong.dto.CommentsResponseDto;
import com.ssafy.singsongsangsong.dto.LikedResponseDto;
import com.ssafy.singsongsangsong.dto.PostCommentsDto;
import com.ssafy.singsongsangsong.dto.SectionElementDto;
import com.ssafy.singsongsangsong.dto.SongInfoResponse;
import com.ssafy.singsongsangsong.dto.SongListByThemeResponseDto;
import com.ssafy.singsongsangsong.dto.SongSimilarityByRanksResponse;
import com.ssafy.singsongsangsong.dto.SpectrumResponseDto;
import com.ssafy.singsongsangsong.security.ArtistPrincipal;
import com.ssafy.singsongsangsong.service.song.SongService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/song")
@RequiredArgsConstructor
public class SongController {

	private final SongService songService;

	@PutMapping("/{songId}/{emotionType}")
	@PreAuthorize("hasRole('USER')")
	public void updateEmotion(@AuthenticationPrincipal ArtistPrincipal user, @PathVariable(value = "songId") Long songId,
		@PathVariable(value = "emotionType") EmotionsConstants emotionType) throws
		NoSuchFieldException {
		// 기존 사용자가 해당 노래에 대해서 emotion을 남긴 경우, 해당 노래의 emotion을 삭제하고 count down 시킨 다음,
		// 새로운 감정을 추가한다. count up++
		songService.updateEmotionType(user.getId(), songId, emotionType);
	}

	@PostMapping("/comments")
	@PreAuthorize("hasRole('USER')")
	public void postComment(@AuthenticationPrincipal ArtistPrincipal user,
		@RequestBody PostCommentsDto dto) {
		// 댓글을 남긴다.
		songService.postComment(user.getId(), dto.getSongId(), dto.getContent());
	}

	@GetMapping("/comments/{songId}")
	public CommentsResponseDto getComments(@PathVariable Long songId) {
		return songService.getComments(songId);
	}

	/**
	 * 특정 테마에 해당하는 곡 리스트 반환
	 */
	@GetMapping("/theme/{themeName}")
	public SongListByThemeResponseDto getSongListByTheme(@PathVariable String themeName,
		@RequestParam(defaultValue = "10") int size) {
		return songService.getSongListByTheme(themeName, size);
	}

	@GetMapping("/detail/{songId}")
	public SongInfoResponse getSong(@PathVariable(value = "songId") Long songId) {
		return songService.getSong(songId);
	}

	// 해당 노래에 해당하는 분위기와 장르의 correlation을 가져옵니다.
	@GetMapping("/analyze/{songId}")
	public AnalyzeGenreAndAtmosphereResponse getAnalyzeGenreAndAtmosphere(@PathVariable Long songId,
		@RequestParam(required = false, defaultValue = "5") int size) {
		return songService.getAnalyzeGenreAndAtmosphere(songId, size);
	}

	@PostMapping("/play/{songId}")
	public void playSong(@PathVariable Long songId, @AuthenticationPrincipal ArtistPrincipal user) {
		songService.playSong(user.getId(), songId);
	}

	@PostMapping("/download/{songId}")
	public Resource downloadSong(@PathVariable Long songId, @AuthenticationPrincipal ArtistPrincipal user) throws
		IOException {
		return songService.downloadSong(user.getId(), songId);
	}

	@GetMapping("/similarity/{songId}")
	public SongSimilarityByRanksResponse getSongsSimilarityByRanks(@PathVariable Long songId,
		@RequestParam(required = false, defaultValue = "5", name = "size") int size) {
		return songService.getSongsSimilarityByRanks(songId, size);
	}

	@GetMapping("/section/{songId}")
	public List<SectionElementDto> getSectionOfSong(@PathVariable(value = "songId") Long songId) {
		return songService.getSectionOfSong(songId);
	}

	@GetMapping("/spectrum/{songId}")
	public SpectrumResponseDto getSpectrumId(@PathVariable(value = "songId") Long songId) {
		return songService.getSpectrumId(songId);
	}
	@PostMapping("/liked/{songId}")
	public LikedResponseDto likedSong(@PathVariable(value = "songId") Long songId,
		@AuthenticationPrincipal ArtistPrincipal user) {
		return songService.likedSong(songId, user.getId());
	}
}
