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
public class SongArtistDetailDto {
	
	private List<TrendSongDto> songs;
	private List<ArtistInfoDto> artists;

}
