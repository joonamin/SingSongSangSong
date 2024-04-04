package com.ssafy.singsongsangsong.dto;

import com.ssafy.singsongsangsong.entity.Artist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotArtistResponseDto {
	private int weeklyPlayCountSum;
	private ArtistInfoDto artistInfoDto;
}
