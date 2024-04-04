package com.ssafy.singsongsangsong.repository.maria.file;

import java.util.Optional;

import com.ssafy.singsongsangsong.entity.File;

public interface FileRepositoryCustom {
	Optional<File> findByOriginalFileName(String originalFileName);

}
