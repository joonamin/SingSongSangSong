package com.ssafy.singsongsangsong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmotionsDto {
	private int movedEmotionCount;
	private int likeEmotionCount;
	private int excitedEmotionCount;
	private int energizedEmotionCount;
	private int funnyEmotionCount;
	private int sadEmotionCount;
}
