package com.yq.news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.yq.controller.QueryService;
import com.yq.news.pojo.Category;
import com.yq.news.pojo.News;
import com.yq.news.service.NewsQueryService;

@RestController
public class QueryController implements QueryService {

	@Autowired
	private NewsQueryService newsQueryService;
	
	@Override
	public List<News> findAllNews() {
		
		return this.newsQueryService.findNews();
	}

//	@Override
//	public News findNewsById(@PathVariable int id) {
//		
//		return this.newsQueryService.findNewsById(id);
//	}

	@Override
	public List<Category> findAllCategory() {
		
		return this.newsQueryService.findAllCategory();
	}

}
