package com.ssafy.singsongsangsong.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.TreeMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtmosphereCountDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private TreeMap<String, Long> atmosphereCount;
	
	public AtmosphereCountDto() {
		this.atmosphereCount = new TreeMap<>(Collections.reverseOrder());
		
		this.atmosphereCount.put("happy", 0L);
		this.atmosphereCount.put("sad", 0L);
		this.atmosphereCount.put("calm", 0L);
		this.atmosphereCount.put("exciting", 0L);
		this.atmosphereCount.put("love", 0L);
		this.atmosphereCount.put("inspiring", 0L);
		this.atmosphereCount.put("mystery", 0L);
		this.atmosphereCount.put("nostalgia", 0L);
		this.atmosphereCount.put("etc", 0L);
	}

}
