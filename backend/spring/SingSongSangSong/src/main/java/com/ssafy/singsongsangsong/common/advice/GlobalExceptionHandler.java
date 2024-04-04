package com.ssafy.singsongsangsong.common.advice;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ssafy.singsongsangsong.common.ErrorEntity;
import com.ssafy.singsongsangsong.exception.DuplicatedFileException;
import com.ssafy.singsongsangsong.exception.NotYetAnalyzedException;
import com.ssafy.singsongsangsong.exception.artist.AlreadyFollowedException;
import com.ssafy.singsongsangsong.exception.artist.ArtistNotFoundException;
import com.ssafy.singsongsangsong.exception.common.BusinessException;
import com.ssafy.singsongsangsong.exception.file.NotFoundFileException;
import com.ssafy.singsongsangsong.exception.song.AlreadyCompletedException;
import com.ssafy.singsongsangsong.exception.song.NotFoundSongException;
import com.ssafy.singsongsangsong.exception.song.PleaseRetryException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler({JWTVerificationException.class, JWTDecodeException.class})
	public ErrorEntity handleJwtException(RuntimeException e, HttpServletResponse response) {
		String errorMessage;
		if (e instanceof JWTVerificationException) {
			errorMessage = "토큰이 유효하지 않습니다.";
		} else if (e instanceof JWTDecodeException) {
			errorMessage = "토큰을 디코딩하는 중 오류가 발생했습니다.";
		} else {
			errorMessage = "something wrong with jwt token.";
		}

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		return ErrorEntity.builder()
			.code(HttpStatus.UNAUTHORIZED.toString())
			.message(errorMessage)
			.build();
	}

	@ExceptionHandler({NotFoundFileException.class, NotFoundSongException.class, ArtistNotFoundException.class})
	public ErrorEntity handleNotFoundException(RuntimeException e, HttpServletResponse response) {
		String errorMessage;
		if (e instanceof NotFoundFileException) {
			errorMessage = "파일을 찾을 수 없습니다.";
		} else if (e instanceof NotFoundSongException) {
			errorMessage = "노래를 찾을 수 없습니다.";
		} else if (e instanceof ArtistNotFoundException) {
			errorMessage = "아티스트를 찾을 수 없습니다.";
		} else {
			errorMessage = "찾을 수 없는 리소스입니다.";
		}

		response.setStatus(HttpStatus.NOT_FOUND.value());
		return ErrorEntity.builder()
			.code(HttpStatus.NOT_FOUND.toString())
			.message(errorMessage)
			.build();
	}

	@ExceptionHandler({AlreadyCompletedException.class, AlreadyFollowedException.class})
	public ErrorEntity handleAlreadyException(RuntimeException e, HttpServletResponse response) {
		String errorMessage;
		if (e instanceof AlreadyCompletedException) {
			errorMessage = "이미 분석이 완료되었습니다.";
		} else if (e instanceof AlreadyFollowedException) {
			errorMessage = "이미 팔로우한 아티스트입니다.";
		} else {
			errorMessage = "이미 처리된 요청입니다.";
		}

		response.setStatus(HttpStatus.CONFLICT.value());
		return ErrorEntity.builder()
			.code(HttpStatus.CONFLICT.toString())
			.message(errorMessage)
			.build();
	}

	@ExceptionHandler({PleaseRetryException.class})
	public ErrorEntity handlePleaseRetryException(RuntimeException e, HttpServletResponse response) {
		response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		return ErrorEntity.builder()
			.code(HttpStatus.NOT_ACCEPTABLE.toString())
			.message("요청을 처리하지 못했습니다. 다시 시도해주세요.")
			.build();
	}

	@ExceptionHandler({NotYetAnalyzedException.class})
	public ErrorEntity handleNotYetAnalyzedException(RuntimeException e, HttpServletResponse response) {
		response.setStatus(HttpStatus.TOO_EARLY.value());
		return ErrorEntity.builder()
			.code(HttpStatus.TOO_EARLY.toString())
			.message("아직 분석이 완료되지 않았습니다.")
			.build();
	}

	@ExceptionHandler({MalformedURLException.class, DuplicatedFileException.class})
	public ErrorEntity handleMalformedURLException(IOException e, HttpServletResponse response) {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		return ErrorEntity.builder()
			.code(HttpStatus.BAD_REQUEST.toString())
			.message(e.getMessage())
			.build();
	}

	@ExceptionHandler({BusinessException.class, Exception.class})
	public ErrorEntity handleBusinessException(Exception e, HttpServletResponse response) {
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		log.error(e.getMessage());
		log.error(e.getLocalizedMessage());
		return ErrorEntity.builder()
			.code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
			.message("서버 관리자에게 문의하세요..ㅜㅜ [msg] => " + e.getMessage())
			.build();
	}

}
