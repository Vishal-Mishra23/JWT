package com.JWT.Controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JWT.DTO.LoginRequestDTO;
import com.JWT.DTO.LoginResponseDTO;
import com.JWT.DTO.SignUpRequestDTO;
import com.JWT.DTO.SignUpResponseDTO;
import com.JWT.securityConfig.AuthService;

import io.jsonwebtoken.security.InvalidKeyException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * Vishal Mishra
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	/**
	 * @param request
	 * @param loginRequestDTO
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 */
	@PostMapping("/login")

	public ResponseEntity<LoginResponseDTO> login(HttpServletRequest request,
			@RequestBody LoginRequestDTO loginRequestDTO)
			throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		return ResponseEntity.ok(authService.login(request, loginRequestDTO));
	}
	
	/**
	 * @param signUpRequestDTO
	 * @return
	 */
	@PostMapping("/signup")
	public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
		return ResponseEntity.ok(authService.signUp(signUpRequestDTO));
	}

}
