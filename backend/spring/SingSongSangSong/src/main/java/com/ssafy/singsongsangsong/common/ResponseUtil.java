package com.ssafy.singsongsangsong.common;

public class ResponseUtil {
	private ResponseUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static <T> ApiResponse<T> ok(T data) {
		return ApiResponse.<T>builder()
			.success(true)
			.data(data)
			.build();
	}

	public static ApiResponse<?> error(ErrorEntity error) {
		return ApiResponse.builder()
			.success(false)
			.error(error)
			.build();
	}
}
