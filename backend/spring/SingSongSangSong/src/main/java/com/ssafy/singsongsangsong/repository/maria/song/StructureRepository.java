package com.ssafy.singsongsangsong.repository.maria.song;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.singsongsangsong.entity.Structure;

public interface StructureRepository extends JpaRepository<Structure, Long>, StructureRepositoryCustom {
}
