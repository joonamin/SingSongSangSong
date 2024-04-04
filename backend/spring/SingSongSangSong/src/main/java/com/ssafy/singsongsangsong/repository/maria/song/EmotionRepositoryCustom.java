package com.ssafy.singsongsangsong.repository.maria.song;

import java.util.Optional;

import com.ssafy.singsongsangsong.constants.EmotionsConstants;
import com.ssafy.singsongsangsong.entity.Emotions;

public interface EmotionRepositoryCustom {

	Optional<java.lang.String> checkIfEmotionExists(Long songId, Long artistId);

	void updateEmotionType(Long id, Long artistId, EmotionsConstants emotionType);

	Optional<Emotions> findByArtistAndSongId(Long artistId, Long songId);
}
