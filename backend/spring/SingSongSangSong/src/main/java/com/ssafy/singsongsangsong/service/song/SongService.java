package com.ssafy.singsongsangsong.service.song;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;

import com.ssafy.singsongsangsong.constants.EmotionsConstants;
import com.ssafy.singsongsangsong.dto.AnalyzeGenreAndAtmosphereResponse;
import com.ssafy.singsongsangsong.dto.CommentsResponseDto;
import com.ssafy.singsongsangsong.dto.LikedResponseDto;
import com.ssafy.singsongsangsong.dto.SectionElementDto;
import com.ssafy.singsongsangsong.dto.SongInfoResponse;
import com.ssafy.singsongsangsong.dto.SongListByThemeResponseDto;
import com.ssafy.singsongsangsong.dto.SongSimilarityByRanksResponse;
import com.ssafy.singsongsangsong.dto.SpectrumResponseDto;

public interface SongService {

	void updateEmotionType(Long artistId, Long songId, EmotionsConstants emotionType) throws NoSuchFieldException;

	void postComment(Long artistId, Long songId, String content);

	CommentsResponseDto getComments(Long songId);

	SongListByThemeResponseDto getSongListByTheme(String themeName, int size);

	SongInfoResponse getSong(Long songId);

	AnalyzeGenreAndAtmosphereResponse getAnalyzeGenreAndAtmosphere(Long songId, int size);

	void playSong(Long artistId, Long songId);

	Resource downloadSong(Long artistId, Long songId) throws IOException;

	SongSimilarityByRanksResponse getSongsSimilarityByRanks(Long songId, int size);

	List<SectionElementDto> getSectionOfSong(Long songId);

	LikedResponseDto likedSong(Long songId, Long artistId);

	SpectrumResponseDto getSpectrumId(Long songId);
}
