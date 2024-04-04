package com.ssafy.singsongsangsong.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CustomWebClient {

	@Value("${webclient.baseurl}")
	private String baseUrl;

	@Value("${webclient.contentType}")
	private String contentType;

	@Bean
	public org.springframework.web.reactive.function.client.WebClient webClient() {
		return org.springframework.web.reactive.function.client.WebClient.builder()
			.baseUrl(baseUrl)
			.defaultHeader("Content-Type", contentType)
			.build();
	}
}
