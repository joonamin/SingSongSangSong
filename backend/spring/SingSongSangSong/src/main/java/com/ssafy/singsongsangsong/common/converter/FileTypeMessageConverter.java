package com.ssafy.singsongsangsong.common.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ssafy.singsongsangsong.constants.FileType;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileTypeMessageConverter implements Converter<String, FileType> {

	@Override
	public FileType convert(String source) {
		log.info("source: {}, FileType: {}", source, FileType.getByName(source));
		return FileType.getByName(source);
	}
}
