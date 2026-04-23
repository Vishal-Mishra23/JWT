package com.JWT.Services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTService {
	
//	@PostMapping(value = "/numbers", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	public Flux<Integer> generateNumbers(@RequestBody RequestDto req) {
//	    return Flux.range(1, req.getCount())
//	               .delayElements(Duration.ofSeconds(1));
//	}
	
	@GetMapping(value = "/Hello")
	public String  helloService(){
		return "Hello";
	}


}
