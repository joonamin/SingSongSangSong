package com.ssafy.singsongsangsong.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.singsongsangsong.ArtistFixture;
import com.ssafy.singsongsangsong.constants.FileType;
import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;
import com.ssafy.singsongsangsong.service.file.FileService;

@SpringBootTest
@ActiveProfiles("test")
public class FileServiceTest {

	@Autowired
	FileService fileService;

	@Autowired
	ArtistRepository artistRepository;

	MockMultipartFile mockImageFile = new MockMultipartFile("test.jpeg", "mockImage.jpeg", "image/jpeg",
		new FileInputStream(new ClassPathResource("mockImage.jpeg").getFile()));

	public FileServiceTest() throws IOException {
	}

	@BeforeEach
	public void setUp() {
		artistRepository.save(ArtistFixture.NO_PROFILE_USER.getArtist());

	}

	@AfterEach
	public void tearDown() {
		artistRepository.deleteById(ArtistFixture.NO_PROFILE_USER.getArtist().getId());
	}

	@Test
	public void 이미지_업로드_및_GET() {
		Artist artist = ArtistFixture.NO_PROFILE_USER.getArtist();
		try {
			String savedFileName = fileService.saveFile(artist.getId(), FileType.IMAGE, mockImageFile);
			System.out.println("savedFileName = " + savedFileName);

			// byte stream으로 받아온 Resource는 fileName이 기본적으로 null이다.
			Resource file = fileService.getFile(artist.getId(), FileType.IMAGE, mockImageFile.getOriginalFilename());

			assertThat(file).isNotNull();
			// assertThat(file.getFilename()).isEqualTo(savedFileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
