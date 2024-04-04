package com.ssafy.singsongsangsong.repository.maria.liked;

import static com.ssafy.singsongsangsong.entity.QLikes.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.singsongsangsong.entity.Likes;
import com.ssafy.singsongsangsong.entity.Song;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LikedRepositoryCustomImpl implements LikedRepositoryCustom{
	private final JPAQueryFactory jpaQueryFactory;
	@Override
	public List<Likes> getLikedSongsByArtistId(Long artistId) {
		return jpaQueryFactory
			.selectFrom(likes)
			.where(likes.artist.id.eq(artistId))
			.orderBy(likes.id.desc())
			.fetch();
	}

	@Override
	public Optional<Likes> findBySongIdAndArtistId(Long songId, Long artistId) {
		return Optional.ofNullable(jpaQueryFactory
			.selectFrom(likes)
			.where(likes.song.id.eq(songId).and(likes.artist.id.eq(artistId)))
			.fetchOne());
	}
}
