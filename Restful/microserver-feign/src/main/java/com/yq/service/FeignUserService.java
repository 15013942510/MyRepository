package com.yq.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service-provider",fallback=FallBackFeignUserService.class)	//具备负载均衡
public interface FeignUserService {

	@RequestMapping(value = "/findAllNames",method=RequestMethod.GET)
	public List<String> getUserList();
}
@Component
class FallBackFeignUserService implements FeignUserService{

	@Override
	public List<String> getUserList() {
		
		List<String> errors = new ArrayList<>();
		errors.add("网络中断！");
		
		return errors;
	}
}