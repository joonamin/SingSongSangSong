package com.ssafy.singsongsangsong.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.singsongsangsong.constants.DefaultFileName;
import com.ssafy.singsongsangsong.dto.ArtistDetailDto;
import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.entity.File;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;
import com.ssafy.singsongsangsong.repository.maria.artist.FollowingRepository;
import com.ssafy.singsongsangsong.service.artist.ArtistService;

@SpringBootTest()
@ActiveProfiles("test")
public class ArtistServiceTest {

	@Autowired
	ArtistService artistService;

	@Autowired
	ArtistRepository artistRepository;

	@Autowired
	FollowingRepository followingRepository;

	static Artist validArtist1;
	static Artist validArtist2;
	static Artist invalidArtist;

	@BeforeAll
	public static void init() {
		validArtist1 = Artist.builder()
			.id(1L)
			.age(20)
			.sex('M')
			.nickname("nickname1")
			.username("username1")
			.password("1234")
			.introduction("introduction1")
			.profileImage(null)
			.build();

		validArtist2 = Artist.builder()
			.id(2L)
			.age(20)
			.sex('F')
			.nickname("nickname2")
			.username("username2")
			.password("1234")
			.introduction("introduction2")
			.profileImage(null)
			.build();

	}

	@Test
	public void getArtistInfoTest() {
		Artist saved = artistRepository.save(validArtist1);

		File profileImage = Optional.ofNullable(saved.getProfileImage()).orElseGet(() -> null);
		String savedImageFileName = Optional.ofNullable(profileImage)
			.map(File::getSavedFileName)
			.orElseGet(DefaultFileName.DEFAULT_PROFILE_PICTURE::getName);

		ArtistDetailDto artistInfo = artistService.getArtistInfo(saved.getId());
		assertThat(artistInfo.getArtistInfoDto().getArtistId()).isEqualTo(saved.getId());
		assertThat(artistInfo.getArtistInfoDto().getIntroduction()).isEqualTo(saved.getIntroduction());
		assertThat(artistInfo.getArtistInfoDto().getNickname()).isEqualTo(saved.getNickname());
		assertThat(artistInfo.getArtistInfoDto().getProfileImage().getSavedFileName()).isEqualTo(savedImageFileName);
		assertThat(artistInfo.getArtistInfoDto().getUsername()).isEqualTo(saved.getUsername());
	}

	@Test
	public void getPublishedSongTest() {
		// TODO: add test for getPublishedSong
	}

	@Test
	public void toggleFollowArtistTest() {
		Artist artist1 = artistRepository.save(validArtist1);
		Artist artist2 = artistRepository.save(validArtist2);

		// 팔로우 기능 확인
		artistService.toggleFollowArtist(artist1.getUsername(), artist2.getId());
		assertThat(followingRepository.getFollowingWhere(artist1.getId(), artist2.getId())).isPresent();

		// 언팔로우 기능 확인
		artistService.toggleFollowArtist(artist1.getUsername(), artist2.getId());
		assertThat(followingRepository.getFollowingWhere(artist1.getId(), artist2.getId())).isEmpty();
	}

}
