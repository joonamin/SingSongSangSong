package com.ssafy.singsongsangsong.repository.maria.atmosphere;

import static com.ssafy.singsongsangsong.entity.QAtmosphere.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.singsongsangsong.entity.Atmosphere;
import com.ssafy.singsongsangsong.entity.Song;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AtmosphereRepositoryCustomImpl
	implements AtmosphereRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Atmosphere> getAtmosphereBySongId(Long songId) {
		return queryFactory.selectFrom(atmosphere1)
			.where(atmosphere1.song.id.eq(songId))
			.fetch();
	}
	@Override
	public List<Song> atmosphereFilterList(List<Long> songIdList, String requestAtmosphere) {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		songIdList.stream().forEach(id->{
			booleanBuilder.or(atmosphere1.song.id.eq(id));
		});
		return queryFactory
			.select(atmosphere1.song)
			.from(atmosphere1)
			.where(atmosphere1.atmosphere.eq(requestAtmosphere).and(booleanBuilder))
			.fetch();
	}
	@Override
	public List<Atmosphere> findBySongId(Long songId, int limit) {
		return queryFactory.selectFrom(atmosphere1)
			.where(atmosphere1.song.id.eq(songId))
			.limit(limit)
			.fetch();
	}

	@Override
	public Optional<Atmosphere> getFirstAtmosphereBySongId(Long songId) {
		return Optional.ofNullable(queryFactory.selectFrom(atmosphere1)
			.where(atmosphere1.song.id.eq(songId))
			.orderBy(atmosphere1.correlation.desc())
			.fetchFirst());
	}
}
