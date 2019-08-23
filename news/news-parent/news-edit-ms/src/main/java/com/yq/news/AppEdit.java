package com.yq.news;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("com.yq.news.mapper")
@EnableEurekaClient
public class AppEdit {

	public static void main(String[] args) {
		SpringApplication.run(AppEdit.class, args);
	}
}
