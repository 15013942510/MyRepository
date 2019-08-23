package com.web.shopping.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.shopping.entity.PageResult;
import com.web.shopping.mapper.TbBrandMapper;
import com.web.shopping.pojo.TbBrand;
import com.web.shopping.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private TbBrandMapper brandMapper;
	
	@Override
	public List<TbBrand> findAll() {
		
		return brandMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(null);
		
		//返回自定义封装结果
		PageResult pageResult = new PageResult();
		pageResult.setTotal(page.getTotal());
		pageResult.setRows(page.getResult());
		
		List<TbBrand> list = brandMapper.selectByExample(null);
		
		//查询的区间记录数
		PageInfo pageInfo = new PageInfo<>(list);
		
		return pageResult;
	}

	@Override
	public void add(TbBrand brand) {
		
		brandMapper.insert(brand);
	}

	@Override
	public void delete(Long[] ids) {
		
		for (Long id : ids) {
			brandMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public TbBrand findId(Long id) {
		
		return brandMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(TbBrand brand) {
		
		brandMapper.updateByPrimaryKey(brand);
	}

	@Override
	public List<Map> selectBrandList() {
		
		return brandMapper.selectBrandList();
	}

	@Override
	public PageResult search(TbBrand brand, int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(null);
		
		return new PageResult(page.getResult(), page.getTotal());
	}

}
