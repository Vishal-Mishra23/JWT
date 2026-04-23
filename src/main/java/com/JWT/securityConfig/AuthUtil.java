package com.JWT.securityConfig;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.JWT.DTO.UserDetailsDTO;
import com.JWT.config.JWTKeyUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * Vishal Mishra
 */
@Component
@RequiredArgsConstructor
public class AuthUtil {

	
	private final JWTKeyUtil jwtKeyUtil;


	@Value("${spring.app.jwtSecret}")
	private String jwtSecret;

	@Value("${spring.app.jwtExpirationMs}")
	private Long jwtExpiration;

 

//	/**
//	 * @return
//	 */
//	private SecretKey getSecretKey() {
//		return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
//	}
//	
	/**
	 * @param privateKeyName
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 */
	private PrivateKey getPrivateKey(String privateKeyName)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		return jwtKeyUtil.getJwtPrivateKey(privateKeyName);
	}

	/**
	 * @param request
	 * @param userDTO
	 * @return
	 * @throws IOException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public String generateAccessToken(HttpServletRequest request ,UserDetailsDTO userDTO) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		String userName = userDTO.getUserName();
				return Jwts.builder()
				.subject(userName)
				.claim("ip", getClientip(request))
				.claim("device-ID",getDeviceID(request))
				.issuedAt(new Date())
				.audience().add(userName).and()
				.issuer("Vishal")
				.expiration(new Date(new Date().getTime() + Long.valueOf(jwtExpiration)))
				.signWith(getPrivateKey("jwtPrivateKeyMar2026.pem"))
				.compact();
	}


	/**
	 * @param request
	 * @return
	 */
	private String getDeviceID(HttpServletRequest request) {
		String xfHeader = request.getHeader("X-Device-ID");
		if (xfHeader==null) {
			return request.getRemoteHost();
		}
		return xfHeader;
	}

	/**
	 * @param request
	 * @return
	 */
	private String getClientip(HttpServletRequest request) {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader==null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0].trim();
	}

	public String getUserNameFromToken(HttpServletRequest request , String token) throws JwtException, IllegalArgumentException, CertificateException {
		
		Claims claim = Jwts.parser()
			.verifyWith(jwtKeyUtil.getJwtPublicKey("jwtPublicKeyMar2026.crt"))
			.build()
			.parseSignedClaims(token)
			.getPayload();
		
		String ipAddress = claim.get("ip", String.class);
		String deviceID = claim.get("device-ID", String.class);
		if (!ipAddress.equalsIgnoreCase(request.getHeader("X-Forwarded-For").split(",")[0].trim())
				|| !deviceID.equalsIgnoreCase(request.getHeader("X-Device-ID"))) {
			throw new JwtException("JWT Tamper");
		}

			return claim.getSubject();
	}

}
