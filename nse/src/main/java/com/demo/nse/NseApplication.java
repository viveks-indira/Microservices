package com.demo.nse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication

public class NseApplication {

	public static void main(String[] args) {
		SpringApplication.run(NseApplication.class, args);
		System.out.println("Running Bse Service 7071");
	}

}
