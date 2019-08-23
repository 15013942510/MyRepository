package com.web.shopping.service;

import java.util.List;
import java.util.Map;

import com.web.shopping.entity.PageResult;
import com.web.shopping.pojo.TbTypeTemplate;

public interface TypeTemplateService {

	List<TbTypeTemplate> findAll();

	PageResult findPage(int pageNum, int pageSize);

	void add(TbTypeTemplate typeTemplate);

	void update(TbTypeTemplate typeTemplate);

	TbTypeTemplate findOne(Long id);

	void delete(Long[] ids);

	PageResult search(TbTypeTemplate typeTemplate, int pageNum, int pageSize);

	List<Map> findTypeTemplateList(Long id);

}
