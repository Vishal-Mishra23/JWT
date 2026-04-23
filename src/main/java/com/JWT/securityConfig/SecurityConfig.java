package com.JWT.securityConfig;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

//	private final DataSource dataSource;
	private final JWTAuthFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(
				(requests)->requests.requestMatchers("/h2-console/**","/auth/**")
									   .permitAll()
									   .requestMatchers("/admin/**")
									   .hasRole("ADMIN")
									 .requestMatchers("/jwtCreate/**")
									   .hasAnyRole("ADMIN", "USER")
									 .anyRequest().authenticated())
//									 .formLogin(withDefaults())
									 .httpBasic(withDefaults())
									 .csrf(csrf -> csrf.disable())
									 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
									 .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class)
									 .build();
////		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
//		http.formLogin(withDefaults());
//		http.httpBasic(withDefaults());
//		http.headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
//		http.csrf(csrf -> csrf.disable());

//		return http.build();
	}

}
