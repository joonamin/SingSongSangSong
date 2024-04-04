package com.ssafy.singsongsangsong.repository.maria.liked;

import java.util.List;
import java.util.Optional;

import com.ssafy.singsongsangsong.entity.Likes;
import com.ssafy.singsongsangsong.entity.Song;

public interface LikedRepositoryCustom {

	public List<Likes> getLikedSongsByArtistId(Long artistId);

	public Optional<Likes> findBySongIdAndArtistId(Long songId, Long artistId);
}
