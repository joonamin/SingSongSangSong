package com.ssafy.singsongsangsong.repository.maria.song;

import java.util.List;

import com.ssafy.singsongsangsong.entity.Structure;

public interface StructureRepositoryCustom {
	List<Structure> getStructureBySongId(Long songId);
}
