package com.ssafy.singsongsangsong.common.converter;

import org.springframework.core.convert.converter.Converter;

import com.ssafy.singsongsangsong.constants.EmotionsConstants;

public class EmotionTypeMessageConverter implements Converter<java.lang.String, EmotionsConstants> {

	@Override
	public EmotionsConstants convert(java.lang.String source) {
		source = source.toUpperCase();
		return EmotionsConstants.valueOf(source);
	}
}
