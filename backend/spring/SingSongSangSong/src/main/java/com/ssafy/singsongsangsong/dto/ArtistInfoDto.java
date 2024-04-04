package com.ssafy.singsongsangsong.dto;

import java.util.Optional;

import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.entity.File;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ArtistInfoDto {
	private Long artistId;
	private String nickname;
	private String username;
	private ImageDto profileImage;
	private String introduction;

	public static ArtistInfoDto from(Artist artist) {
		File profileImage = Optional.ofNullable(artist.getProfileImage()).orElseGet(() -> null);

		return ArtistInfoDto.builder()
			.artistId(artist.getId())
			.nickname(artist.getNickname())
			.username(artist.getUsername())
			.profileImage(ImageDto.from(profileImage))
			.introduction(artist.getIntroduction())
			.build();
	}

}
