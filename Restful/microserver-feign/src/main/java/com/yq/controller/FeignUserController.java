package com.yq.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yq.service.FeignUserService;
import com.yq.service.UserHelloService;

@RestController
public class FeignUserController {
	
	@Autowired
	private FeignUserService feignUserService;
	
	@Autowired
	private UserHelloService helloService;
	
	@RequestMapping("/loadAllName")
	public List<String> loadAllName(){
		
		return feignUserService.getUserList();
	}
	
	@RequestMapping("sayHello")
	public String sayHello() {
		
		return helloService.getHello();
	}
}
