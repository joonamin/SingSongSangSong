package com.ssafy.singsongsangsong.utils;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ssafy.singsongsangsong.annotation.CsvFileContents;

class CsvWriterImplTest {

	CsvWriter csvWriter = new CsvWriterImpl();

	String path = "./test.csv";
	BufferedReader bufferedReader;

	String[][] dataset = {
		{"1", "pop", "exciting", "20", "F"},
		{"2", "ROCK", "inspiring", "15", "F"},
		{"3", "hiphop", "exciting", "25", "M"},
		{"4", "kpop", "happy", "27", "M"}
	};

	@BeforeEach
	void setUp() throws IOException {
		csvWriter.initializeCsvFile(CsvFileContents.ARTIST_SONG_RECORD, path);
	}

	@AfterEach
	void tearDown() {
		File file = new File(path);
		file.delete();
	}

	@Test
	void createFileIfNotExists() throws IOException {
		boolean isAlreadyExists = csvWriter.createFileIfNotExists(path);
		assertThat(isAlreadyExists).isTrue();
		File file = new File(path);
		boolean deleted = file.delete();
		if (deleted) {
			assertThat(csvWriter.createFileIfNotExists(path)).isFalse();
		}
	}

	@Test
	void initializeCsvFile() throws IOException {
		boolean isInitialized = csvWriter.initializeCsvFile(CsvFileContents.ARTIST_SONG_RECORD, path);
		assertThat(isInitialized).isTrue();

		bufferedReader = new BufferedReader(new FileReader(path));
		String[] columns = bufferedReader.readLine().split(",");
		for (int i = 0; i < columns.length; i++) {
			assertThat(columns[i]).isEqualTo(CsvFileContents.ARTIST_SONG_RECORD_COLUMNS[i]);
		}
		bufferedReader.close();
	}

	@Test
	void writeCsvFile() throws IOException {
		bufferedReader = new BufferedReader(new FileReader(path));
		// 첫 라인은 컬럼들에 대한 제목이므로, 넘어간다
		bufferedReader.readLine();
		for (String[] data : dataset) {
			boolean isWritten = csvWriter.writeCsvFile(path, data, true);
			assertThat(isWritten).isTrue();
			String[] columns = bufferedReader.readLine().split(",");
			for (int i = 0; i < columns.length; i++) {
				assertThat(columns[i]).isEqualTo(data[i]);
			}
		}
		bufferedReader.close();
	}
}