package com.web.shopping.service;

import java.util.List;
import java.util.Map;

import com.web.shopping.entity.PageResult;
import com.web.shopping.pojo.TbSpecification;
import com.web.shopping.pojogroup.Specification;

public interface SpecificationService {

	List<TbSpecification> findAll();

	PageResult findPage(int pageNum, int pageSize);

	void add(Specification specification);

	void delete(Long[] ids);

	Specification findId(Long id);

	void update(Specification specification);

	PageResult findSpecName(int pageSize,int pageNum,TbSpecification specification);

	List<Map> selectOptionList();

}
