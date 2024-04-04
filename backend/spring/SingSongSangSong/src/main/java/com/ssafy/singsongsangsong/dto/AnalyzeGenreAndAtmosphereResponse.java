package com.ssafy.singsongsangsong.dto;

import java.util.List;

import com.ssafy.singsongsangsong.entity.Atmosphere;
import com.ssafy.singsongsangsong.entity.Genre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyzeGenreAndAtmosphereResponse {
	private int genreLength;
	private int atmosphereLength;

	List<AnalyzeGenreDto> genres;
	List<AnalyzeAtmosphereDto> atmospheres;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class AnalyzeGenreDto {
		private String genre;
		private double correlation;

		public static AnalyzeGenreDto from(Genre genre) {
			return AnalyzeGenreDto.builder()
				.genre(genre.getMainCategory())
				.correlation(genre.getCorrelation())
				.build();
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class AnalyzeAtmosphereDto {
		private String atmosphere;
		private double correlation;

		public static AnalyzeAtmosphereDto from(Atmosphere atmosphere) {
			return AnalyzeAtmosphereDto.builder()
				.atmosphere(atmosphere.getAtmosphere())
				.correlation(atmosphere.getCorrelation())
				.build();
		}
	}
}
