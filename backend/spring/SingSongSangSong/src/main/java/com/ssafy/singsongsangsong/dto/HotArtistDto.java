package com.ssafy.singsongsangsong.dto;

import com.ssafy.singsongsangsong.entity.Artist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HotArtistDto {
	private int weeklyPlayCountSum;
	private Artist artist;
}
