package com.ssafy.singsongsangsong.service.song;

import java.util.List;

import com.ssafy.singsongsangsong.dto.ArtistInfoDto;
import com.ssafy.singsongsangsong.dto.HotArtistResponseDto;
import com.ssafy.singsongsangsong.dto.LikedPageResponseDto;
import com.ssafy.singsongsangsong.dto.SearchResponseDto;
import com.ssafy.singsongsangsong.dto.SongBriefDto;

public interface SongPlayListService {
	public LikedPageResponseDto getLikedPagination(String username, int pageNo);

	public List<SongBriefDto> getWeeklyHitSongList();

	public List<SongBriefDto> getGenreHitSongList(String genre);

	public List<SongBriefDto> getAtmosphereHitSongList(String genre);

	public List<ArtistInfoDto> getFollowArtistList(String username);

	public SearchResponseDto searchArtistAndSong(String keyword, String genre, String atmosphere, Integer bpm,
		String sort);

	public List<HotArtistResponseDto> getHotArtist();
}
