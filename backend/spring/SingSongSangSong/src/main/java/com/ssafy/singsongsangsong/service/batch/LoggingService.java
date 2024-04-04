package com.ssafy.singsongsangsong.service.batch;

import java.io.IOException;

/**
 * 아래는 batch job에서 사용할 csv 파일을 생성하는 서비스입니다.
 * exportCsvFile 메소드는 artistId와 songId를 받아서 로그 정보를 csv 파일로 export합니다.
 */
public interface LoggingService {
	void playingSongLogAsCsv(Long artistId, Long songId) throws IOException;
}
