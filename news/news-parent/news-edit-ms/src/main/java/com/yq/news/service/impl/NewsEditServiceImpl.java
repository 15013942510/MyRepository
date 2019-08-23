package com.yq.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yq.news.mapper.NewsMapper;
import com.yq.news.pojo.News;
import com.yq.news.service.NewsEditService;
@Service
public class NewsEditServiceImpl implements NewsEditService {

	@Autowired
	private NewsMapper newsMapper;
	
	@Override
	public void addNews(News news) {
		
		newsMapper.insert(news);
	}

	@Override
	public void updateNews(News news) {
		
		newsMapper.updateByPrimaryKeySelective(news);
	}

	@Override
	public void delNews(Integer[] ids) {
		
		for (Integer id : ids) {
			newsMapper.deleteByPrimaryKey(id);
		}
	}

}
