package com.ssafy.singsongsangsong.exception.emotion;

import java.util.NoSuchElementException;

public class NotSupportedEmotionException extends NoSuchElementException {
	public NotSupportedEmotionException(String msg) {
		super(msg);
	}

	public NotSupportedEmotionException() {
		super("지원하지 않는 감정입니다.");
	}
}
