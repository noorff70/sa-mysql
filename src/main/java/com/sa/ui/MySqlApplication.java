package com.sa.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MySqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySqlApplication.class, args);
	}

}
