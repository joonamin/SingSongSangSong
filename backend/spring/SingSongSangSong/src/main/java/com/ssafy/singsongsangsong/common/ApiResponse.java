package com.ssafy.singsongsangsong.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
	private boolean success;
	private ErrorEntity error;
	private T data;
}
