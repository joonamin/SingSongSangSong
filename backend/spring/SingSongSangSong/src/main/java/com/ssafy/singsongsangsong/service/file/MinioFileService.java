package com.ssafy.singsongsangsong.service.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.singsongsangsong.constants.DefaultFileName;
import com.ssafy.singsongsangsong.constants.FileType;
import com.ssafy.singsongsangsong.dto.UploadSongDto;
import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.entity.File;
import com.ssafy.singsongsangsong.entity.Song;
import com.ssafy.singsongsangsong.exception.DuplicatedFileException;
import com.ssafy.singsongsangsong.exception.artist.ArtistNotFoundException;
import com.ssafy.singsongsangsong.exception.common.BusinessException;
import com.ssafy.singsongsangsong.exception.file.NotFoundFileException;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;
import com.ssafy.singsongsangsong.repository.maria.file.FileRepository;
import com.ssafy.singsongsangsong.repository.maria.song.SongRepository;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class MinioFileService implements FileService {

	private final MinioClient minioClient;
	private final FileRepository fileRepository;
	private final SongRepository songRepository;
	private final ArtistRepository artistRepository;

	@Override
	@Transactional
	public String saveFile(Long artistId, FileType fileType, MultipartFile fileData) throws IOException {

		if (fileRepository.findByOriginalFileName(fileData.getOriginalFilename()).isPresent()) {
			throw new DuplicatedFileException("동일한 파일을 중복하여 업로드 할 수 없습니다");
		}
		String savedFileName = uploadToMinIo(fileType.getName(), fileData);
		log.info("original: {} => saved: {}", fileData.getOriginalFilename(), savedFileName);
		fileRepository.save(File.of(savedFileName, fileData.getOriginalFilename(), artistId));
		return savedFileName;
	}

	@Override
	public Resource getFile(Long artistId, FileType fileType, String originalFileName) throws IOException {
		File file = fileRepository.findByOriginalFileName(originalFileName).orElseThrow(NotFoundFileException::new);
		log.info("getFile => originalFileName: {}", originalFileName);

		String bucket = fileType.getName();
		if (fileType == FileType.MFCC || fileType == FileType.SPECTRUM) {
			bucket = FileType.IMAGE.getName();
		}
		log.info("get file => bucket: {}, savedFileName: {}", bucket, file.getSavedFileName());

		try (InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
			.bucket(bucket)
			.object(file.getSavedFileName())
			.build())) {
			return new ByteArrayResource(inputStream.readAllBytes());
		} catch (Exception e) {
			throw new BusinessException("minio storage에서 파일을 다운받지 못했습니다 => reason: " + e.getMessage());
		}
	}

	@Override
	public Resource getFileViaId(Long artistId, FileType fileType, Long fileId) throws IOException {
		File file = fileRepository.findById(fileId)
			.orElseThrow(() -> new NotFoundFileException(String.valueOf(fileId) + "에 해당하는 file이 없습니다."));
		return getFile(artistId, fileType, file.getOriginalFileName());
	}

	@Override
	@Transactional
	public UploadSongDto uploadSong(Long artistId, FileType fileType, MultipartFile fileData) throws IOException {
		String savedFileName = saveFile(artistId, fileType, fileData);
		Artist artist = artistRepository.findById(artistId).orElseThrow(ArtistNotFoundException::new);
		File defaultAlbumImage = fileRepository.findByOriginalFileName(DefaultFileName.DEFAULT_ALBUM_PICTURE.getName())
			.orElseThrow(() -> new BusinessException("기본 앨범 이미지 확인.. 서버 파일 스토리지에 Default_Album 사진에 대한 정보가 없습니다."));
		Song song = songRepository.save(
			Song.builder()
				.artist(artist)
				.musicFileName(fileData.getOriginalFilename())
				.albumImage(defaultAlbumImage)
				.build());
		return new UploadSongDto(song.getId(), fileData.getOriginalFilename(), savedFileName);
	}

	private String uploadToMinIo(String bucket, MultipartFile file) {
		String savedFileName = UUID.randomUUID().toString();

		try {
			minioClient.putObject(PutObjectArgs.builder()
				.bucket(bucket)
				.object(savedFileName)
				.stream(file.getInputStream(), file.getSize(), -1)
				.contentType(file.getContentType())
				.build());
		} catch (Exception e) {
			log.error(e.toString());
		}

		return savedFileName;
	}

}
