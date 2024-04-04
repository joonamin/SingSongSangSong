package com.ssafy.singsongsangsong.repository.maria.song;

import static com.ssafy.singsongsangsong.entity.QEmotions.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.ssafy.singsongsangsong.constants.EmotionsConstants;
import com.ssafy.singsongsangsong.entity.Emotions;
import com.ssafy.singsongsangsong.exception.common.BusinessException;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmotionRepositoryCustomImpl implements EmotionRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<java.lang.String> checkIfEmotionExists(Long songId, Long artistId) {
		// 사용자 emotion은 최대 '1'개
		Optional<Emotions> target = Optional.ofNullable(jpaQueryFactory.selectFrom(emotions)
			.where(emotions.song.id.eq(songId).and(emotions.artist.id.eq(artistId)))
			.fetchOne());

		return target.map(value -> value.getEmotionType().getName());
	}

	@Override
	public void updateEmotionType(Long id, Long artistId, EmotionsConstants emotionType) {

		EnumPath<EmotionsConstants> path = Expressions.enumPath(EmotionsConstants.class, "emotionType");

		JPAUpdateClause updateClause = jpaQueryFactory.update(emotions)
			.set(path, emotionType)
			.where(emotions.song.id.eq(id).and(emotions.artist.id.eq(artistId)));
		if (updateClause.execute() == 0) {
			throw new BusinessException("감정 업데이트 실패!, 기존 감정이 존재하지 않습니다.");
		}
	}

	@Override
	public Optional<Emotions> findByArtistAndSongId(Long artistId, Long songId) {
		return Optional.ofNullable(jpaQueryFactory.selectFrom(emotions)
			.where(emotions.artist.id.eq(artistId).and(emotions.song.id.eq(songId)))
			.fetchOne());
	}

}
