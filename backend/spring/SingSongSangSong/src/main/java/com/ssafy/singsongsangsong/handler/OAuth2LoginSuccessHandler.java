package com.ssafy.singsongsangsong.handler;

import java.io.IOException;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ssafy.singsongsangsong.constants.Role;
import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;
import com.ssafy.singsongsangsong.security.oauth2.CustomOAuth2User;
import com.ssafy.singsongsangsong.service.jwt.JwtService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtService jwtService;
	private final ArtistRepository artistRepository;
	private final Environment env;
	private String REDIRECT_URL;

	@PostConstruct
	void init() {
		REDIRECT_URL = env.getProperty("spring.security.singsongsangsong.redirect-uri");
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		log.info("OAuth2 Login 성공");
		try {
			CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
			if (oAuth2User.getRole() == Role.GUEST) {
				String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
				response.sendRedirect(REDIRECT_URL + "sign-up?accessToken=" + accessToken);
			} else {
				loginSuccess(response, oAuth2User);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
		Artist user = artistRepository.findByUsername(oAuth2User.getEmail()).orElseThrow();
		Long userId = user.getId();

		String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());

		response.sendRedirect(REDIRECT_URL + "?accessToken=" + accessToken);
	}
}
