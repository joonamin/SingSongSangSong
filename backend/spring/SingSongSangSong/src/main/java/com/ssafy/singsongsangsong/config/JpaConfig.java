package com.ssafy.singsongsangsong.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.ssafy.singsongsangsong.repository.maria")
public class JpaConfig {
}
