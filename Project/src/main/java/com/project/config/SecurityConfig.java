package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
			//.csrf().disable() 타임리프에서 th:action을 사용하지 않고 post,put,delete맵핑에 접속시도시 csrf토큰이 없어서 403에러가 발생, 
			// 그 에러를 방지하기위해 csrf토큰 보안을 비활성화 시키는 것, th:action을 사용할경우 상관없음,
			.authorizeHttpRequests().requestMatchers("/").permitAll() //   /로 시작하는 주소로 접근시 모두 접근 가능하게 해줌,
			.requestMatchers(
					"/css/**",		// ~~~로 시작하는 주소에 대한 모든 접근을 허용,
					"/projectimg/**",		
					"/font/**",
					"/js/**"
					)
			.permitAll()
				.requestMatchers("/mypage").hasRole("USER") // 각각 주소에대한 접근 권한을 설정 ,
				.requestMatchers("/message").hasRole("MANAGER")
				.requestMatchers("/adsd").hasRole("ADMIN")
				.anyRequest().authenticated();	// 위 접근권한에 대해서 인증된 사람만 접근 가능하게 설정,
		http.formLogin(); // LOGIN폼을 설정해줄때 작성 , 
		return http.build();
	}


}
