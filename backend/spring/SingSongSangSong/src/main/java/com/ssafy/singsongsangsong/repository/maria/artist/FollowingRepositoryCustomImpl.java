package com.ssafy.singsongsangsong.repository.maria.artist;

import static com.ssafy.singsongsangsong.entity.QFollower_Following.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.singsongsangsong.entity.Follower_Following;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FollowingRepositoryCustomImpl implements FollowingRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Follower_Following> getFollowingWhere(Long follower, Long following) {
		return Optional.ofNullable(queryFactory.selectFrom(follower_Following)
			.where(follower_Following.from.id.eq(follower).and(follower_Following.to.id.eq(following)))
			.fetchOne());
	}
}
