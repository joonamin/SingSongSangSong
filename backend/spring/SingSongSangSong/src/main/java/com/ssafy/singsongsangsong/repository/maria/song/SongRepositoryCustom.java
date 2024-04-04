package com.ssafy.singsongsangsong.repository.maria.song;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.OrderSpecifier;
import com.ssafy.singsongsangsong.entity.Song;

public interface SongRepositoryCustom {
	public List<Song> getPublishedSongsByArtistId(Long artistId);

	public List<Song> findSongOrderByWeeklyCountDesc();

	public List<Song> findSongForGenreOrderByWeeklyCountDesc(String requestGenre);

	public List<Song> findSongForAtmosphereOrderByWeeklyCountDesc(String requestAtmosphere);

	public List<Song> findSongByBpmAndKeyword(String keyword, int startBpm, int endBpm, OrderSpecifier[] orderSpecifier,
		String requestGenre, String requestAtmosphere);

	public Optional<Song> getSongByArtistIdAndSongId(Long songId, Long artistId);

	long decrementEmotionCount(Long songId, String emotionName) throws NoSuchFieldException;

	long incrementEmotionCount(Long songId, String emotionName);

	List<Song> findByThemeName(String themeName, int size);

	void incrementPlayCount(Long songId);

	void incrementDownloadCount(Long songId);

	List<Song> findAllByArtistIdAndIsPublished(Long artistId);

	List<Song> getSongByArtistId(Long artistId);
}
