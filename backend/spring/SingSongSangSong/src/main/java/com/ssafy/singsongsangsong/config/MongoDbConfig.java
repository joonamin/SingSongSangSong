package com.ssafy.singsongsangsong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.ssafy.singsongsangsong.repository.mongo")
@RequiredArgsConstructor
public class MongoDbConfig {

	private final MongoMappingContext mongoMappingContext;

	@Bean
	public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory mongoDbFactory,
		MongoMappingContext mongoMappingContext) {

		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
		MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
		mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
		return mappingConverter;
	}
}
