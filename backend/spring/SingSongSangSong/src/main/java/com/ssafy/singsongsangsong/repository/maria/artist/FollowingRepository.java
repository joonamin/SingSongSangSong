package com.ssafy.singsongsangsong.repository.maria.artist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.singsongsangsong.entity.Follower_Following;

@Repository
public interface FollowingRepository extends JpaRepository<Follower_Following, Long>, FollowingRepositoryCustom {
}
