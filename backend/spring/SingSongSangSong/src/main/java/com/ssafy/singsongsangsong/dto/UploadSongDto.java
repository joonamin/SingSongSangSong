package com.ssafy.singsongsangsong.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadSongDto extends UploadFileDto implements Serializable {
	private Long songId;

	public UploadSongDto(Long songId, String originalFileName, String savedFileName) {
		super(savedFileName, originalFileName);
		this.songId = songId;
	}
}
