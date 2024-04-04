package com.ssafy.singsongsangsong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikedResponseDto {
	private String result;

	public static LikedResponseDto from(String result) {
		LikedResponseDto likedResponseDto = new LikedResponseDto();
		likedResponseDto.setResult(result);
		return likedResponseDto;
	}
}
