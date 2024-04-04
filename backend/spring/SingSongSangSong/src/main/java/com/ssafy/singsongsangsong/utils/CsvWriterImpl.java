package com.ssafy.singsongsangsong.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.ssafy.singsongsangsong.annotation.CsvFileContents;
import com.ssafy.singsongsangsong.exception.NotMatchedWithCsvColumnsException;
import com.ssafy.singsongsangsong.exception.file.CreateFileException;

@Component
public class CsvWriterImpl implements CsvWriter {
	@Override
	public boolean createFileIfNotExists(String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			boolean isCreated = file.createNewFile();
			if (!isCreated)
				throw new CreateFileException("CSV 파일 생성에 실패했습니다.");
			return false;
		}
		return true;
	}

	@Override
	public boolean initializeCsvFile(CsvFileContents format, String path) throws IOException {
		String[] columns = null;
		if (format == CsvFileContents.ARTIST_SONG_RECORD) {
			columns = CsvFileContents.ARTIST_SONG_RECORD_COLUMNS;
		}
		if (columns == null) {
			throw new IllegalArgumentException("csv format을 확인해주세요");
		}
		writeCsvFile(path, columns, false);
		return true;
	}

	@Override
	public boolean writeCsvFile(String path, String[] data, boolean isAppend) throws
		NotMatchedWithCsvColumnsException,
		IOException {
		if (data.length != CsvFileContents.ARTIST_SONG_RECORD_COLUMNS.length) {
			throw new NotMatchedWithCsvColumnsException();
		}

		String csvRawData = convertToCsv(data);
		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(path, isAppend));

		if (isAppend) {
			fileWriter.append(csvRawData);
		} else {
			fileWriter.write(csvRawData);
		}

		fileWriter.newLine();
		fileWriter.close();

		return true;
	}

	private String convertToCsv(String[] data) {
		return Stream.of(data).map(this::escapeSpecialCharacters).collect(Collectors.joining(","));
	}

	private String escapeSpecialCharacters(String data) {
		if (data == null) {
			throw new IllegalArgumentException("Input data cannot be null");
		}
		String escapedData = data.replaceAll("\\R", " ");
		if (data.contains(",") || data.contains("\"") || data.contains("'")) {
			data = data.replace("\"", "\"\"");
			escapedData = "\"" + data + "\"";
		}
		return escapedData;
	}
}
