package com.ssafy.singsongsangsong.dto;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgeSexPartDto {
	
	private int age;
	private char sex;
	private String part;
	
	@Override
	public int hashCode() {
		return Objects.hash(age, part, sex);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgeSexPartDto other = (AgeSexPartDto) obj;
		return age == other.age && Objects.equals(part, other.part) && sex == other.sex;
	}

}
