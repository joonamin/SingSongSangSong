package com.ssafy.singsongsangsong.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BpmDetailDto {
	
	private int count;
	private List<Long> songs;

}
