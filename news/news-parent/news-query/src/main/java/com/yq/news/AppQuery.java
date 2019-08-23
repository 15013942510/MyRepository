package com.yq.news;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("com.yq.news.mapper")
@EnableEurekaClient
public class AppQuery {

	public static void main(String[] args) {
		SpringApplication.run(AppQuery.class, args);
	}
}
