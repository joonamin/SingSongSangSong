package com.ssafy.singsongsangsong.dto;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trend")
public class AgeSexChartDto {
	
	private List<GenreAgeSexDto> genres;
	private List<AtmosphereAgeSexDto> atmospheres;
	private List<SongAgeSexDto> songs;
	private List<ArtistAgeSexDto> artists;

}
