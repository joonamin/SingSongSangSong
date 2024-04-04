package com.ssafy.singsongsangsong.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.singsongsangsong.entity.Artist;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;
import com.ssafy.singsongsangsong.security.ArtistPrincipal;
import com.ssafy.singsongsangsong.security.PasswordUtil;
import com.ssafy.singsongsangsong.service.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {
	private static final String NO_CHECK_URL = "/login";

	private final JwtService jwtService;
	private final ArtistRepository artistRepository;

	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		if (request.getRequestURI().equals(NO_CHECK_URL)) {
			filterChain.doFilter(request, response);
			return;
		}
		checkAccessTokenAndAuthentication(request, response, filterChain);
	}

	public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain)
		throws ServletException, IOException {
		log.info("checkAccessTokenAndAuthentication() 호출");

		Optional<String> accessToken = jwtService.extractAccessToken(request)
			.filter(jwtService::isTokenValid);
		log.info("accessToken : {}", accessToken);
		// 회원이 access token을 가지고 요청을 보낸 상황
		if (accessToken.isPresent()) {
			log.info("access token이 존재합니다");
			String token = accessToken.get();
			String email = jwtService.extractEmail(token)
				.orElseThrow(() -> new JwtException("can't extract email"));

			Artist loadedUser = artistRepository.findByUsername(email)
				.orElseThrow(() -> new JwtException("can't find user with email"));
			saveAuthentication(loadedUser);
		} else {
			log.info("access token이 존재하지 않습니다");
		}

		filterChain.doFilter(request, response);
	}

	public void saveAuthentication(Artist myUser) {
		String password = myUser.getPassword();
		if (password == null) {
			password = PasswordUtil.generateRandomPassword();
		}
		UserDetails userDetails = User.builder()
			.username(myUser.getUsername())
			.password(password)
			.roles(myUser.getRole().name())
			.build();

		ArtistPrincipal principal = ArtistPrincipal.builder().id(myUser.getId())
			.username(myUser.getUsername())
			.nickname(myUser.getNickname())
			.build();

		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null,
			authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
