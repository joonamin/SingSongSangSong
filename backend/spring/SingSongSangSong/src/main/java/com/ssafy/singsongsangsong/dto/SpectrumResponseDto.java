package com.ssafy.singsongsangsong.dto;

import java.util.List;

import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpectrumResponseDto {
	private Long spectrumImageId;

	public static SpectrumResponseDto from(Long spectrumImageId) {
		SpectrumResponseDto spectrumResponseDto = new SpectrumResponseDto();
		spectrumResponseDto.setSpectrumImageId(spectrumImageId);
		return spectrumResponseDto;
	}
}
