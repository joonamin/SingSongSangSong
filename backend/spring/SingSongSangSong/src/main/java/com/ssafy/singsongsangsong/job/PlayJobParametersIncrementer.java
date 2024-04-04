package com.ssafy.singsongsangsong.job;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

public class PlayJobParametersIncrementer implements JobParametersIncrementer {

    @Override
    public JobParameters getNext(JobParameters parameters) {
        if (parameters == null || parameters.isEmpty()) {
            // 최초 실행이라면, 파라미터를 생성합니다.
            return new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
        } else {
            // 이전 실행에서 파라미터를 얻어온 후, 파라미터를 수정하여 반환합니다.
            long timestamp = parameters.getLong("timestamp", System.currentTimeMillis());
            return new JobParametersBuilder(parameters)
                .addLong("timestamp", timestamp + 1)
                .toJobParameters();
        }
    }
}
