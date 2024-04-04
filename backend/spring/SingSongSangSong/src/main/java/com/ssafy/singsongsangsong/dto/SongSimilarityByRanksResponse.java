package com.ssafy.singsongsangsong.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

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
public class SongSimilarityByRanksResponse {

	private int size;

	private String albumImageFileName;
	private String title;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime createdDate;

	List<Comparison> comparison;

	@Builder
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Comparison {
		private Float correlation;
		private Target target;

		@Builder
		@Getter
		@Setter
		@NoArgsConstructor
		@AllArgsConstructor
		public static class Target {
			private Long songId;
			private String albumImageFileName;
			private String title;
			@JsonDeserialize(using = LocalDateTimeDeserializer.class)
			private LocalDateTime createdDate;
		}
	}

}
