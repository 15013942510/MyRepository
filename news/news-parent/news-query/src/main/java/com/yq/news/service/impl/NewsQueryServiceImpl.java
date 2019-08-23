package com.yq.news.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yq.news.mapper.CategoryMapper;
import com.yq.news.mapper.NewsMapper;
import com.yq.news.pojo.Category;
import com.yq.news.pojo.News;
import com.yq.news.pojo.PageResult;
import com.yq.news.service.NewsQueryService;

@Service
public class NewsQueryServiceImpl implements NewsQueryService {

	@Autowired
	private NewsMapper newsMapper;
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Override
	public List<News> findNews() {
		
		return newsMapper.selectByExample(null);
	}

	@Override
	public News findNewsById(int id) {
		
		return this.newsMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Category> findAllCategory() {
		
		return this.categoryMapper.selectByExample(null);
	}

	@Override
	public PageResult findByPage(int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		Page<News> page = (Page<News>) this.newsMapper.selectByExample(null);
		
		//返回自定义的封装结果
		PageResult pageResult = new PageResult();
		pageResult.setTotal(page.getTotal());
		pageResult.setRows(page.getResult());
		
		//总记录数
		List<News> list = this.newsMapper.selectByExample(null);
		
		//查询的区间记录数
		PageInfo pageInfo = new PageInfo<>(list);
		
		return pageResult;
	}

}
