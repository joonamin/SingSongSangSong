package com.ssafy.singsongsangsong.exception.file;

public class NotFoundFileException extends RuntimeException {
	public NotFoundFileException(String message) {
		super(message);
	}

	public NotFoundFileException() {
		super("파일을 찾을 수 없습니다.");
	}
}
