package com.ssafy.singsongsangsong.constants;

// Default 값을 가지는 File은 Original File Name과 Saved File Name이 같습니다.
public enum DefaultFileName {
	DEFAULT_ALBUM_PICTURE("default_album"),
	DEFAULT_PROFILE_PICTURE("default"),
	DEFAULT_NOT_YET_IMAGE("default_not_yet");
	private final String name;

	DefaultFileName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
