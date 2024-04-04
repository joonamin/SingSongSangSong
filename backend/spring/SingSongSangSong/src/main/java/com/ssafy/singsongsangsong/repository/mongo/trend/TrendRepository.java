package com.ssafy.singsongsangsong.repository.mongo.trend;

import java.time.LocalDate;

import com.ssafy.singsongsangsong.dto.AgeSexChartDto;
import com.ssafy.singsongsangsong.dto.AllChartDto;
import com.ssafy.singsongsangsong.dto.BpmDetailDto;
import com.ssafy.singsongsangsong.dto.SongArtistDto;

public interface TrendRepository {
	
	AllChartDto getAllChart(LocalDate date);
	AgeSexChartDto getAgeSexChart(LocalDate date, String age, String sex);
	SongArtistDto getGenreChart(LocalDate date, String genre);
	SongArtistDto getAtmosphereChart(LocalDate date, String atmosphere);
	BpmDetailDto getBpmChart(LocalDate date, String bpm);
	
}
