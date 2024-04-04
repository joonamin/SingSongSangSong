package com.ssafy.singsongsangsong.service.song;

import static com.ssafy.singsongsangsong.webclient.WebClientRequestService.SimilarityResponse.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.singsongsangsong.annotation.CsvFileContents;
import com.ssafy.singsongsangsong.annotation.ExportCsvFile;
import com.ssafy.singsongsangsong.constants.DefaultFileName;
import com.ssafy.singsongsangsong.constants.EmotionsConstants;
import com.ssafy.singsongsangsong.constants.FileType;
import com.ssafy.singsongsangsong.dto.AnalyzeGenreAndAtmosphereResponse;
import com.ssafy.singsongsangsong.dto.AnalyzeGenreAndAtmosphereResponse.AnalyzeAtmosphereDto;
import com.ssafy.singsongsangsong.dto.AnalyzeGenreAndAtmosphereResponse.AnalyzeGenreDto;
import com.ssafy.singsongsangsong.dto.ArtistInfoDto;
import com.ssafy.singsongsangsong.dto.CommentsInfoDto;
import com.ssafy.singsongsangsong.dto.CommentsResponseDto;
import com.ssafy.singsongsangsong.dto.CommentsResponseDto.CommentsResponse;
import com.ssafy.singsongsangsong.dto.LikedResponseDto;
import com.ssafy.singsongsangsong.dto.SectionElementDto;
import com.ssafy.singsongsangsong.dto.SimpleSongDto;
import com.ssafy.singsongsangsong.dto.SongInfoResponse;
import com.ssafy.singsongsangsong.dto.SongInfoResponse.SongInfoResponseBuilder;
import com.ssafy.singsongsangsong.dto.SongListByThemeResponseDto;
import com.ssafy.singsongsangsong.dto.SongSimilarityByRanksResponse;
import com.ssafy.singsongsangsong.dto.SongSimilarityByRanksResponse.Comparison;
import com.ssafy.singsongsangsong.dto.SpectrumResponseDto;
import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.entity.Atmosphere;
import com.ssafy.singsongsangsong.entity.Comments;
import com.ssafy.singsongsangsong.entity.Emotions;
import com.ssafy.singsongsangsong.entity.Genre;
import com.ssafy.singsongsangsong.entity.Likes;
import com.ssafy.singsongsangsong.entity.Song;
import com.ssafy.singsongsangsong.exception.artist.ArtistNotFoundException;
import com.ssafy.singsongsangsong.exception.common.BusinessException;
import com.ssafy.singsongsangsong.exception.song.NotFoundSongException;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;
import com.ssafy.singsongsangsong.repository.maria.atmosphere.AtmosphereRepository;
import com.ssafy.singsongsangsong.repository.maria.comments.CommentsRepository;
import com.ssafy.singsongsangsong.repository.maria.file.FileRepository;
import com.ssafy.singsongsangsong.repository.maria.genre.GenreRepository;
import com.ssafy.singsongsangsong.repository.maria.liked.LikedRepository;
import com.ssafy.singsongsangsong.repository.maria.song.EmotionRepository;
import com.ssafy.singsongsangsong.repository.maria.song.SongRepository;
import com.ssafy.singsongsangsong.repository.maria.song.StructureRepository;
import com.ssafy.singsongsangsong.service.file.FileService;
import com.ssafy.singsongsangsong.webclient.WebClientRequestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

	private final SongRepository songRepository;

	private final EmotionRepository emotionRepository;

	private final ArtistRepository artistRepository;

	private final CommentsRepository commentsRepository;

	private final GenreRepository genreRepository;

	private final AtmosphereRepository atmosphereRepository;

	private final StructureRepository structureRepository;

	private final WebClientRequestService webClientRequestService;

	private final FileRepository fileRepository;

	private final LikedRepository likedRepository;

	private final FileService fileService;

	@Override
	@Transactional
	public void updateEmotionType(Long artistId, Long songId, EmotionsConstants emotionType) throws
		NoSuchFieldException {
		// 기존 사용자가 해당 노래에 대해서 emotion을 남긴 경우, 해당 노래의 emotion을 삭제하고 count down 시킨 다음,
		// 새로운 감정을 추가한다. count up++
		Artist artist = artistRepository.findById(artistId).orElseThrow(ArtistNotFoundException::new);
		Song song = songRepository.findById(songId).orElseThrow(NotFoundSongException::new);
		Optional<java.lang.String> targetEmotion = emotionRepository.checkIfEmotionExists(song.getId(), artistId);
		if (targetEmotion.isPresent()) {
			java.lang.String previousEmotionName = targetEmotion.get();

			// 기존 emotion 업데이트 및 반정규화된 table, count 조정
			emotionRepository.updateEmotionType(song.getId(), artistId, emotionType);
			songRepository.decrementEmotionCount(song.getId(), previousEmotionName);
			songRepository.incrementEmotionCount(song.getId(), emotionType.getName());
		} else {
			// emotion 추가 및 반정규화된 table, count++
			emotionRepository.save(Emotions.builder()
				.song(song)
				.artist(artist)
				.emotionType(emotionType)
				.build());
			songRepository.incrementEmotionCount(song.getId(), emotionType.getName());
		}
	}

	@Override
	@Transactional
	public void postComment(Long artistId, Long songId, String content) {
		// 댓글을 남긴다
		Artist artist = artistRepository.findById(artistId).orElseThrow(ArtistNotFoundException::new);
		Song song = songRepository.findById(songId).orElseThrow(NotFoundSongException::new);
		commentsRepository.save(Comments.builder().artist(artist).song(song).content(content).build());
	}

	@Override
	public CommentsResponseDto getComments(Long songId) {
		List<CommentsResponse> comments = commentsRepository.findBySongId(songId)
			.stream()
			.map(CommentsResponse::from)
			.toList();
		return CommentsResponseDto.builder().comments(comments).build();
	}

	@Override
	public SongListByThemeResponseDto getSongListByTheme(String themeName, int size) {
		List<Song> songs = songRepository.findByThemeName(themeName, size);
		return SongListByThemeResponseDto.builder()
			.size(songs.size())
			.songList(songs.stream().map(SimpleSongDto::from).toList())
			.build();
	}

	@Override
	public SongInfoResponse getSong(Long songId) {
		Song song = songRepository.findById(songId).orElseThrow(NotFoundSongException::new);
		Artist artist = song.getArtist();
		List<Comments> commentsList = commentsRepository.findBySongId(songId);
		SongInfoResponseBuilder builder = SongInfoResponse.builder();

		String musicFileName = Optional.ofNullable(song.getMusicFileName())
			.orElseGet(DefaultFileName.DEFAULT_PROFILE_PICTURE::getName);

		String originalFileName;
		if (song.getAlbumImage() != null && song.getAlbumImage().getOriginalFileName() != null) {
			originalFileName = song.getAlbumImage().getOriginalFileName();
		} else {
			originalFileName = DefaultFileName.DEFAULT_PROFILE_PICTURE.getName();
		}

		Long spectrumId = -1L;
		if (song.getMfccImage() == null) {
			spectrumId = fileRepository.findByOriginalFileName(DefaultFileName.DEFAULT_NOT_YET_IMAGE.getName()).get()
				.getId();
		} else {
			spectrumId = song.getSpectrumImage().getId();
		}

		Long mfccId = -1L;
		if (song.getMfccImage() == null) {
			mfccId = fileRepository.findByOriginalFileName(DefaultFileName.DEFAULT_NOT_YET_IMAGE.getName()).get()
				.getId();
		} else {
			mfccId = song.getMfccImage().getId();
		}

		builder = builder.songTitle(song.getTitle())
			.artist(ArtistInfoDto.from(artist))
			.lyrics(song.getLyrics())
			.chord(song.getChord())
			.bpm(song.getBpm())
			.songFileName(musicFileName)
			.albumImageFileName(originalFileName)
			.songDescription(song.getSongDescription())
			.spectrumImageId(spectrumId)
			.mfccImageId(mfccId);

		builder = builder.movedEmotionCount(song.getMovedEmotionCount())
			.likeEmotionCount(song.getLikeEmotionCount())
			.energizedEmotionCount(song.getEnergizedEmotionCount())
			.excitedEmotionCount(song.getExcitedEmotionCount())
			.funnyEmotionCount(song.getFunnyEmotionCount())
			.sadEmotionCount(song.getSadEmotionCount());

		List<CommentsInfoDto> comments = commentsList.stream().map(CommentsInfoDto::from).toList();
		builder = builder.likeCount(song.getLikeCount())
			.downloadCount(song.getDownloadCount())
			.playCount(song.getPlayCount());
		builder = builder.comments(comments);

		return builder.build();
	}

	@Override
	public AnalyzeGenreAndAtmosphereResponse getAnalyzeGenreAndAtmosphere(Long songId, int size) {
		List<Genre> genres = genreRepository.findBySongId(songId, size);
		List<Atmosphere> atmospheres = atmosphereRepository.findBySongId(songId, size);

		return AnalyzeGenreAndAtmosphereResponse.builder()
			.genreLength(genres.size())
			.atmosphereLength(atmospheres.size())
			.genres(genres.stream().map(AnalyzeGenreDto::from).toList())
			.atmospheres(atmospheres.stream().map(AnalyzeAtmosphereDto::from).toList())
			.build();
	}

	@Transactional
	@ExportCsvFile(format = CsvFileContents.ARTIST_SONG_RECORD)
	public void playSong(Long artistId, Long songId) {
		// 노래 재생 횟수 증가 및 CSV파일로 로그 저장 (통계적 분석 위함)
		songRepository.findById(songId).orElseThrow(NotFoundSongException::new);
		artistRepository.findById(artistId).orElseThrow(ArtistNotFoundException::new);
		songRepository.incrementPlayCount(songId);
	}

	@Override
	@Transactional
	@ExportCsvFile(format = CsvFileContents.ARTIST_SONG_RECORD)
	public Resource downloadSong(Long artistId, Long songId) throws IOException {
		// 노래 다운로드 횟수 증가 및 CSV파일로 로그 저장 (통계적 분석 위함)
		Song song = songRepository.findById(songId).orElseThrow(NotFoundSongException::new);
		songRepository.incrementDownloadCount(songId);

		String musicFileName = song.getMusicFileName();
		return fileService.getFile(artistId, FileType.IMAGE, musicFileName);
	}

	@Override
	public SongSimilarityByRanksResponse getSongsSimilarityByRanks(Long songId, int size) {

		if (size <= 0) {
			throw new IllegalArgumentException("size는 0보다 커야합니다.");
		}

		Song song = songRepository.findById(songId).orElseThrow(NotFoundSongException::new);
		List<SimilarityInfo> retrieved = webClientRequestService.requestSelectSimilarity(songId);
		Collections.sort(retrieved);

		List<Comparison> comparison = retrieved.stream().map(similarityInfo -> {
			Long targetId = similarityInfo.getId();
			Song targetSong = songRepository.findById(targetId).orElseThrow(NotFoundSongException::new);
			String originalFileName;

			if (targetSong.getAlbumImage().getOriginalFileName() != null) {
				originalFileName = targetSong.getAlbumImage().getOriginalFileName();
			} else {
				originalFileName = DefaultFileName.DEFAULT_PROFILE_PICTURE.getName();
			}

			return Comparison.builder()
				.target(Comparison.Target.builder()
					.songId(targetSong.getId())
					.albumImageFileName(originalFileName)
					.title(targetSong.getTitle())
					.createdDate(targetSong.getCreatedDate())
					.build())
				.correlation(similarityInfo.getDistance())
				.build();
		}).toList();

		String originalAlbumImageFile;
		if (song.getAlbumImage() != null && song.getAlbumImage().getOriginalFileName() != null) {
			originalAlbumImageFile = song.getAlbumImage().getOriginalFileName();
		} else {
			originalAlbumImageFile = DefaultFileName.DEFAULT_ALBUM_PICTURE.getName();
		}

		SongSimilarityByRanksResponse response = SongSimilarityByRanksResponse.builder()
			.size(size)
			.albumImageFileName(originalAlbumImageFile)
			.title(song.getTitle())
			.createdDate(song.getCreatedDate())
			.comparison(comparison.subList(0, size - 1))
			.build();

		return response;
	}

	@Override
	public List<SectionElementDto> getSectionOfSong(Long songId) {
		return structureRepository.getStructureBySongId(songId)
			.stream()
			.map(SectionElementDto::from)
			.toList();
	}



	@Override
	public LikedResponseDto likedSong(Long songId, Long artistId) {
		Song song = songRepository.findById(songId).orElseThrow(() -> new NotFoundSongException("유효하지 않은 곡입니다."));
		Optional<Likes> isLike = likedRepository.findBySongIdAndArtistId(songId, artistId);
		String result;
		if (isLike.isPresent()) {
			Likes like = isLike.get();
			likedRepository.delete(like);
			song.setLikeCount(song.getLikeCount() - 1);
			result = "UnRegister Liked";
		} else {
			Likes like = new Likes();
			like.setSong(song);
			like.setArtist(
				artistRepository.findById(artistId).orElseThrow(() -> new ArtistNotFoundException("유효하지 않은 아티스트입니다.")));
			likedRepository.save(like);
			song.setLikeCount(song.getLikeCount() + 1);
			result = "Register Liked";
		}
		songRepository.save(song);
		return LikedResponseDto.from(result);
	}

	@Override
	public SpectrumResponseDto getSpectrumId(Long songId) {
		Long spectrumId = songRepository.findById(songId).orElseThrow(() -> new BusinessException("노래를 찾지 못했습니다."))
			.getSpectrumImage().getId();
		return SpectrumResponseDto.from(spectrumId);
	}

}
