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
public class TrendChartDto {
	
	private List<TrendSongDetailDto> weekly;
	private List<TrendSongDetailDto> world;
	private List<TrendSongDetailDto> korean;
	private EmotionSongsDto emotions;
	
}
