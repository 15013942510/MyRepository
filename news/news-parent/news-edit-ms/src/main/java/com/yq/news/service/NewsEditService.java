package com.yq.news.service;

import com.yq.news.pojo.News;

public interface NewsEditService {

	void addNews(News news);

	void updateNews(News news);

	void delNews(Integer[] ids);

}
