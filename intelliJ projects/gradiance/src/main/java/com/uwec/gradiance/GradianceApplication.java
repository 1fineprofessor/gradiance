package com.uwec.gradiance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GradianceApplication {
	@GetMapping("/")
		String home(){
			return "Hello world!";
		}

	public static void main(String[] args) {
		SpringApplication.run(GradianceApplication.class, args);
	}

}
