package com.JWT.CustomUserDetailsServices;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.JWT.Repository.CustomUserDetailsRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServicesSVC implements UserDetailsService {

	private final CustomUserDetailsRepo customUserDetailsRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return (UserDetails) customUserDetailsRepo.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Name Not Found"));
	}

}
