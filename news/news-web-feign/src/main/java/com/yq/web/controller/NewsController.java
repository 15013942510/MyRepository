package com.yq.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yq.news.pojo.Category;
import com.yq.news.pojo.News;
import com.yq.web.service.NewsQueryService;

@Controller
public class NewsController {

	@Autowired
	private NewsQueryService newsQueryService;
	
	@RequestMapping("/loadAllNews")
	public ModelAndView loadAllNews() {
		
		List<News> newsList = newsQueryService.findAllNews();
		
		List<Category> categoryList = newsQueryService.findAllCategory();
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("newsList", newsList);
		mv.addObject("categoryList", categoryList);
		mv.setViewName("newslist");
		
		return mv;
	}
	
}
