package com.yq.news.service;

import java.util.List;

import com.yq.news.pojo.Category;
import com.yq.news.pojo.News;
import com.yq.news.pojo.PageResult;

public interface NewsQueryService {

	List<News> findNews();

	News findNewsById(int id);

	List<Category> findAllCategory();

	PageResult findByPage(int pageNum, int pageSize);

}
