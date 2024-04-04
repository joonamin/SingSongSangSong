package com.ssafy.singsongsangsong.exception.artist;

public class ArtistNotFoundException extends RuntimeException {
	public ArtistNotFoundException(String message) {
		super(message);
	}

	public ArtistNotFoundException() {
		super("Artist Not Found");
	}
}
