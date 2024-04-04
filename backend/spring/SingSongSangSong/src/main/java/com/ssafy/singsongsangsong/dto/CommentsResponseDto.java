package com.ssafy.singsongsangsong.dto;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ssafy.singsongsangsong.constants.DefaultFileName;
import com.ssafy.singsongsangsong.entity.Comments;
import com.ssafy.singsongsangsong.entity.File;

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
public class CommentsResponseDto implements Serializable {

	List<CommentsResponse> comments;

	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Setter
	@Getter
	public static class CommentsResponse implements Serializable {
		private Long authorId;
		private String artistNickname;
		private String content;
		private String imageFileName;
		private Date createdAt;

		public static CommentsResponse from(Comments comments) {
			Date date = Date.from(comments.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant());
			Optional<File> profileImage = Optional.ofNullable(comments.getArtist().getProfileImage());
			return CommentsResponse.builder()
				.authorId(comments.getArtist().getId())
				.artistNickname(comments.getArtist().getNickname())
				.content(comments.getContent())
				.imageFileName(profileImage.orElseGet(
						() -> new File(999L, DefaultFileName.DEFAULT_PROFILE_PICTURE.getName(),
							DefaultFileName.DEFAULT_PROFILE_PICTURE.getName(), comments.getArtist().getId()))
					.getOriginalFileName())
				.createdAt(date)
				.build();
		}

	}

}
