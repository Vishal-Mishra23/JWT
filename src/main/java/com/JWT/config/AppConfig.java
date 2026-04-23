package com.JWT.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.JWT.CustomUserDetailsServices.CustomUserDetailsServicesSVC;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

	private final CustomUserDetailsServicesSVC customUserDetailsServicesSVC;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public AuthenticationManager authenticationManager(PasswordEncoder bcryptEncode) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(customUserDetailsServicesSVC);
		daoAuthenticationProvider.setPasswordEncoder(bcryptEncode);
		return new ProviderManager(daoAuthenticationProvider);
	}

//	@Bean
//	public UserDetailsService userServiceDetail() {
//		UserDetails user1 = User.withUsername("user1").password(bcryptEncode().encode("password")).roles("USER")
//				.build();
//		UserDetails admin = User.withUsername("admin").password(bcryptEncode().encode("admin")).roles("ADMIN").build();
//		JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//		userDetailsManager.createUser(user1);
//		userDetailsManager.createUser(admin);
//		return userDetailsManager;
//		return new InMemoryUserDetailsManager(user1,admin);
//	}
	@Bean
	public PasswordEncoder bcryptEncode() {
		return new BCryptPasswordEncoder();
	}

}
