package com.ssafy.singsongsangsong.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * song의 atmosphere를 받아서 theme를 반환합니다.
 */
@Component
public class ThemesClassifier {

	enum Themes {
		HAPPY("happy"),
		SAD("sad"),
		CALM("calm"),
		EXCITING("exciting"),
		LOVE("love"),
		INSPIRING("inspiring"),
		MYSTERY("mystery"),
		NOSTALGIA("nostalgia"),
		ETC("etc");

		private final String name;

		Themes(String value) {
			this.name = value;
		}

		public String getName() {
			return name;
		}
	}

	private static final Map<String, String> mapper = new HashMap<>();

	static {
		List<String> themesName = List.of("happy", "sad", "calm", "exciting", "love", "inspiring", "mystery",
			"nostalgia", "etc");
		List<String> information = List.of(
			"fun, funny, party, happy, summer, upbeat, uplifting, christmas, holiday, positive",
			"sad, melancholic, dark, heavy, emotional",
			"calm, relaxing, meditative, soft, nature, soundscape, melodic, slow, dream",
			"action, adventure, energetic, fast, epic, powerful, sport, game, drama, dramatic, groovy",
			"love, romantic, sexy",
			"hopeful, inspiring, motivational, documentary",
			"deep, cool, space",
			"retro, ballad",
			"advertising, background, children, commercial, corporate, film, movie, trailer, travel"
		);

		for (int i = 0; i < themesName.size(); i++) {
			String[] value = information.get(i).split(", ");
			for (String v : value) {
				mapper.put(v, themesName.get(i));
			}
		}
	}

	public String getTheme(String atmosphereName) {
		if (!mapper.containsKey(atmosphereName)) {
			throw new IllegalArgumentException("해당 분위기에 대한 정의된 테마가 없습니다.");
		}
		return mapper.get(atmosphereName);
	}
}
