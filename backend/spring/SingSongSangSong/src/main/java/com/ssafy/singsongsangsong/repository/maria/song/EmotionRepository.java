package com.ssafy.singsongsangsong.repository.maria.song;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.singsongsangsong.entity.Emotions;

public interface EmotionRepository extends JpaRepository<Emotions, Long>, EmotionRepositoryCustom {
}
