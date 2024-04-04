package com.ssafy.singsongsangsong.service.file;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.singsongsangsong.constants.FileType;
import com.ssafy.singsongsangsong.dto.UploadSongDto;

public interface FileService {

	public String saveFile(Long artistId, FileType fileType, MultipartFile fileData) throws IOException;

	// img, audio
	// img => get ---
	// audio => get -- ?
	public Resource getFile(Long artistId, FileType fileType, String originalFileName) throws IOException;

	public Resource getFileViaId(Long artistId, FileType fileType, Long fileId) throws IOException;

	public UploadSongDto uploadSong(Long artistId, FileType fileType, MultipartFile fileData) throws IOException;
}
