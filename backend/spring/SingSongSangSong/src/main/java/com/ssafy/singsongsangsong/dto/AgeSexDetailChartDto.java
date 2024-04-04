package com.ssafy.singsongsangsong.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgeSexDetailChartDto {
	
	private List<GenreAgeSexDetailDto> genres;
	private List<AtmosphereAgeSexDetailDto> atmospheres;
	private List<SongAgeSexDetailDto> songs;
	private List<ArtistAgeSexDetailDto> artists;

}
