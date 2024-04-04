package com.ssafy.singsongsangsong.constants;

import lombok.Getter;

@Getter
public enum FileType {
	IMAGE("image"),
	AUDIO("audio"),
	MFCC("mfcc"),
	SPECTRUM("spectrum");

	private final String name;

	FileType(String name) {
		this.name = name;
	}

	public static FileType getByName(String name) {
		for (FileType fileType : FileType.values()) {
			if (fileType.getName().equalsIgnoreCase(name)) {
				return fileType;
			}
		}
		throw new IllegalArgumentException("No enum constant for " + name);
	}
}
