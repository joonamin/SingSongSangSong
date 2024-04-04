package com.ssafy.singsongsangsong.aop.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.ssafy.singsongsangsong.annotation.CsvFileContents;
import com.ssafy.singsongsangsong.annotation.ExportCsvFile;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

	@AfterReturning(pointcut = "@annotation(com.ssafy.singsongsangsong.annotation.ExportCsvFile)")
	public void loggingAsCsv(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
		Method method = methodSignature.getMethod();

		ExportCsvFile annotation = method.getAnnotation(ExportCsvFile.class);
		CsvFileContents format = annotation.format();

		// {artistId, songId} 의 형식으로 method에 전달된 parameter를 가져옵니다.
		if (format.name().equals("ARTIST_SONG_RECORD")) {
			Object[] args = joinPoint.getArgs();
			if (args.length != 2 || !(args[0] instanceof Long) || !(args[1] instanceof Long)) {
				log.error("메소드에 전달된 parameter는 {artistId: Long, songId: Long} 형식이어야 합니다. 현재 전달된 parameter: {args: "
					+ Arrays.toString(args) + "}");
				return;
			}
			Long[] record = {(Long)args[0], (Long)args[1]};

			log.info("Exporting artist song record as csv file...");
		}
	}

}
