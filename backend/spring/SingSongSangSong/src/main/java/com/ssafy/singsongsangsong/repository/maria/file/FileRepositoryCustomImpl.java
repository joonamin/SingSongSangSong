package com.ssafy.singsongsangsong.repository.maria.file;

import static com.ssafy.singsongsangsong.entity.QFile.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.singsongsangsong.entity.File;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FileRepositoryCustomImpl
	implements FileRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<File> findByOriginalFileName(String originalFileName) {
		return Optional.ofNullable(queryFactory.selectFrom(file)
			.where(file.originalFileName.eq(originalFileName))
			.fetchOne());
	}
}
