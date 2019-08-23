package com.yq.web.service;

import org.springframework.cloud.openfeign.FeignClient;

import com.yq.controller.QueryService;
@FeignClient("news-query-ms")
public interface NewsQueryService extends QueryService {

	
}
