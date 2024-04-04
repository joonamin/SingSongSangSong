package com.ssafy.singsongsangsong.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.ssafy.singsongsangsong.entity.Song;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class SongBriefDto {
	private Long songId;
	private String title;
	private Long artistId;
	private String artistName;
	private String albumImage;
	private String genre;
	private String atmosphere;
	private String songFileName;
	private int playCount;
	private int duration;

	public static SongBriefDto from(Song song) {
		SongBriefDto dto = new SongBriefDto();
		BeanUtils.copyProperties(song, dto);

		dto.setSongId(song.getId());
		dto.setTitle(song.getTitle());
		dto.setArtistId(song.getArtist().getId());
		dto.setArtistName(song.getArtist().getNickname());
		if(song.getAlbumImage() != null){
			dto.setAlbumImage(song.getAlbumImage().getOriginalFileName());
		}
		else {
			dto.setAlbumImage("default.jpg");
		}
		dto.setGenre(song.getCustomGenre());
		dto.setPlayCount(song.getPlayCount());
		dto.setDuration(song.getDuration());
		dto.setAtmosphere(song.getThemes());
		dto.setSongFileName(song.getMusicFileName());
		return dto;
	}
}
