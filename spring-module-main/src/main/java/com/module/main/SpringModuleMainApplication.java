package com.module.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringModuleMainApplication {

	public static void main(String[] args) {
		System.out.println(String.format("%% %s", 1, 2));
		SpringApplication.run(SpringModuleMainApplication.class, args);
	}

}
