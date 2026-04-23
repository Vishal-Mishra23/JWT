package com.JWT.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {
	

	@PostMapping("/hello")
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("Hello");
	}

	

}
