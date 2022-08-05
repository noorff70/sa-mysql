package com.sa.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MySqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySqlApplication.class, args);
	}

}
