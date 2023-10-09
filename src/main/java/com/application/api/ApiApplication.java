/**
 *  Class Application
 *  version 1.1
 *  Shaternikov Alexey
 *  10.06.2023
 *
 *  This program download movies from developer.themoviedb.org.
 *  You can add and delete movies from favorites.
 *  You can register, delete and update current user.
 *
 */



package com.application.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
