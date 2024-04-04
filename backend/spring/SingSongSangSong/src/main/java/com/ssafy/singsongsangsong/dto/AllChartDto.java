package com.ssafy.singsongsangsong.dto;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trend")
public class AllChartDto {
	
	private List<Long> weekly;
	private List<Long> world;
	private List<Long> korean;
	private List<Long> emotions;

}
