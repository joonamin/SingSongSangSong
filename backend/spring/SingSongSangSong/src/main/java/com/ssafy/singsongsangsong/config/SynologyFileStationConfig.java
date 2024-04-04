package com.ssafy.singsongsangsong.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
public class SynologyFileStationConfig {
	// naming convention은 synology api를 따릅니다.
	@Value("${synology.host}")
	String host;

	@Value("${synology.account}")
	String account;

	@Value("${synology.passwd}")
	String passwd;

	@Value("${synology.baseDir}")
	String baseDir;
}

