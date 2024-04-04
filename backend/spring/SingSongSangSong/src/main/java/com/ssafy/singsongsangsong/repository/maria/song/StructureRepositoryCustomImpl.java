package com.ssafy.singsongsangsong.repository.maria.song;

import static com.ssafy.singsongsangsong.entity.QStructure.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.singsongsangsong.entity.Structure;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class StructureRepositoryCustomImpl
	implements StructureRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Structure> getStructureBySongId(Long songId) {
		return jpaQueryFactory.selectFrom(structure)
			.where(structure.song.id.eq(songId))
			.fetch();
	}
}
