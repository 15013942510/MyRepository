package com.web.shopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("com.web.shopping.mapper")
@EnableEurekaClient
public class ManagerApp {

	public static void main(String[] args) {
		SpringApplication.run(ManagerApp.class, args);
	}
}
