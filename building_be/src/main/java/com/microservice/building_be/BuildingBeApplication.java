package com.microservice.building_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BuildingBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuildingBeApplication.class, args);
	}

}
