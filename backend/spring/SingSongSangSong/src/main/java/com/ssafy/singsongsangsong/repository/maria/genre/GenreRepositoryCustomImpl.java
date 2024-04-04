package com.ssafy.singsongsangsong.repository.maria.genre;

import static com.ssafy.singsongsangsong.entity.QGenre.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.singsongsangsong.entity.Genre;
import com.ssafy.singsongsangsong.entity.Song;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryCustomImpl implements GenreRepositoryCustom{
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Song> genreFilterList(List<Long> songIdList, String requestGenre) {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		songIdList.stream().forEach(id -> {
			booleanBuilder.or(genre.song.id.eq(id));
		});
		return jpaQueryFactory
			.select(genre.song)
			.from(genre)
			.where(genre.mainCategory.eq(requestGenre).and(booleanBuilder))
			.fetch();
	}
	@Override
	public List<Genre> findBySongId(Long songId, int limit) {
		return jpaQueryFactory.selectFrom(genre)
			.where(genre.song.id.eq(songId))
			.limit(limit)
			.fetch();
	}
}
