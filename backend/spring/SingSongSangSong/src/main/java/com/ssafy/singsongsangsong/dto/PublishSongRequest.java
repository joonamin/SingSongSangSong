package com.ssafy.singsongsangsong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishSongRequest {
	private Long songId;
	private String albumImageName;
	private String lyrics;
	private String description;
}
