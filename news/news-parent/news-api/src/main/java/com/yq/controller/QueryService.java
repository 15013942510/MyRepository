package com.yq.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yq.news.pojo.Category;
import com.yq.news.pojo.News;

public interface QueryService {
	
	@RequestMapping(value = "findAllNews",method = RequestMethod.GET)
	public List<News> findAllNews();
	
//	@RequestMapping(value = "findNewsById/{id}",method = RequestMethod.GET)
//	public News findNewsById(@PathVariable int id);
	
	@RequestMapping(value ="findAllCategory",method = RequestMethod.GET)
	public List<Category> findAllCategory();
	
}
