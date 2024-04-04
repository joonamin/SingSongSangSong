package com.ssafy.singsongsangsong.dto;

import com.ssafy.singsongsangsong.entity.Structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionElementDto {
	private String label;
	private int start;
	private int end;

	public static SectionElementDto from(Structure structure) {
		SectionElementDto sectionElementDto = new SectionElementDto();
		sectionElementDto.setLabel(structure.getLabel());
		sectionElementDto.setStart(structure.getStartTime());
		sectionElementDto.setEnd(structure.getEndTime());

		return sectionElementDto;
	}
}
