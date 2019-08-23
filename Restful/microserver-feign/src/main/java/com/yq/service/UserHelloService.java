package com.yq.service;

import org.springframework.cloud.openfeign.FeignClient;

import com.yq.controller.HelloService;
@FeignClient("service-provider")
public interface UserHelloService extends HelloService {

}
