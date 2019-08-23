package com.yq.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Value("${server.port}")
	private String serverPort;
	
	@RequestMapping(value = "/findAllNames",method=RequestMethod.GET)
	public List<String> findAllNames(){
		
		List<String> names = new ArrayList<>();
		
		names.add("测试01");
		names.add("测试02");
		names.add("测试03");
		names.add("测试04");
		names.add("serverport：" + serverPort);
		
		return names;
	}
}
