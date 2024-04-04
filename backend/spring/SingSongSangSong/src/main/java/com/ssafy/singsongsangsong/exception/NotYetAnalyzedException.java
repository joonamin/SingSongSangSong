package com.ssafy.singsongsangsong.exception;

public class NotYetAnalyzedException extends RuntimeException {

	public NotYetAnalyzedException() {
		super("아직 분석되지 않은 곡입니다.");
	}
}
