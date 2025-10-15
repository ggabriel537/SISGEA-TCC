package com.sisgea.sisgea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SisgeaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SisgeaApplication.class, args);
	}
}
