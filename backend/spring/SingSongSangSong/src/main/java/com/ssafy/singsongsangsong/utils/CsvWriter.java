package com.ssafy.singsongsangsong.utils;

import java.io.IOException;

import com.ssafy.singsongsangsong.annotation.CsvFileContents;
import com.ssafy.singsongsangsong.exception.NotMatchedWithCsvColumnsException;

public interface CsvWriter {
	// 파일이 존재하지 않을 경우 생성한 다음, false를 리턴한다.
	boolean createFileIfNotExists(String path) throws IOException;

	boolean initializeCsvFile(CsvFileContents format, String path) throws IOException; // 성능 향상을 위해 List 대신 Arrays 사용

	boolean writeCsvFile(String path, String[] data, boolean isAppend) throws
		NotMatchedWithCsvColumnsException,
		IOException;
}
