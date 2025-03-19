package com.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class EcomGlobeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomGlobeApplication.class, args);

		System.out.println("Welcome to EcomGlobe");
	}

}
