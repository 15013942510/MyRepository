package com.yq.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class UserRestController {

	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/loadAllNames")
	@HystrixCommand(fallbackMethod="fallbackLoadAllNames")
	public List<String> loadAllNames(){
		
		String url = "http://service-provider/findAllNames";
		
		List list = restTemplate.getForObject(url, List.class);
		
		return list;
	}
	
	public List<String> fallbackLoadAllNames(){
		
		List<String> list = new ArrayList<>();
		
		list.add("访问中断！");
		
		return list;
	}
	
}
