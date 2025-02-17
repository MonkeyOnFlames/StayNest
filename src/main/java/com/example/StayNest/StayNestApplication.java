package com.example.StayNest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMongoAuditing
@EnableMethodSecurity
public class StayNestApplication {

	public static void main(String[] args) {
		SpringApplication.run(StayNestApplication.class, args);
	}

}
