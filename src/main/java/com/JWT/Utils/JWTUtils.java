package com.JWT.Utils;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.JWT.DTO.UserDetailsDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTUtils {

	@Value("${spring.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${spring.app.jwtExpirationMs}")
	private int jwtExpiration;
	
	public String generateTokenfromUserName(UserDetailsDTO userDetails) {
		String userName = userDetails.getUserName();
		return Jwts.builder()
				.subject(userName)
				.claim("userID", userName)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(key())
				.compact();
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
}
