package com.ssafy.singsongsangsong.dto;

import java.util.List;

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
public class SongListByThemeResponseDto {
	private int size;
	private List<SimpleSongDto> songList;

}
