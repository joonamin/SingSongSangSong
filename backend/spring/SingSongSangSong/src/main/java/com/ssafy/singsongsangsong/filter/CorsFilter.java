// package com.ssafy.singsongsangsong.filter;
//
// import java.io.IOException;
// import java.util.Arrays;
// import java.util.List;
//
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.stereotype.Component;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
// import jakarta.servlet.Filter;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.ServletRequest;
// import jakarta.servlet.ServletResponse;
// import jakarta.servlet.http.HttpServletResponse;
//
// @Configuration
// public class CorsFilter implements WebMvcConfigurer { // WebMvcConfigurer 구현
// 	@Override
// 	public void addCorsMappings(CorsRegistry registry) {
// 		registry.addMapping("/api") // 적용할 path 패턴을 입력
// 			.allowedOrigins("http://localhost:3000","https://api.singsongsangsong.com","https://www.singsongsangsong.com","http://localhost:8080") // 허가할 출처들을 기입
// 			.allowedMethods(                // 허가할 메서드를 기입
// 				HttpMethod.GET.name(),
// 				HttpMethod.POST.name(),
// 				HttpMethod.PUT.name(),
// 				HttpMethod.DELETE.name(),
// 				HttpMethod.OPTIONS.name()
// 			);
// 	}
// }