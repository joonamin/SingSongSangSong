package com.ssafy.singsongsangsong.service.song;

import static com.ssafy.singsongsangsong.entity.QSong.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.ssafy.singsongsangsong.dto.ArtistInfoDto;
import com.ssafy.singsongsangsong.dto.HotArtistDto;
import com.ssafy.singsongsangsong.dto.HotArtistResponseDto;
import com.ssafy.singsongsangsong.dto.LikedPageResponseDto;
import com.ssafy.singsongsangsong.dto.SearchResponseDto;
import com.ssafy.singsongsangsong.dto.SongBriefDto;
import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.entity.Likes;
import com.ssafy.singsongsangsong.entity.Song;
import com.ssafy.singsongsangsong.exception.artist.ArtistNotFoundException;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;
import com.ssafy.singsongsangsong.repository.maria.atmosphere.AtmosphereRepository;
import com.ssafy.singsongsangsong.repository.maria.liked.LikedRepository;
import com.ssafy.singsongsangsong.repository.maria.genre.GenreRepository;
import com.ssafy.singsongsangsong.repository.maria.song.SongRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SongPlayListServiceImpl implements SongPlayListService {
	private final SongRepository songRepository;
	private final LikedRepository likedRepository;
	private final ArtistRepository artistRepository;
	private final GenreRepository genreRepository;
	private final AtmosphereRepository atmosphereRepository;

	@Override
	public LikedPageResponseDto getLikedPagination(String username, int pageNo) {
		Artist artist = artistRepository.findByUsername(username)
			.orElseThrow(() -> new ArtistNotFoundException("존재하지 않는 유저입니다."));
		PageRequest pageRequest = PageRequest.of(pageNo - 1, 10);
		log.info("artistId : {}", artist.getId());
		List<SongBriefDto> likedSongList = songsFromLikes(likedRepository.getLikedSongsByArtistId(artist.getId()));
		log.info("likedSongList : {}", likedSongList);
		int start = (int)pageRequest.getOffset();
		int end = Math.min((start + pageRequest.getPageSize()), likedSongList.size());
		Page<SongBriefDto> likedSongPage = new PageImpl<>(likedSongList.subList(start, end), pageRequest,
			likedSongList.size());
		log.info("pageData : {}", likedSongPage.getContent());

		return LikedPageResponseDto.from(likedSongPage);
	}

	private List<SongBriefDto> songsFromLikes(List<Likes> likesList) {
		List<SongBriefDto> songsFromLikes = new ArrayList<>();
		for (Likes likes : likesList) {
			songsFromLikes.add(SongBriefDto.from(likes.getSong()));
		}
		return songsFromLikes;
	}

	@Override
	public List<SongBriefDto> getWeeklyHitSongList() {
		List<SongBriefDto> list = new ArrayList<>();
		List<Song> songListFromDB = songRepository.findSongOrderByWeeklyCountDesc();
		for (Song song : songListFromDB) {
			list.add(SongBriefDto.from(song));
		}
		return list;
	}

	@Override
	public List<SongBriefDto> getGenreHitSongList(String genre) {
		genre = castingGenreString(genre);
		List<SongBriefDto> list = new ArrayList<>();
		List<Song> songListFromDB = songRepository.findSongForGenreOrderByWeeklyCountDesc(genre);
		for (Song song : songListFromDB) {
			list.add(SongBriefDto.from(song));
		}
		return list;
	}
	private String castingGenreString(String genre) {
		if(genre.equals("hiphop")) {
			return "Hip Hop";
		}
		else {
			return genre.substring(0,1).toUpperCase() + genre.substring(1);
		}
	}

	@Override
	public List<SongBriefDto> getAtmosphereHitSongList(String atmosphere) {
		List<SongBriefDto> list = new ArrayList<>();
		List<Song> songListFromDB = songRepository.findSongForAtmosphereOrderByWeeklyCountDesc(atmosphere);
		for (Song song : songListFromDB) {
			list.add(SongBriefDto.from(song));
		}
		return list;
	}

	@Override
	public List<ArtistInfoDto> getFollowArtistList(String username) {
		Artist artist = artistRepository.findByUsername(username)
			.orElseThrow(() -> new ArtistNotFoundException("유효하지 않은 user입니다."));
		List<ArtistInfoDto> list = new ArrayList<>();
		List<Artist> followArtistFromDB = artistRepository.getFollowArtistByArtistId(artist.getId());
		for (Artist followArtist : followArtistFromDB) {
			list.add(ArtistInfoDto.from(followArtist));
		}
		return list;
	}

	@Override
	public SearchResponseDto searchArtistAndSong(String keyword, String requestGenre, String requestAtmosphere,
		Integer bpm,
		String sort) {
		if(requestGenre != null) {
			requestGenre = castingGenreString(requestGenre);
		}
		int[] bpmRange = bpmRange(bpm);
		OrderSpecifier[] orderSpecifiers = sortStandard(sort);
		List<Song> songListFiltering = songRepository.findSongByBpmAndKeyword(keyword, bpmRange[0], bpmRange[1],
			orderSpecifiers, requestGenre, requestAtmosphere);

		List<Artist> artistList = artistRepository.findArtistBySearchParam(keyword);
		return SearchResponseDto.from(artistList, songListFiltering);
	}

	@Override
	public List<HotArtistResponseDto> getHotArtist() {
		List<HotArtistDto> hotArtistResponseDtoList = artistRepository.findHotArtist();
		List<HotArtistResponseDto> result = new ArrayList<>();
		for (HotArtistDto hotArtistDto : hotArtistResponseDtoList) {
			HotArtistResponseDto hotArtistResponseDto = new HotArtistResponseDto();
			hotArtistResponseDto.setWeeklyPlayCountSum(hotArtistDto.getWeeklyPlayCountSum());
			hotArtistResponseDto.setArtistInfoDto(ArtistInfoDto.from(hotArtistDto.getArtist()));
			result.add(hotArtistResponseDto);
		}
		return result;
	}

	private int[] bpmRange(Integer bpm) {
		int startBpm = 0;
		int endBpm = 39;
		if (bpm >= 40 && bpm < 200) {
			startBpm = bpm;
			endBpm = bpm + 20;
		} else if (bpm >= 200) {
			startBpm = 200;
			endBpm = 10000;
		}
		return new int[] {startBpm, endBpm};
	}

	private OrderSpecifier[] sortStandard(String sort) {
		List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
		if (sort.equals("date")) {
			orderSpecifiers.add(new OrderSpecifier(Order.DESC, song.createdDate));
		}
		if (sort.equals("view")) {
			orderSpecifiers.add(new OrderSpecifier(Order.DESC, song.playCount));
		} else {
			orderSpecifiers.add(new OrderSpecifier(Order.DESC, song.likeCount));
		}
		return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
	}

}
