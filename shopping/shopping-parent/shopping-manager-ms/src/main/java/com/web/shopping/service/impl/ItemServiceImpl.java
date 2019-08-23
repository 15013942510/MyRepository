package com.web.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.shopping.entity.PageResult;
import com.web.shopping.mapper.TbItemCatMapper;
import com.web.shopping.pojo.TbItemCat;
import com.web.shopping.pojo.TbItemCatExample;
import com.web.shopping.service.ItemCatService;
@Service
public class ItemServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public List<TbItemCat> findAll() {
		
		return itemCatMapper.selectByExample(null);
	}

	@Override
	public void add(TbItemCat itemCat) {
		
		itemCatMapper.insert(itemCat);
	}

	@Override
	public void update(TbItemCat itemCat) {
		
		itemCatMapper.updateByPrimaryKey(itemCat);
	}

	@Override
	public TbItemCat findOne(Long id) {
		
		return itemCatMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		Page<TbItemCat> page = (Page<TbItemCat>) itemCatMapper.selectByExample(null);
		
		return new PageResult(page.getResult(), page.getTotal());
	}

	@Override
	public void delete(Long[] ids) {
		
		for (Long id : ids) {
			
			itemCatMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult search(TbItemCat itemCat, int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		TbItemCatExample example = new TbItemCatExample();
		TbItemCatExample.Criteria criteria = example.createCriteria();
		
		if (itemCat != null) {
			
			if (itemCat.getName() != null && itemCat.getName().length() > 0) {
				
				criteria.andNameLike("%" + itemCat.getName() + "%");
			}
		}
		
		Page<TbItemCat> page = (Page<TbItemCat>) itemCatMapper.selectByExample(example);
		
		return new PageResult(page.getResult(), page.getTotal());
	}

	@Override
	public List<TbItemCat> findByParentId(Long parentId) {
		
		TbItemCatExample example = new TbItemCatExample();
		TbItemCatExample.Criteria criteria = example.createCriteria();
		
		//设置条件：
		criteria.andParentIdEqualTo(parentId);
		
		//条件查询
		return itemCatMapper.selectByExample(example);
	}

}
