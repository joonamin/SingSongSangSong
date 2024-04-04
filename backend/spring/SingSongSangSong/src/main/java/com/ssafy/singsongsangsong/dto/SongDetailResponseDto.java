package com.ssafy.singsongsangsong.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongDetailResponseDto {
	private String title;
	private ArtistInfoDto artistInfoDto;
	private int duration;

	private String songDescription;
	private List<Integer> emotions;
	private String lyrics;
	private String chord;
	private int bpm;

	private String location;
	private String albumImageLocation;


}
