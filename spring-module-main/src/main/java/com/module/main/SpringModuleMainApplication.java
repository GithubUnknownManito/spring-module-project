package com.module.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@MapperScan("com.module.**.dao.**")
@ComponentScan({"com.module.main.**", "com.module.crud.common"})
public class SpringModuleMainApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringModuleMainApplication.class, args);
	}
}
