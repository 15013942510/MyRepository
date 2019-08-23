package com.yq.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.yq.news.pojo.Category;
import com.yq.news.pojo.News;

@Controller
public class NewsController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String URL_NEWSLIST = "http://localhost:8899/news-query/findAll";
	
	private static final String URL_CATEGORY = "http://localhost:8899/news-query/findAllCategory";
	
	@RequestMapping("/loadAllNews")
	public ModelAndView loadAllNews() {
		
		List<News> newsList = this.restTemplate.getForObject(URL_NEWSLIST, List.class);
		
		List<Category> categoryList = this.restTemplate.getForObject(URL_CATEGORY, List.class);
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("newsList", newsList);
		mv.addObject("categoryList", categoryList);
		
		mv.setViewName("newslist");
	
		return mv;
	}
	
}
