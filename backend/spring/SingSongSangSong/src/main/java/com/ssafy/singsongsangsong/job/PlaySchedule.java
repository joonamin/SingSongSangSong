package com.ssafy.singsongsangsong.job;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PlaySchedule {
	
	private final JobLauncher jobLauncher;
	private final PlayJobConfig playJobConfig;
	
	@Scheduled(cron = "0 0 3 ? * MON")
	public void playCount() throws Exception {
		jobLauncher.run(playJobConfig.playJob(), new JobParametersBuilder().toJobParameters());
	}

}
