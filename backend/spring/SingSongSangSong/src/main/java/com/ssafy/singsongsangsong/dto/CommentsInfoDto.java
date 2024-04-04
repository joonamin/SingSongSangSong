package com.ssafy.singsongsangsong.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.ssafy.singsongsangsong.entity.Comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsInfoDto {
	private Long artistId;
	private String nickname;
	private String content;
	private String profileImageFileName;
	private String createdAt;

	public static CommentsInfoDto from(Comments comments) {
		return CommentsInfoDto.builder()
			.artistId(comments.getArtist().getId())
			.nickname(comments.getArtist().getNickname())
			.content(comments.getContent())
			.profileImageFileName(comments.getArtist().getProfileImage().getOriginalFileName())
			.createdAt(comments.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
			.build();
	}
}
