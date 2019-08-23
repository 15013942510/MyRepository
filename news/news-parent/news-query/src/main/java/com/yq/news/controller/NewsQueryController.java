package com.yq.news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yq.news.pojo.Category;
import com.yq.news.pojo.News;
import com.yq.news.pojo.PageResult;
import com.yq.news.service.NewsQueryService;

@RestController
public class NewsQueryController {

	@Autowired
	private NewsQueryService newsQueryService;
	
	@RequestMapping("/findAll")
//	@CrossOrigin(origins="*")	结局ajax同源性问题
	public List<News> findAll(){
		
		return newsQueryService.findNews();
	}
	
	@RequestMapping("/findAllCategory")
	public List<Category> findAllCategory(){
		
		return newsQueryService.findAllCategory();
	}
	
	@RequestMapping("/findOne/{id}")
	public News findOne(@PathVariable int id) {
		
		return newsQueryService.findNewsById(id);
	}
	
	@RequestMapping("/findPage")
	public PageResult findPage(int pageNum,int pageSize) {
		
		PageResult pageResult = newsQueryService.findByPage(pageNum,pageSize);
		
		return pageResult;
	}
	
}
