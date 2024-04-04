package com.ssafy.singsongsangsong.repository.maria.genre;

import java.util.List;

import com.ssafy.singsongsangsong.entity.Genre;
import com.ssafy.singsongsangsong.entity.Song;

public interface GenreRepositoryCustom {
	List<Song> genreFilterList(List<Long> songIdList,String requestGenre);
	List<Genre> findBySongId(Long songId, int limit);
}
