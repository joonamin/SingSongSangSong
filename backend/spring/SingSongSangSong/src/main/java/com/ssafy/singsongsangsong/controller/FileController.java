package com.ssafy.singsongsangsong.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ssafy.singsongsangsong.constants.FileType;
import com.ssafy.singsongsangsong.dto.UploadFileDto;
import com.ssafy.singsongsangsong.security.ArtistPrincipal;
import com.ssafy.singsongsangsong.service.file.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {

	private final FileService fileService;

	@PostMapping("/upload/{fileType}")
	public UploadFileDto uploadFile(@AuthenticationPrincipal ArtistPrincipal user,
		@PathVariable(name = "fileType") FileType fileType, @RequestBody MultipartFile fileData) throws IOException {

		if (user == null) {
			throw new JWTVerificationException("로그인이 필요합니다");
		}

		switch (fileType) {
			case IMAGE:
				return UploadFileDto.builder()
					.savedFileName(fileService.saveFile(user.getId(), fileType, fileData))
					.originalFileName(fileData.getOriginalFilename()).build();
			case AUDIO:
				return fileService.uploadSong(user.getId(), fileType, fileData);
			default:
				throw new MalformedURLException("upload 요청 URL이 잘못되었습니다.");
		}
	}

	@GetMapping("/download/{fileType}/{originalFileName}")
	public ResponseEntity<Resource> downloadFile(@AuthenticationPrincipal ArtistPrincipal user,
		@PathVariable("fileType") FileType fileType, @PathVariable("originalFileName") String originalFileName) throws
		IOException {

		if (user == null) {
			throw new JWTVerificationException("로그인이 필요합니다");
		}

		log.info("fileType: {}", fileType);
		log.info("originalFileName: {}", originalFileName);

		Resource file = null;

		ResponseEntity.BodyBuilder responseEntityBuilder = ResponseEntity.ok();

		if (fileType == FileType.MFCC) {
			Long mfccImageId = Long.valueOf(originalFileName);
			file = fileService.getFileViaId(user.getId(), fileType, mfccImageId);
			responseEntityBuilder.contentType(MediaType.valueOf("image/svg+xml"));
		} else if (fileType == FileType.IMAGE) {
			file = fileService.getFile(user.getId(), fileType, originalFileName);
			responseEntityBuilder.contentType(MediaType.IMAGE_JPEG);
		} else if (fileType == FileType.AUDIO) {
			file = fileService.getFile(user.getId(), fileType, originalFileName);
			responseEntityBuilder.contentType(MediaType.valueOf("audio/mpeg"));
		} else if (fileType == FileType.SPECTRUM) {
			Long spectrumImageId = Long.valueOf(originalFileName);
			file = fileService.getFileViaId(user.getId(), fileType, spectrumImageId);
			responseEntityBuilder.contentType(MediaType.IMAGE_JPEG);
		}

		return responseEntityBuilder.body(file);
	}

}
