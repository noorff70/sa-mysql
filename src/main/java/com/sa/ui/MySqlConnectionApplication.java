package com.sa.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MySqlConnectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySqlConnectionApplication.class, args);
	}

}
