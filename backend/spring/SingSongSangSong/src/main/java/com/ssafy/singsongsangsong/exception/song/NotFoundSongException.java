package com.ssafy.singsongsangsong.exception.song;

public class NotFoundSongException extends RuntimeException {

	public NotFoundSongException(String message) {
		super(message);
	}

	public NotFoundSongException() {
		super("check the song Id: it does not exist.");
	}

}
