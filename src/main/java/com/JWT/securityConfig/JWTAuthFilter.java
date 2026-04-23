package com.JWT.securityConfig;

import java.io.IOException;
import java.security.cert.CertificateException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.JWT.Repository.CustomUserDetailsRepo;
import com.JWT.entityManager.UserDetailsEntity;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

	private final CustomUserDetailsRepo customUserDetailsRepo;
	private final AuthUtil authUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("incoming request : {}", request.getRequestURI());

		final String requestTokenHeader = request.getHeader("Authorization");

		if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = requestTokenHeader.split("Bearer ")[1];

		try {
			String userName = authUtil.getUserNameFromToken(request , token);
			if (userName == null || SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetailsEntity user = customUserDetailsRepo.findByUserName(userName).orElseThrow();
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null,
						user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			filterChain.doFilter(request, response);

		} catch (JwtException | IllegalArgumentException | CertificateException e) {
			log.error("Kya Hua Bhaiya");
			e.printStackTrace();
		    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token");
		    return ;
		}
	}

}
