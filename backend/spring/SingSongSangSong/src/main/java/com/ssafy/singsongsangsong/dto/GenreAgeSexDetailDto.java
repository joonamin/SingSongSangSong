package com.ssafy.singsongsangsong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenreAgeSexDetailDto {
	
	private String genre;
	private int playCount;
	private TrendSongDto song;

}
