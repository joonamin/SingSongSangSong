package com.ssafy.singsongsangsong.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@ConfigurationProperties(prefix = "minio")
@Getter
@Setter
@Slf4j
public class ObjectStoreConfig {
	private String endpoint;
	private String accessKey;
	private String secretKey;
	private String serviceName;

	@Bean
	public MinioClient minioClient() {
		return MinioClient.builder()
			.endpoint(endpoint)
			.credentials(accessKey, secretKey)
			.build();
	}

}
