package com.example.StayNest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
//@EnableMethodSecurity
public class StayNestApplication {

	public static void main(String[] args) {
		SpringApplication.run(StayNestApplication.class, args);
	}

}
