package com.uwec.gradiance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GradianceApplication {
	// http://localhost:8085 will let you test when run locally.
	// when run on the server it is just https://gradiance.org
	public static void main(String[] args) {
		SpringApplication.run(GradianceApplication.class, args);
	}

}
