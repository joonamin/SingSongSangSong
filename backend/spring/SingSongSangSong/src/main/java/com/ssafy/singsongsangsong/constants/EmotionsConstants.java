package com.ssafy.singsongsangsong.constants;

public enum EmotionsConstants {
	MOVED("moved"),
	LIKE("like"),
	EXCITED("excited"),
	ENERGIZED("energized"),
	FUNNY("funny"),
	SAD("sad");
	private final java.lang.String name;

	EmotionsConstants(java.lang.String emotionName) {
		this.name = emotionName;
	}

	public java.lang.String getName() {
		return name;
	}
}
