package com.web.shopping.service;

import java.util.List;

import com.web.shopping.entity.PageResult;
import com.web.shopping.entity.Result;
import com.web.shopping.pojo.TbItemCat;

public interface ItemCatService {

	List<TbItemCat> findAll();

	void add(TbItemCat itemCat);

	void update(TbItemCat itemCat);

	TbItemCat findOne(Long id);

	PageResult findPage(int pageNum, int pageSize);

	void delete(Long[] ids);

	PageResult search(TbItemCat itemCat, int pageNum, int pageSize);

	List<TbItemCat> findByParentId(Long parentId);

}
