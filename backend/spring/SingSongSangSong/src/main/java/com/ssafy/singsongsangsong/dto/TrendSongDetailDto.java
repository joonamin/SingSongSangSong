package com.ssafy.singsongsangsong.dto;

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
public class TrendSongDetailDto {
	
	private long songId;
	private String songTitle;
	private ArtistInfoDto artist;
	
	private String songFileName;
	private String albumImageFileName;
	private String songDescription;
	
	private int likeCount;
	private int downloadCount;
	private int playCount;
	private int bpm;
	
	private int movedEmotionCount;
	private int likeEmotionCount;
	private int excitedEmotionCount;
	private int energizedEmotionCount;
	private int funnyEmotionCount;
	private int sadEmotionCount;
	
	private AnalyzeGenreAndAtmosphereResponse analysis;

}
