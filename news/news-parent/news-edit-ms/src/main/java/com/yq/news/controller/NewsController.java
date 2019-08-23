package com.yq.news.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yq.news.pojo.News;
import com.yq.news.pojo.Result;
import com.yq.news.service.NewsEditService;

@RestController
public class NewsController {

	@Autowired
	private NewsEditService newsEditService;
	
	@RequestMapping(value="/addNews",method=RequestMethod.POST)
	public Result addNews(@RequestBody News news) {
		
		Result result = new Result();
		
		try {
			
			news.setCreatedate(new Date());
			newsEditService.addNews(news);
			result.setSuccess(true);
			
		} catch (Exception e) {
			
			result.setSuccess(false);
			e.printStackTrace();
			
		}
		
		return result;
	}
	
	@RequestMapping(value = "/updateNews",method = RequestMethod.POST)
	public Result updateNews(@RequestBody News news) {
		
		Result result = new Result();
		
		try {
			news.setCreatedate(new Date());
			newsEditService.updateNews(news);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/delNews",method = RequestMethod.GET)
	public Result delNews(Integer[] ids) {
		
		Result result = new Result();
		
		try {
			newsEditService.delNews(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
		}
		
		return result;
	}
	
}
