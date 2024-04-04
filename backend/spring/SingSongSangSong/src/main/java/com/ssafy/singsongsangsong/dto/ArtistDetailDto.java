package com.ssafy.singsongsangsong.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDetailDto {
	private ArtistInfoDto artistInfoDto;
	private int countPublishedSong;

	private Map<String, Integer> preferGenreMap;
	private Map<String, Integer> preferAtmosphereMap;

	public static ArtistDetailDto from(ArtistInfoDto artistInfoDto, int countPublishedSong,
		Map<String, Integer> preferGenreMap, Map<String, Integer> preferAtmosphereMap) {
		ArtistDetailDto artistDetailDto = new ArtistDetailDto();
		artistDetailDto.setArtistInfoDto(artistInfoDto);
		artistDetailDto.setCountPublishedSong(countPublishedSong);
		artistDetailDto.setPreferGenreMap(preferGenreMap);
		artistDetailDto.setPreferAtmosphereMap(preferAtmosphereMap);
		return artistDetailDto;
	}
}
