package com.ssafy.singsongsangsong.repository.maria.artist;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.singsongsangsong.entity.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long>, ArtistRepositoryCustom {
	Optional<Artist> findByUsername(String username);
}
