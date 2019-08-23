package com.yq.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController implements HelloService {

	@Override
	public String getHello() {
		
		return "good afterroon!";
	}

}
