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
public class EmotionSongsDto {
	
	private TrendSongDto moved;
	private TrendSongDto like;
	private TrendSongDto excited;
	private TrendSongDto energized;
	private TrendSongDto funny;
	private TrendSongDto sad;

}
