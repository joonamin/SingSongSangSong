package com.ssafy.singsongsangsong.dto;

import com.ssafy.singsongsangsong.constants.DefaultFileName;
import com.ssafy.singsongsangsong.entity.File;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ImageDto {
	private String savedFileName;
	private String originalFileName;

	public static ImageDto from(File image) {
		String originalImageName;
		String savedImageName;

		if (image != null) {
			originalImageName = image.getOriginalFileName();
			savedImageName = image.getSavedFileName();
		} else {
			originalImageName = DefaultFileName.DEFAULT_PROFILE_PICTURE.getName();
			savedImageName = DefaultFileName.DEFAULT_PROFILE_PICTURE.getName();
		}

		return ImageDto.builder()
			.originalFileName(originalImageName)
			.savedFileName(savedImageName)
			.build();
	}
}
