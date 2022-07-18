package com.sa.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DisplayUIApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisplayUIApplication.class, args);
	}

}
