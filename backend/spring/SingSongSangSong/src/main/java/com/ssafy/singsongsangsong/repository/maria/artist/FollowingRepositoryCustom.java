package com.ssafy.singsongsangsong.repository.maria.artist;

import java.util.Optional;

import com.ssafy.singsongsangsong.entity.Follower_Following;

public interface FollowingRepositoryCustom {
	Optional<Follower_Following> getFollowingWhere(Long follower, Long following);
}
