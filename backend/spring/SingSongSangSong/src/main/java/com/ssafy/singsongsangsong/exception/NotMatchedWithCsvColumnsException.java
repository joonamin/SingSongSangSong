package com.ssafy.singsongsangsong.exception;

public class NotMatchedWithCsvColumnsException extends RuntimeException {

	public NotMatchedWithCsvColumnsException(String message) {
		super(message);
	}

	public NotMatchedWithCsvColumnsException() {
		super("데이터가 CSV 파일의 컬럼과 매칭되지 않습니다. 컬럼을 초기화하거나 데이터를 확인해주세요.");
	}
}
