package com.ssafy.singsongsangsong.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ssafy.singsongsangsong.entity.Song;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LikedPageResponseDto {
	private List<SongBriefDto> likedSongPage;
	private int pageTotal;
	private boolean isFirst;
	private boolean isLast;
	public static LikedPageResponseDto from(Page<SongBriefDto> page) {
		return LikedPageResponseDto.builder()
			.likedSongPage(page.getContent())
			.pageTotal(page.getTotalPages())
			.isFirst(page.isFirst())
			.isLast(page.isLast())
			.build();
	}
}