package com.ssafy.singsongsangsong.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String savedFileName;
	private String originalFileName;

	private Long ownerId;

	public static File of(String fileName, String originalFileName, Long ownerId) {
		File file = new File();
		file.savedFileName = fileName;
		file.originalFileName = originalFileName;
		file.ownerId = ownerId;
		return file;
	}

}
