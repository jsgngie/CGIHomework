package com.cinema.demo;

import com.cinema.demo.data.LocalDatabase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CinemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(LocalDatabase localDatabase) {
		return args -> {
			localDatabase.readMoviesTSV();
			localDatabase.readRatingTSV();
		};
	}
}
