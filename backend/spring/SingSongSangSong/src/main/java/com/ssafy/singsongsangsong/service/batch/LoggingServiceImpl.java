package com.ssafy.singsongsangsong.service.batch;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.singsongsangsong.annotation.CsvFileContents;
import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.entity.Song;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;
import com.ssafy.singsongsangsong.repository.maria.song.SongRepository;
import com.ssafy.singsongsangsong.utils.CsvWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoggingServiceImpl implements LoggingService {

	private final ArtistRepository artistRepository;
	private final SongRepository songRepository;
	private final CsvWriter csvWriter;

	@Value("${csv.file.dir}")
	private String csvFileDir;

	@Override
	public void playingSongLogAsCsv(Long artistId, Long songId) throws IOException {
		Artist player = artistRepository.findById(artistId)
			.orElseThrow(() -> new IllegalArgumentException("해당 아티스트가 존재하지 않습니다."));
		Song targetSong = songRepository.findById(songId)
			.orElseThrow(() -> new IllegalArgumentException("해당 노래가 존재하지 않습니다."));

		String filePath = getFilePath();
		String data[] = {String.valueOf(songId), targetSong.getCustomGenre(), targetSong.getThemes(),
			String.valueOf(player.getSex())};

		boolean isAlreadyExists = csvWriter.createFileIfNotExists(filePath);
		if (isAlreadyExists) {
			csvWriter.writeCsvFile(filePath, data, true);
		} else {
			csvWriter.initializeCsvFile(CsvFileContents.ARTIST_SONG_RECORD, filePath);
		}
	}

	// yyyy-mm-dd-log.csv
	private String getFilePath() {
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return csvFileDir + File.separator + today + "-log.csv";
	}

}
