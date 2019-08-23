package com.web.shopping.service;

import java.util.List;

import com.web.shopping.entity.PageResult;
import com.web.shopping.pojo.TbContent;

public interface ContentService {

	List<TbContent> findAll();

	PageResult search(TbContent content,int pageNum,int pageSize);

	void add(TbContent content);

	void update(TbContent content);

	TbContent findOne(Long id);

	void delete(Long[] ids);

	List<TbContent> findByCategoryId(Long categoryId);

}
