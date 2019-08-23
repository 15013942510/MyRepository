package com.web.shopping.service;

import java.util.List;
import java.util.Map;

import com.web.shopping.entity.PageResult;
import com.web.shopping.pojo.TbBrand;

public interface BrandService {

	List<TbBrand> findAll();

	PageResult findPage(int pageNum, int pageSize);

	void add(TbBrand brand);

	void delete(Long[] ids);

	TbBrand findId(Long id);

	void update(TbBrand brand);

	List<Map> selectBrandList();

	PageResult search(TbBrand brand, int pageNum, int pageSize);

}
