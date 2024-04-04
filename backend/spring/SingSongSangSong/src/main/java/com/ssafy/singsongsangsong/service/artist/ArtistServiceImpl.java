package com.ssafy.singsongsangsong.service.artist;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.singsongsangsong.constants.DefaultFileName;
import com.ssafy.singsongsangsong.constants.FileType;
import com.ssafy.singsongsangsong.constants.Role;
import com.ssafy.singsongsangsong.dto.ArtistDetailDto;
import com.ssafy.singsongsangsong.dto.ArtistInfoDto;
import com.ssafy.singsongsangsong.dto.EmotionsDto;
import com.ssafy.singsongsangsong.dto.FollowerCountResponse;
import com.ssafy.singsongsangsong.dto.GuestJoinRequestDto;
import com.ssafy.singsongsangsong.dto.MyProfileResponse;
import com.ssafy.singsongsangsong.dto.SimpleSongDto;
import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.entity.File;
import com.ssafy.singsongsangsong.entity.Follower_Following;
import com.ssafy.singsongsangsong.entity.Song;
import com.ssafy.singsongsangsong.exception.artist.ArtistNotFoundException;
import com.ssafy.singsongsangsong.exception.common.BusinessException;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;
import com.ssafy.singsongsangsong.repository.maria.artist.FollowingRepository;
import com.ssafy.singsongsangsong.repository.maria.file.FileRepository;
import com.ssafy.singsongsangsong.repository.maria.song.SongRepository;
import com.ssafy.singsongsangsong.service.file.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistServiceImpl implements ArtistService {
	private final ArtistRepository artistRepository;
	private final SongRepository songRepository;
	private final FollowingRepository followingRepository;
	private final FileRepository fileRepository;
	private final FileService fileService;

	@Transactional
	@Override
	public void join(String username, GuestJoinRequestDto dto) throws IOException {
		Artist artist = artistRepository.findByUsername(username).orElseThrow();
		artist.setAge(dto.getAge());
		artist.setIntroduction(dto.getIntroduction());
		artist.setNickname(dto.getNickname());
		artist.setRole(Role.USER);
		artist.setSex(dto.getSex());

		Optional<MultipartFile> profileImage = Optional.ofNullable(dto.getProfileImage());

		if (profileImage.isPresent()) {
			String originalFileName = profileImage.get().getOriginalFilename();
			String savedFileName = fileService.saveFile(artist.getId(), FileType.IMAGE, profileImage.get());
			artist.setProfileImage(File.builder()
				.originalFileName(originalFileName)
				.savedFileName(savedFileName)
				.build());
		} else {
			File defaultImageFile = fileRepository.findByOriginalFileName(
					DefaultFileName.DEFAULT_PROFILE_PICTURE.getName())
				.orElseThrow(
					() -> new BusinessException("기본 프로필 이미지 확인.. 서버 파일 스토리지에 Default_Profile 사진에 대한 정보가 없습니다."));
			artist.setProfileImage(defaultImageFile);
		}

		log.info("GuestJoinRequestDto : {}", dto);
		artistRepository.save(artist);
	}

	@Override
	public ArtistDetailDto getArtistInfo(Long artistId) {
		Artist artist = artistRepository.findById(artistId)
			.orElseThrow(() -> new IllegalArgumentException("해당 아티스트가 존재하지 않습니다."));
		List<Song> publishedSongList = songRepository.findAllByArtistIdAndIsPublished(artistId);
		int publishedSongCount = publishedSongList.size();
		Map<String, Integer> preferGenre = new HashMap<>();
		Map<String, Integer> preferAtmosphere = new HashMap<>();
		for (Song song : publishedSongList) {
			String genre = song.getCustomGenre();
			String countGenre = genre.replace(" ", "").toLowerCase() + "Count";
			int previousCount = preferGenre.getOrDefault(countGenre, 0);
			preferGenre.put(countGenre, previousCount + 1);

			String atmosphere = song.getThemes();
			previousCount = preferAtmosphere.getOrDefault(atmosphere, 0);
			preferAtmosphere.put(atmosphere, previousCount + 1);
		}
		return ArtistDetailDto.from(ArtistInfoDto.from(artist), publishedSongCount, preferGenre, preferAtmosphere);
	}

	@Override
	public List<SimpleSongDto> getPublishedSong(Long artistId) {
		return artistRepository.getPublishedSongsByArtistId(artistId)
			.stream()
			.map(SimpleSongDto::from)
			.toList();
	}

	@Override
	public void toggleFollowArtist(String username, Long followingId) throws ArtistNotFoundException {
		Artist follower = artistRepository.findByUsername(username)
			.orElseThrow(() -> new ArtistNotFoundException("해당 유저가 존재하지 않습니다."));

		Artist following = artistRepository.findById(followingId)
			.orElseThrow(() -> new ArtistNotFoundException("해당 아티스트가 존재하지 않습니다."));

		// 기존에 좋아요를 눌렀다면, 좋아요를 취소합니다.
		followingRepository.getFollowingWhere(follower.getId(), followingId)
			.ifPresentOrElse(
				followingRepository::delete,
				() -> followingRepository.save(
					Follower_Following.of(follower, following)));
	}

	@Override
	public EmotionsDto getEmotions(Long artistId) {
		List<Song> publishedSongs = songRepository.getPublishedSongsByArtistId(artistId);
		EmotionsDto result = new EmotionsDto();
		for (Song song : publishedSongs) {
			result.setEnergizedEmotionCount(result.getEnergizedEmotionCount() + song.getEnergizedEmotionCount());
			result.setExcitedEmotionCount(result.getExcitedEmotionCount() + song.getExcitedEmotionCount());
			result.setFunnyEmotionCount(result.getFunnyEmotionCount() + song.getFunnyEmotionCount());
			result.setSadEmotionCount(result.getSadEmotionCount() + song.getSadEmotionCount());
			result.setMovedEmotionCount(result.getMovedEmotionCount() + song.getMovedEmotionCount());
			result.setLikeEmotionCount(result.getLikeEmotionCount() + song.getLikeEmotionCount());
		}
		return result;
	}

	@Override
	public FollowerCountResponse getFollowerCount(Long artistId) {
		int followerCount = artistRepository.getFollowerCountByArtistId(artistId);
		return FollowerCountResponse.builder()
			.artistId(artistId)
			.followerCount(followerCount)
			.build();
	}

	@Override
	public MyProfileResponse getMyProfile(Long id) {
		Artist artist = artistRepository.findById(id).orElseThrow(ArtistNotFoundException::new);
		return MyProfileResponse.builder()
			.id(id)
			.username(artist.getUsername())
			.nickname(artist.getNickname())
			.build();
	}
}
