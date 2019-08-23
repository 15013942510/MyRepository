package com.web.shopping.service;

import java.util.List;

import com.web.shopping.entity.PageResult;
import com.web.shopping.pojo.TbContentCategory;

public interface ContentCategoryService {

	List<TbContentCategory> findAll();

	PageResult findPage(int pageNum,int pageSize);

	void add(TbContentCategory contentCategory);

	void update(TbContentCategory contentCategory);

	TbContentCategory findOne(Long id);

	void delete(Long[] ids);

	PageResult search(TbContentCategory contentCategory, int pageNum, int pageSize);

}
