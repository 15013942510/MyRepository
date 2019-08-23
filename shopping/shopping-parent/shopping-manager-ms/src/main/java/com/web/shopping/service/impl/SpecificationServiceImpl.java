package com.web.shopping.service.impl;

import java.util.List;
import java.util.Map;

import javax.swing.JPopupMenu.Separator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.shopping.entity.PageResult;
import com.web.shopping.mapper.TbSpecificationMapper;
import com.web.shopping.mapper.TbSpecificationOptionMapper;
import com.web.shopping.pojo.TbSpecification;
import com.web.shopping.pojo.TbSpecificationExample;
import com.web.shopping.pojo.TbSpecificationOption;
import com.web.shopping.pojo.TbSpecificationOptionExample;
import com.web.shopping.pojogroup.Specification;
import com.web.shopping.service.SpecificationService;

@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	
	//查询全部
	@Override
	public List<TbSpecification> findAll() {
		
		return specificationMapper.selectByExample(null);
	}

	//按分页查询
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(null);
		
		//返回自定义封装结果
		PageResult pageResult = new PageResult();
		pageResult.setTotal(page.getTotal());
		pageResult.setRows(page.getResult());
		
		List<TbSpecification> specifications = specificationMapper.selectByExample(null);
		
		//查询的区间记录数
		PageInfo pageInfo = new PageInfo<>();
		
		return pageResult;
	}

	//增加
	@Override
	public void add(Specification specification) {
		
		//保存规格
		specificationMapper.insert(specification.getSpecification());
		
		//保存规格选项
		for (TbSpecificationOption specificationOption : specification.getSpecificationOptionsList()) {
			
			//设置规格的ID
			specificationOption.setSpecId(specification.getSpecification().getId());
			
			specificationOptionMapper.insert(specificationOption);
		}
	}

	//批量删除
	@Override
	public void delete(Long[] ids) {
		
		for (Long id : ids) {
			//删除规格
			specificationMapper.deleteByPrimaryKey(id);
			
			//删除规格选项
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(id);
			specificationOptionMapper.deleteByExample(example);
		}
	}

	//根据 id 获取实体
	@Override
	public Specification findId(Long id) {
		
		Specification specification = new Specification();
		
		//根据规格ID查询规格对象
		TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
		specification.setSpecification(tbSpecification);
		
		//根据规格的ID查询规格选项
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(id);
		List<TbSpecificationOption> list = specificationOptionMapper.selectByExample(example);
		specification.setSpecificationOptionsList(list);
		
		return specification;
	}

	//修改
	@Override
	public void update(Specification specification) {
		
		//修改规格
		specificationMapper.updateByPrimaryKey(specification.getSpecification());
		
		//先删除规格选项，再添加规格选项
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(specification.getSpecification().getId());
		specificationOptionMapper.deleteByExample(example);
		
		//保存规格选项
		for (TbSpecificationOption specificationOption : specification.getSpecificationOptionsList()) {
			
			//设置规格的ID
			specificationOption.setSpecId(specification.getSpecification().getId());
			System.out.println(specificationOption.getSpecId());
			
			specificationOptionMapper.insert(specificationOption);
		}
	}

	//搜索
	@Override
	public PageResult findSpecName(int pageNum,int pageSize,TbSpecification specification) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example = new TbSpecificationExample();
		TbSpecificationExample.Criteria criteria = example.createCriteria();
		
		if (specification != null) {
			
			if (specification.getSpecName() != null && specification.getSpecName().length() > 0) {
				
				criteria.andSpecNameLike("%" + specification.getSpecName() + "%");
			}
		}
		
		Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(example);
		
		return new PageResult(page.getResult(),page.getTotal());
	}

	@Override
	public List<Map> selectOptionList() {
		
		return specificationMapper.selectOptionList();
	}

}
