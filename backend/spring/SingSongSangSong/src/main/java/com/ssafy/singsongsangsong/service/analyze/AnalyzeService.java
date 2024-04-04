package com.ssafy.singsongsangsong.service.analyze;

import com.ssafy.singsongsangsong.dto.PublishSongRequest;
import com.ssafy.singsongsangsong.dto.SimpleSongDto;
import com.ssafy.singsongsangsong.dto.UploadMainPageDto;
import com.ssafy.singsongsangsong.exception.song.AlreadyCompletedException;

public interface AnalyzeService {
	public void completeAnalyze(Long songId) throws AlreadyCompletedException;

	public UploadMainPageDto getUploadStatus(Long artistId);

	public void publishSong(Long songId);

	public void registerPublishedInformation(PublishSongRequest dto);

	public SimpleSongDto getSongAnalistics(Long songId);

	public void requestAnalyze(Long artistId, Long songId);
}
