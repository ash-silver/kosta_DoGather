package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().requestMatchers(
				"/member/join"
				,"/index"
				,"/list"
				,"/products/{p_id}"
				,"/products/options"
				,"/products/options/{p_id}"
				,"/reviews/**"
				,"/comment"
				).permitAll()
				.requestMatchers("/orders/**","/orders","/ordersheet","/qnaboard/**").hasAnyRole("SELLER","USER")
				.requestMatchers("/products/**","/products/options/**","/products/charts/**","/products/charts**").hasRole("SELLER")
				.requestMatchers("/**").hasRole("ADMIN")
				.anyRequest().authenticated(); // 위 접근권한에 대해서 인증된 사람만 접근 가능하게 설정,
		http.formLogin() // LOGIN폼을 설정해줄때 작성 ,
				.loginPage("/member/login") // 로그인을 실행하게될 FORM페이지로의 이동
				.loginProcessingUrl("/login/") // 해당하는 url로 접근시 로그인의 기능이 작동, request되는 값을 컨트롤러가 아닌 이곳으로 넘겨받음.
				.usernameParameter("m_email") // default= Username 파라미터로 넘어오는 값의 이름을 설정
				.passwordParameter("m_pwd")// default =Password
				.defaultSuccessUrl("/index", true) // 로그인 성공시 이동하는 페이지
				.failureUrl("/login") // 로그인 실패시 이동하는 페이지
				.permitAll().and().logout() // 로그아웃에 대한 설정
				.logoutUrl("/logout"); // 로그아웃 처리 URL, default: /logout, 원칙적으로 post 방식만 지원
//		  .logoutSuccessUrl("/logout/") // 로그아웃 성공 후 이동페이지 // OAuth2 성공시 redirect
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() { // css,js,img등의 시큐리티 필터적용이 필요없는 자원에 대한 접근을 설정,
		return (web) -> web.ignoring().requestMatchers("/css/**", "/font/**", "/projectimg/**", "/favicon.ico",
				"/error", "/js/**");
	}

}