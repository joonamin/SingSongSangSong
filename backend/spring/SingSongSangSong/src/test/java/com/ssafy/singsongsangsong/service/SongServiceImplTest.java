package com.ssafy.singsongsangsong.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.singsongsangsong.ArtistFixture;
import com.ssafy.singsongsangsong.constants.EmotionsConstants;
import com.ssafy.singsongsangsong.dto.CommentsResponseDto;
import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.entity.Emotions;
import com.ssafy.singsongsangsong.entity.Song;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;
import com.ssafy.singsongsangsong.repository.maria.song.EmotionRepository;
import com.ssafy.singsongsangsong.repository.maria.song.SongRepository;
import com.ssafy.singsongsangsong.service.song.SongService;

@SpringBootTest
@ActiveProfiles("test")
class SongServiceImplTest {

	@Autowired
	SongRepository songRepository;

	@Autowired
	EmotionRepository emotionRepository;

	@Autowired
	SongService songService;

	@Autowired
	ArtistRepository artistRepository;

	@Nested
	class 감정_업데이트_테스트 {
		Artist artist = artistRepository.save(ArtistFixture.NO_PROFILE_USER.getArtist());

		@Nested
		class 사용자가_어떠한_감정을_남기지_않은_게시물에_대해 {

			Song song = songRepository.save(Song.builder()
				.id(1L)
				.artist(artist)
				.build());

			@Test
			void 감정_테이블에_엔트리를_추가한_다음_감정_카운트를_증가시킨다() {
				assertThatCode(() -> {
					songService.updateEmotionType(artist.getId(), song.getId(), EmotionsConstants.MOVED);
				}).doesNotThrowAnyException();

				assertThat(songRepository.findById(song.getId())).isPresent();
				assertThat(songRepository.findById(song.getId()).get().getMovedEmotionCount()).isEqualTo(1);

				assertThat(emotionRepository.findByArtistAndSongId(artist.getId(), song.getId())).isPresent();
				assertThat(emotionRepository.findByArtistAndSongId(artist.getId(), song.getId())
					.get()
					.getEmotionType()
					.getName()).isEqualTo(
					EmotionsConstants.MOVED.getName());
			}
		}

		@Nested
		class 사용자가_감정을_이미_남긴_게시물에_대해 {
			Song song = songRepository.save(Song.builder()
				.id(2L)
				.movedEmotionCount(100)
				.artist(artist)
				.build());

			Emotions emotions = emotionRepository.save(Emotions.builder()
				.song(song)
				.emotionType(EmotionsConstants.MOVED)
				.artist(song.getArtist())
				.build());

			@Test
			void 기존_감정을_업데이트하고_카운트를_증가시킨다() {
				assertThatCode(() -> {
					songService.updateEmotionType(artist.getId(), song.getId(), EmotionsConstants.EXCITED);
				}).doesNotThrowAnyException();

				assertThat(songRepository.findById(song.getId())).isPresent();
				assertThat(songRepository.findById(song.getId()).get().getMovedEmotionCount()).isEqualTo(99);
				assertThat(songRepository.findById(song.getId()).get().getExcitedEmotionCount()).isEqualTo(1);

				assertThat(emotionRepository.findByArtistAndSongId(artist.getId(), song.getId())).isPresent();
				assertThat(emotionRepository.findByArtistAndSongId(artist.getId(), song.getId())
					.get()
					.getEmotionType()
					.getName()).isEqualTo(
					EmotionsConstants.EXCITED.getName());
			}
		}
	}

	@Nested
	class postComment_메소드는 {
		@Nested
		class 사용자가_댓글을_남긴_경우 {
			Artist artist = artistRepository.save(ArtistFixture.NO_PROFILE_USER.getArtist());
			Song song = songRepository.save(Song.builder()
				.id(1L)
				.artist(artist)
				.build());

			@Test
			void 댓글을_추가한다() {
				assertThatCode(() -> {
					songService.postComment(artist.getId(), song.getId(), "댓글입니다.");
				}).doesNotThrowAnyException();

				CommentsResponseDto comments = songService.getComments(song.getId());
				assertThat(comments.getComments().size()).isEqualTo(1);
			}
		}

	}

}