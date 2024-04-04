package com.ssafy.singsongsangsong.repository.mongo.trend;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.singsongsangsong.dto.AgeSexChartDto;
import com.ssafy.singsongsangsong.dto.AllChartDto;
import com.ssafy.singsongsangsong.dto.AtmosphereChartDto;
import com.ssafy.singsongsangsong.dto.BpmChartDto;
import com.ssafy.singsongsangsong.dto.BpmDetailDto;
import com.ssafy.singsongsangsong.dto.GenreChartDto;
import com.ssafy.singsongsangsong.dto.SongArtistDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TrendRepositoryImpl implements TrendRepository {
	
	private final MongoTemplate mongoTemplate;

	@Override
	public AllChartDto getAllChart(LocalDate date) {
		return mongoTemplate.findOne(Query.query(Criteria.where("part").is("all").and("start").lte(date).and("end").gt(date)), AllChartDto.class);
	}
	
	@Override
	public AgeSexChartDto getAgeSexChart(LocalDate date, String age, String sex) {
		return mongoTemplate.findOne(Query.query(Criteria.where("age").is(age).and("sex").is(sex).and("start").lte(date).and("end").gt(date)), AgeSexChartDto.class);
	}

	@Override
	public SongArtistDto getGenreChart(LocalDate date, String genre) {
		return mongoTemplate.findOne(Query.query(Criteria.where("part").is("genre").and("start").lte(date).and("end").gt(date)), GenreChartDto.class).getGenres().get(genre);
	}

	@Override
	public SongArtistDto getAtmosphereChart(LocalDate date, String atmosphere) {
		return mongoTemplate.findOne(Query.query(Criteria.where("part").is("atmosphere").and("start").lte(date).and("end").gt(date)), AtmosphereChartDto.class).getAtmospheres().get(atmosphere);
	}

	@Override
	public BpmDetailDto getBpmChart(LocalDate date, String bpm) {
		return mongoTemplate.findOne(Query.query(Criteria.where("part").is("bpm").and("start").lte(date).and("end").gt(date)), BpmChartDto.class).getBpms().get(bpm);
	}

}
