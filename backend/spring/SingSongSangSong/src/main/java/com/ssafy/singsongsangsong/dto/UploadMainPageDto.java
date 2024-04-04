package com.ssafy.singsongsangsong.dto;

import java.util.List;

import com.ssafy.singsongsangsong.entity.Song;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UploadMainPageDto {

	private List<UploadProcess> uploadProcesses;

	@Getter
	public enum Process {
		ANALYZING, COMPLETED
	}

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UploadProcess {
		public String title;
		public Process process;
		public Long songId;

		public static UploadProcess from(Song song) {
			Process process = song.isAnalyzed() ? Process.COMPLETED : Process.ANALYZING;
			return UploadProcess.builder().title(song.getTitle()).process(process).songId(song.getId()).build();
		}
	}
}
