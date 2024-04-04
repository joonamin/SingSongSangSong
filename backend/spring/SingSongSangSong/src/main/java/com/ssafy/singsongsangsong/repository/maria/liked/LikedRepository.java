package com.ssafy.singsongsangsong.repository.maria.liked;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.singsongsangsong.entity.Likes;
import com.ssafy.singsongsangsong.entity.Song;

public interface LikedRepository extends JpaRepository<Likes, Long>, LikedRepositoryCustom {
}
