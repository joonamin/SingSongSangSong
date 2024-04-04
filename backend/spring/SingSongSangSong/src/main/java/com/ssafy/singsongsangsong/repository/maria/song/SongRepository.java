package com.ssafy.singsongsangsong.repository.maria.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.singsongsangsong.entity.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>, SongRepositoryCustom {
}
