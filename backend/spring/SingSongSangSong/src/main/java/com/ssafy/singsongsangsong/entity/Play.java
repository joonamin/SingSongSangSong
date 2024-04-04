package com.ssafy.singsongsangsong.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Play implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long songId;
	private String genre;
	private String atmosphere;
	private String age;
	private String sex;

}
