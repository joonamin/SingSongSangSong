package com.ssafy.singsongsangsong.repository.maria.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.singsongsangsong.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> , GenreRepositoryCustom{
}
