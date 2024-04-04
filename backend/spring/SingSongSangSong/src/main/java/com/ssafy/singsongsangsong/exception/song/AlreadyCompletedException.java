package com.ssafy.singsongsangsong.exception.song;

public class AlreadyCompletedException extends RuntimeException {
	public AlreadyCompletedException(String message) {
		super(message);
	}

	public AlreadyCompletedException() {
		super("fail to complete analyze. it has already analyzed result.");
	}
}
