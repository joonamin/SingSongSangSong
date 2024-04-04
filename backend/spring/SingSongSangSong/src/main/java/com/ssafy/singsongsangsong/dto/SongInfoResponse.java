package com.ssafy.singsongsangsong.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongInfoResponse {

	private String songTitle;
	private ArtistInfoDto artist;
	private String songFileName;
	private String albumImageFileName;
	private String songDescription;

	private int movedEmotionCount;
	private int likeEmotionCount;
	private int excitedEmotionCount;
	private int energizedEmotionCount;
	private int funnyEmotionCount;
	private int sadEmotionCount;

	private String lyrics;
	private String chord;
	private int bpm;

	private int likeCount;
	private int playCount;
	private int downloadCount;

	private List<CommentsInfoDto> comments;

	private Long mfccImageId;
	private Long spectrumImageId;
}
