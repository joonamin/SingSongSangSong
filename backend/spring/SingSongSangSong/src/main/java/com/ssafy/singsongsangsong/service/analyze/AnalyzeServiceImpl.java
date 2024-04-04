package com.ssafy.singsongsangsong.service.analyze;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.singsongsangsong.constants.DefaultFileName;
import com.ssafy.singsongsangsong.dto.PublishSongRequest;
import com.ssafy.singsongsangsong.dto.SimpleSongDto;
import com.ssafy.singsongsangsong.dto.UploadMainPageDto;
import com.ssafy.singsongsangsong.entity.Atmosphere;
import com.ssafy.singsongsangsong.entity.File;
import com.ssafy.singsongsangsong.entity.Song;
import com.ssafy.singsongsangsong.exception.NotYetAnalyzedException;
import com.ssafy.singsongsangsong.exception.common.BusinessException;
import com.ssafy.singsongsangsong.exception.file.NotFoundFileException;
import com.ssafy.singsongsangsong.exception.song.AlreadyCompletedException;
import com.ssafy.singsongsangsong.exception.song.NotFoundSongException;
import com.ssafy.singsongsangsong.repository.maria.atmosphere.AtmosphereRepository;
import com.ssafy.singsongsangsong.repository.maria.file.FileRepository;
import com.ssafy.singsongsangsong.repository.maria.song.SongRepository;
import com.ssafy.singsongsangsong.utils.ThemesClassifier;
import com.ssafy.singsongsangsong.webclient.WebClientRequestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalyzeServiceImpl implements AnalyzeService {

	private final SongRepository songRepository;
	private final AtmosphereRepository atmosphereRepository;
	private final ThemesClassifier themesClassifier;
	private final WebClientRequestService webClientRequestService;
	private final FileRepository fileRepository;

	@Override
	@Transactional
	public void completeAnalyze(Long songId) throws AlreadyCompletedException {
		Song song = songRepository.findById(songId).orElseThrow(() -> new NotFoundSongException("해당 곡을 찾을 수 없습니다."));
		if (song.isAnalyzed()) {
			throw new AlreadyCompletedException("이미 분석이 완료된 곡입니다.");
		}

		// 분석된 분위기를 기반으로, theme 컬럼을 초기화해줍니다.
		Atmosphere topRatedAtmosphere = atmosphereRepository.getFirstAtmosphereBySongId(songId)
			.orElseThrow(NotYetAnalyzedException::new);
		String themeName = themesClassifier.getTheme(topRatedAtmosphere.getAtmosphere());

		song.setThemes(themeName);
		song.setAnalyzed(true);
	}

	@Override
	public UploadMainPageDto getUploadStatus(Long artistId) {
		List<Song> songList = songRepository.getSongByArtistId(artistId);
		List<UploadMainPageDto.UploadProcess> uploadProcesses = new ArrayList<>();
		songList.stream().forEach(song -> uploadProcesses.add(UploadMainPageDto.UploadProcess.from(song)));
		return UploadMainPageDto.builder().uploadProcesses(uploadProcesses).build();
	}

	@Transactional
	@Override
	public void publishSong(Long songId) throws NotYetAnalyzedException {
		Song song = songRepository.findById(songId).orElseThrow(() -> new NotFoundSongException("해당 곡을 찾을 수 없습니다."));
		if (!song.isAnalyzed()) {
			throw new NotYetAnalyzedException();
		}
		// 유사도 분석을 위해, vector db에 값 저장을 위임한다.
		webClientRequestService.requestSaveSimilarity(songId);
		song.setPublished(true);
	}

	@Override
	@Transactional
	public void registerPublishedInformation(PublishSongRequest dto) {
		Song song = songRepository.findById(dto.getSongId()).orElseThrow(NotFoundSongException::new);
		File file;
		if (dto.getAlbumImageName() == null) {
			String defaultAlbumImageFile = DefaultFileName.DEFAULT_ALBUM_PICTURE.getName();
			file = fileRepository.findByOriginalFileName(defaultAlbumImageFile)
				.orElseThrow(() -> new BusinessException("기본 앨범 이미지 확인.. 서버 파일 스토리지에 Default_album 사진에 대한 정보가 없습니다."));
		} else {
			file = fileRepository.findByOriginalFileName(dto.getAlbumImageName())
				.orElseThrow(NotFoundFileException::new);
		}
		song.setAlbumImage(file);
		song.setLyrics(dto.getLyrics());
		song.setSongDescription(dto.getDescription());
		song.setPublished(true);
	}

	@Override
	public SimpleSongDto getSongAnalistics(Long songId) {
		Song song = songRepository.findById(songId).orElseThrow(() -> new NotFoundSongException("해당 곡을 찾을 수 없습니다."));
		SimpleSongDto dto = SimpleSongDto.from(song);
		dto.setAtmosphere(atmosphereRepository.getAtmosphereBySongId(songId).getFirst().getAtmosphere());
		return dto;
	}

	@Override
	public void requestAnalyze(Long artistId, Long songId) {
		// artistId와 songId에 해당하는 음악파일의 savedPath를 가져온다.
		// todo: production 환경에서는, 해당 Song이 Artist의 Song이 맞는 지 검증하는 로직이 추가적으로 필요하다.
		Song song = songRepository.findById(songId).orElseThrow(NotFoundSongException::new);
		String originalFileName = song.getMusicFileName();
		fileRepository.findByOriginalFileName(originalFileName)
			.orElseThrow(() -> new NotFoundFileException("음악 파일을 찾을 수 없습니다."));
		webClientRequestService.requestAnalyzeSong(songId);
	}
}
