package com.ssafy.singsongsangsong.dto;

import java.io.Serializable;
import java.util.TreeMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreCountDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private TreeMap<String, Long> genreCount;
	
	public GenreCountDto() {
		this.genreCount = new TreeMap<>();
		
		this.genreCount.put("Blues", 0L);
		this.genreCount.put("Brass & Military", 0L);
		this.genreCount.put("Children's", 0L);
		this.genreCount.put("Classical", 0L);
		this.genreCount.put("Electronic", 0L);
		this.genreCount.put("Funk / Soul", 0L);
		this.genreCount.put("Hip Hop", 0L);
		this.genreCount.put("Jazz", 0L);
		this.genreCount.put("Latin", 0L);
		this.genreCount.put("Non-Music", 0L);
		this.genreCount.put("Pop", 0L);
		this.genreCount.put("Reggae", 0L);
		this.genreCount.put("Rock", 0L);
		this.genreCount.put("Stage & Screen", 0L);
	}
	
}
