package com.yq.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/*
 * 提供方（controller）和消费方（Feign）的公用接口
 */
@RequestMapping("/hello")	//	窄话路径
public interface HelloService {
	
	@RequestMapping(value = "/getgetHello",method = RequestMethod.GET)
	public String getHello();
}
