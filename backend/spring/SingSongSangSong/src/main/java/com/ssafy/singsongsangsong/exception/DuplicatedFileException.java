package com.ssafy.singsongsangsong.exception;

import org.apache.coyote.BadRequestException;

public class DuplicatedFileException extends BadRequestException {
	public DuplicatedFileException(String s) {
		super(s);
	}
}
