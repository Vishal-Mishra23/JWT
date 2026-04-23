package com.JWT.securityConfig;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.JWT.DTO.LoginRequestDTO;
import com.JWT.DTO.LoginResponseDTO;
import com.JWT.DTO.SignUpRequestDTO;
import com.JWT.DTO.SignUpResponseDTO;
import com.JWT.DTO.UserDetailsDTO;
import com.JWT.Repository.CustomUserDetailsRepo;
import com.JWT.entityManager.UserDetailsEntity;

import io.jsonwebtoken.security.InvalidKeyException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final CustomUserDetailsRepo customUserDetailsRepo;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	private final AuthUtil authUtil;

	public LoginResponseDTO login(HttpServletRequest request, LoginRequestDTO loginRequestDTO)
			throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserName(), loginRequestDTO.getPassword()));
		// TODO Auto-generated method stub

		UserDetailsEntity userEntity = (UserDetailsEntity) authentication.getPrincipal();
		UserDetailsDTO userDTO = modelMapper.map(userEntity, UserDetailsDTO.class);
		String token = authUtil.generateAccessToken(request, userDTO);
		return new LoginResponseDTO(token, userDTO.getId());
	}

	public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) {
		UserDetailsEntity userDetails = customUserDetailsRepo.findByUserName(signUpRequestDTO.getUserName())
				.orElse(null);
		if (userDetails != null) {
			throw new IllegalArgumentException("User Already Exist");
		}

		userDetails = customUserDetailsRepo.save(UserDetailsEntity.builder().userName(signUpRequestDTO.getUserName())
				.password(passwordEncoder.encode(signUpRequestDTO.getPassword())).mobNo(signUpRequestDTO.getMobNo())
				.build());
		// TODO Auto-generated method stub
		return modelMapper.map(userDetails, SignUpResponseDTO.class);
	}

}
