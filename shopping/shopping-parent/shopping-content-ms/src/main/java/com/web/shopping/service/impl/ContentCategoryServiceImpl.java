package com.web.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.shopping.entity.PageResult;
import com.web.shopping.mapper.TbContentCategoryMapper;
import com.web.shopping.pojo.TbContentCategory;
import com.web.shopping.pojo.TbContentCategoryExample;
import com.web.shopping.service.ContentCategoryService;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	/**
	 * 列出所有数据
	 */
	@Override
	public List<TbContentCategory> findAll() {
		
		return contentCategoryMapper.selectByExample(null);
	}

	/**
	 * 分页
	 */
	@Override
	public PageResult findPage(int pageNum,int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		Page<TbContentCategory> page = (Page<TbContentCategory>) contentCategoryMapper.selectByExample(null);
		
		return new PageResult(page.getResult(), page.getTotal());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbContentCategory contentCategory) {
		
		contentCategoryMapper.insert(contentCategory);
	}

	/***
	 * 修改
	 */
	@Override
	public void update(TbContentCategory contentCategory) {
		
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
	}

	/**
	 * 根据id获取实体
	 */
	@Override
	public TbContentCategory findOne(Long id) {
		
		return contentCategoryMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		
		for (Long id : ids) {
			contentCategoryMapper.deleteByPrimaryKey(id);
		}
	}

	/**
	 * 条件传 + 分页
	 */
	@Override
	public PageResult search(TbContentCategory contentCategory, int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		TbContentCategoryExample example = new TbContentCategoryExample();
		TbContentCategoryExample.Criteria criteria = example.createCriteria();
		
		if (contentCategory != null) {
			if (contentCategory.getName() != null && contentCategory.getName().length() > 0) {
				criteria.andNameLike("%" + contentCategory.getName() + "%");
			}
		}
		
		Page<TbContentCategory> page = (Page<TbContentCategory>) contentCategoryMapper.selectByExample(example);
		
		return new PageResult(page.getResult(), page.getTotal());
	}

}
