package com.yq.auction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@MapperScan("com.yq.auction.dao")
public class AppUser {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AppUser.class).web(WebApplicationType.NONE).run(args);
	}
}
