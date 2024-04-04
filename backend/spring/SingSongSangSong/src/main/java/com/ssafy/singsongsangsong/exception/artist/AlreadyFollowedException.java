package com.ssafy.singsongsangsong.exception.artist;

public class AlreadyFollowedException extends RuntimeException {
	public AlreadyFollowedException(String msg) {
		super(msg);
	}

	public AlreadyFollowedException() {
		super("이미 팔로우한 아티스트입니다.");
	}
}
