package com.ssafy.singsongsangsong.service.trend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssafy.singsongsangsong.constants.DefaultFileName;
import com.ssafy.singsongsangsong.dto.AgeSexChartDto;
import com.ssafy.singsongsangsong.dto.AgeSexDetailChartDto;
import com.ssafy.singsongsangsong.dto.AllChartDto;
import com.ssafy.singsongsangsong.dto.AnalyzeGenreAndAtmosphereResponse;
import com.ssafy.singsongsangsong.dto.AnalyzeGenreAndAtmosphereResponse.AnalyzeAtmosphereDto;
import com.ssafy.singsongsangsong.dto.AnalyzeGenreAndAtmosphereResponse.AnalyzeGenreDto;
import com.ssafy.singsongsangsong.dto.ArtistAgeSexDetailDto;
import com.ssafy.singsongsangsong.dto.ArtistAgeSexDto;
import com.ssafy.singsongsangsong.dto.ArtistInfoDto;
import com.ssafy.singsongsangsong.dto.AtmosphereAgeSexDetailDto;
import com.ssafy.singsongsangsong.dto.AtmosphereAgeSexDto;
import com.ssafy.singsongsangsong.dto.BpmChartDetailDto;
import com.ssafy.singsongsangsong.dto.BpmDetailDto;
import com.ssafy.singsongsangsong.dto.EmotionSongsDto;
import com.ssafy.singsongsangsong.dto.GenreAgeSexDetailDto;
import com.ssafy.singsongsangsong.dto.GenreAgeSexDto;
import com.ssafy.singsongsangsong.dto.SongAgeSexDetailDto;
import com.ssafy.singsongsangsong.dto.SongAgeSexDto;
import com.ssafy.singsongsangsong.dto.SongArtistDetailDto;
import com.ssafy.singsongsangsong.dto.SongArtistDto;
import com.ssafy.singsongsangsong.dto.TrendChartDto;
import com.ssafy.singsongsangsong.dto.TrendSongDetailDto;
import com.ssafy.singsongsangsong.dto.TrendSongDto;
import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.entity.Atmosphere;
import com.ssafy.singsongsangsong.entity.Genre;
import com.ssafy.singsongsangsong.entity.Song;
import com.ssafy.singsongsangsong.exception.song.NotFoundSongException;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;
import com.ssafy.singsongsangsong.repository.maria.atmosphere.AtmosphereRepository;
import com.ssafy.singsongsangsong.repository.maria.genre.GenreRepository;
import com.ssafy.singsongsangsong.repository.maria.song.SongRepository;
import com.ssafy.singsongsangsong.repository.mongo.trend.TrendRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrendServiceImpl implements TrendService {

	private final TrendRepository trendRepository;
	private final SongRepository songRepository;
	private final ArtistRepository artistRepository;
	private final GenreRepository genreRepository;
	private final AtmosphereRepository atmosphereRepository;
	
	@Override
	public TrendSongDto getSong(Long songId) {
		Song song = songRepository.findById(songId).orElseThrow(NotFoundSongException::new);
		Artist artist = song.getArtist();

		String musicFileName = Optional.ofNullable(song.getMusicFileName())
			.orElseGet(DefaultFileName.DEFAULT_PROFILE_PICTURE::getName);

		String originalFileName;
		if (song.getAlbumImage() != null && song.getAlbumImage().getOriginalFileName() != null) {
			originalFileName = song.getAlbumImage().getOriginalFileName();
		} else {
			originalFileName = DefaultFileName.DEFAULT_PROFILE_PICTURE.getName();
		}
		
		return TrendSongDto.builder()
				.songId(song.getId())
				.songTitle(song.getTitle())
				.artist(ArtistInfoDto.from(artist))
				.songFileName(musicFileName)
				.albumImageFileName(originalFileName)
				.likeCount(song.getLikeCount())
				.downloadCount(song.getDownloadCount())
				.playCount(song.getPlayCount())
				.bpm(song.getBpm())
				.movedEmotionCount(song.getMovedEmotionCount())
				.likeEmotionCount(song.getLikeEmotionCount())
				.energizedEmotionCount(song.getEnergizedEmotionCount())
				.excitedEmotionCount(song.getExcitedEmotionCount())
				.funnyEmotionCount(song.getFunnyEmotionCount())
				.sadEmotionCount(song.getSadEmotionCount())
				.build();
	}
	
	@Override
	public TrendSongDetailDto getSongDetail(Long songId) {
		Song song = songRepository.findById(songId).orElseThrow(NotFoundSongException::new);
		Artist artist = song.getArtist();

		String musicFileName = Optional.ofNullable(song.getMusicFileName())
			.orElseGet(DefaultFileName.DEFAULT_PROFILE_PICTURE::getName);

		String originalFileName;
		if (song.getAlbumImage() != null && song.getAlbumImage().getOriginalFileName() != null) {
			originalFileName = song.getAlbumImage().getOriginalFileName();
		} else {
			originalFileName = DefaultFileName.DEFAULT_PROFILE_PICTURE.getName();
		}
		
		return TrendSongDetailDto.builder()
				.songId(song.getId())
				.songTitle(song.getTitle())
				.artist(ArtistInfoDto.from(artist))
				.songFileName(musicFileName)
				.albumImageFileName(originalFileName)
				.songDescription(song.getSongDescription())
				.likeCount(song.getLikeCount())
				.downloadCount(song.getDownloadCount())
				.playCount(song.getPlayCount())
				.bpm(song.getBpm())
				.movedEmotionCount(song.getMovedEmotionCount())
				.likeEmotionCount(song.getLikeEmotionCount())
				.energizedEmotionCount(song.getEnergizedEmotionCount())
				.excitedEmotionCount(song.getExcitedEmotionCount())
				.funnyEmotionCount(song.getFunnyEmotionCount())
				.sadEmotionCount(song.getSadEmotionCount())
				.analysis(getAnalyzeGenreAndAtmosphere(songId, 5))
				.build();
	}
	
	@Override
	public ArtistInfoDto getArtistInfo(Long artistId) {
		Artist artist = artistRepository.findById(artistId)
			.orElseThrow(() -> new IllegalArgumentException("해당 아티스트가 존재하지 않습니다."));
		return ArtistInfoDto.from(artist);
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

	@Override
	public TrendChartDto getAllChart(LocalDate date) {
		AllChartDto dto = trendRepository.getAllChart(date);
		TrendChartDto trendChartDto = new TrendChartDto();
		
		List<TrendSongDetailDto> weekly = new ArrayList<>();
		for (Long songId : dto.getWeekly()) weekly.add(getSongDetail(songId));
		
		List<TrendSongDetailDto> world = new ArrayList<>();
		for (Long songId : dto.getWorld()) world.add(getSongDetail(songId));
		
		List<TrendSongDetailDto> korean = new ArrayList<>();
		for (Long songId : dto.getKorean()) korean.add(getSongDetail(songId));
		
		List<Long> emotions = dto.getEmotions();
		
		EmotionSongsDto emotionSongsDto = EmotionSongsDto.builder()
				.moved(getSong(emotions.get(0)))
				.like(getSong(emotions.get(1)))
				.excited(getSong(emotions.get(2)))
				.energized(getSong(emotions.get(3)))
				.funny(getSong(emotions.get(4)))
				.sad(getSong(emotions.get(5)))
				.build();
		
		trendChartDto.setWeekly(weekly);
		trendChartDto.setWorld(world);
		trendChartDto.setKorean(korean);
		trendChartDto.setEmotions(emotionSongsDto);
		
		return trendChartDto;
	}
	
	@Override
	public AgeSexDetailChartDto getAgeSexChart(LocalDate date, String age, String sex) {
		AgeSexChartDto dto = trendRepository.getAgeSexChart(date, age, sex);
		 
		 List<GenreAgeSexDetailDto> genres = new ArrayList<>();
		 List<AtmosphereAgeSexDetailDto> atmospheres = new ArrayList<>();
		 List<SongAgeSexDetailDto> songs = new ArrayList<>();
		 List<ArtistAgeSexDetailDto> artists = new ArrayList<>();
		 
		 for (GenreAgeSexDto temp : dto.getGenres()) {
			 genres.add(new GenreAgeSexDetailDto(temp.getGenre(), temp.getPlayCount(), getSong(temp.getSongId())));
		 }
		 
		 for (AtmosphereAgeSexDto temp : dto.getAtmospheres()) {
			 atmospheres.add(new AtmosphereAgeSexDetailDto(temp.getAtmosphere(), temp.getPlayCount(), getSong(temp.getSongId())));
		 }
		 
		 for (SongAgeSexDto temp : dto.getSongs()) {
			 songs.add(new SongAgeSexDetailDto(getSong(temp.getSongId()), temp.getPlayCount()));
		 }
		 
		 for (ArtistAgeSexDto temp : dto.getArtists()) {
			 TrendSongDto songDto = getSong(temp.getSongId());
			 artists.add(new ArtistAgeSexDetailDto(songDto.getArtist(), temp.getPlayCount(), songDto));
		 }
		
		return new AgeSexDetailChartDto(genres, atmospheres, songs, artists);
	}

	@Override
	public SongArtistDetailDto getGenreChart(LocalDate date, String genre) {
		SongArtistDto dto = trendRepository.getGenreChart(date, genre);
		SongArtistDetailDto detailDto = new SongArtistDetailDto();
		
		List<TrendSongDto> songList = new ArrayList<>();
		for (long songId : dto.getSongs()) songList.add(getSong(songId));
		
		List<ArtistInfoDto> artistList = new ArrayList<>();
		for (long artistId: dto.getArtists()) artistList.add(getArtistInfo(artistId));
		
		detailDto.setSongs(songList);
		detailDto.setArtists(artistList);
		
		return detailDto;
	}

	@Override
	public SongArtistDetailDto getAtmosphereChart(LocalDate date, String atmosphere) {
		SongArtistDto dto = trendRepository.getAtmosphereChart(date, atmosphere);
		SongArtistDetailDto detailDto = new SongArtistDetailDto();
		
		List<TrendSongDto> songList = new ArrayList<>();
		for (long songId : dto.getSongs()) songList.add(getSong(songId));
		
		List<ArtistInfoDto> artistList = new ArrayList<>();
		for (long artistId: dto.getArtists()) artistList.add(getArtistInfo(artistId));
		
		detailDto.setSongs(songList);
		detailDto.setArtists(artistList);
		
		return detailDto;
	}

	@Override
	public BpmChartDetailDto getBpmChart(LocalDate date, String bpm) {
		BpmDetailDto dto =  trendRepository.getBpmChart(date, bpm);
		
		BpmChartDetailDto bpmDetailChartDto = new BpmChartDetailDto();
		bpmDetailChartDto.setCount(dto.getCount());
		
		List<TrendSongDto> songList = new ArrayList<>();
		
		for (long songId : dto.getSongs()) songList.add(getSong(songId));
		
		bpmDetailChartDto.setSongs(songList);
		
		return bpmDetailChartDto;
	}

}
