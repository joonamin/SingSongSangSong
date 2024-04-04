package com.ssafy.singsongsangsong.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.singsongsangsong.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import com.ssafy.singsongsangsong.filter.JwtAuthenticationProcessingFilter;
import com.ssafy.singsongsangsong.handler.OAuth2LoginFailureHandler;
import com.ssafy.singsongsangsong.handler.OAuth2LoginSuccessHandler;
import com.ssafy.singsongsangsong.repository.maria.artist.ArtistRepository;
import com.ssafy.singsongsangsong.service.jwt.CustomOAuth2UserService;
import com.ssafy.singsongsangsong.service.jwt.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
	private final JwtService jwtService;
	private final ArtistRepository artistRepository;
	private final ObjectMapper objectMapper;
	private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
	private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
	private final CustomOAuth2UserService customOAuth2UserService;
	private final JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter;
	// private final CorsFilter corsFilter;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// .cors(cors -> cors.disable())
			.cors(cors -> corsConfigurationSource())
			.formLogin(auth -> auth.disable())
			.csrf(auth -> auth.disable())
			.httpBasic(auth -> auth.disable())
			.sessionManagement(auth -> auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(req ->
					req.requestMatchers("/", "error","/sign-up").permitAll()
						// .requestMatchers("/join").hasRole("GUEST")
						.anyRequest().permitAll()
				// .anyRequest().permitAll())
			)
			.oauth2Login(oauth2 ->
				oauth2
					.successHandler(oAuth2LoginSuccessHandler)
					.failureHandler(oAuth2LoginFailureHandler)
					.userInfoEndpoint(userInfo -> userInfo
						.userService(customOAuth2UserService)))
			.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class)
			.addFilterBefore(jwtAuthenticationProcessingFilter, CustomJsonUsernamePasswordAuthenticationFilter.class);
			// .addFilterBefore(corsFilter, SessionManagementFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		// provider.setUserDetailsService(loginService);
		return new ProviderManager(provider);
	}
	@Bean
	public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
		CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
			= new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
		customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
		return customJsonUsernamePasswordLoginFilter;
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowedOrigins(List.of("*"));
		config.setAllowedMethods(List.of("*"));
		config.setAllowedHeaders(List.of("*"));
		config.setMaxAge(1000L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		log.info("source : {}",source);

		return source;
	}
}
