package com.ssafy.singsongsangsong.repository.maria.file;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.singsongsangsong.entity.File;

public interface FileRepository extends JpaRepository<File, Long>, FileRepositoryCustom {

}
