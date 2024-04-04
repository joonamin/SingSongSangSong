package com.ssafy.singsongsangsong.repository.maria.artist;

import java.util.List;

import com.querydsl.core.Tuple;
import com.ssafy.singsongsangsong.dto.HotArtistDto;
import com.ssafy.singsongsangsong.dto.HotArtistResponseDto;
import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.entity.Song;

public interface ArtistRepositoryCustom {
	int getLikeCountByArtistId(Long artistId);

	int getSongCountByArtistId(Long artistId);

	int getFollowerCountByArtistId(Long artistId);

	List<Song> getPublishedSongsByArtistId(Long artistId);

	List<Artist> getFollowArtistByArtistId(Long artistId);
	List<Artist> findArtistBySearchParam(String keyword);
	List<HotArtistDto> findHotArtist();
}
