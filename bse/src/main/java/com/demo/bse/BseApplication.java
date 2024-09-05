package com.demo.bse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class BseApplication {
	public static void main(String[] args) {
		SpringApplication.run(BseApplication.class, args);
		System.out.println("Running Bse Service 7070");
	}
}
