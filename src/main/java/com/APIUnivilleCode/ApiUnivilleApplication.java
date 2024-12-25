package com.APIUnivilleCode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.APIUnivilleCode.model")
public class ApiUnivilleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiUnivilleApplication.class, args);
	}

}
