package com.ssafy.singsongsangsong.dto;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.entity.Song;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SearchResponseDto {
	List<ArtistInfoDto> artistInfoDtoList;
	List<SongBriefDto> songBriefDtoList;

	public static SearchResponseDto from (List<Artist> artistList, List<Song> songList ){
		List<ArtistInfoDto> artistInfoList = new ArrayList<>();
		List<SongBriefDto> songBriefList = new ArrayList<>();

		for(Artist artist : artistList) {
			artistInfoList.add(ArtistInfoDto.from(artist));
		}
		for(Song song : songList) {
			songBriefList.add(SongBriefDto.from(song));
		}
		return SearchResponseDto.builder()
			.artistInfoDtoList(artistInfoList)
			.songBriefDtoList(songBriefList)
			.build();
	}
}
