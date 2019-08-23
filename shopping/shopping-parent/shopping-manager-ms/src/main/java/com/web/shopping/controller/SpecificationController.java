package com.web.shopping.controller;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopping.entity.PageResult;
import com.web.shopping.entity.Result;
import com.web.shopping.pojo.TbSpecification;
import com.web.shopping.pojogroup.Specification;
import com.web.shopping.service.SpecificationService;

@RestController
public class SpecificationController {
	
	@Autowired
	private SpecificationService specificationService;
	
	/*
	 * 返回所有列表信息
	 */
	@RequestMapping(value = "/findAllSpecifications",method = RequestMethod.GET)
	public List<TbSpecification> findAllSpecifications(){
		
		List<TbSpecification> list = specificationService.findAll();
		
		return list;
	}
	
	/*
	 * 返回所有列表信息
	 */
	@RequestMapping(value = "/findSpecificationPage",method = RequestMethod.GET)
	public PageResult findPage(int pageNum,int pageSize) {
		
		return specificationService.findPage(pageNum,pageSize);
	}
	
	/*
	 * 添加
	 */
	@RequestMapping(value = "/addSpecification",method = RequestMethod.POST)
	public Result addSpecification(@RequestBody Specification specification) {
		System.out.println(specification.getSpecification().getSpecName());
		System.out.println(specification.getSpecificationOptionsList().size());
		
		Result result = new Result();
		try {
			specificationService.add(specification);
			System.out.println("ceshi");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
		}
		System.out.println(result.isSuccess());
		return result;
	}
	
	/*
	 * 批量删除
	 */
	@RequestMapping(value = "/deleteSpecification",method = RequestMethod.GET)
	public Result deleteSpecification(Long[] ids) {
		
		try {
			specificationService.delete(ids);
			return new Result(true, "删除规格信息成功！");
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return new Result(false, "删除规格信息成功！");
		}
	}
	
	/*
	 * 根据 id 获取实体
	 */
	@RequestMapping(value = "/findSpecification/{id}",method = RequestMethod.GET)
	public Specification findOne(@PathVariable Long id) {
		
		return specificationService.findId(id);
	}
	
	/*
	 * 修改
	 */
	@RequestMapping(value = "/updateSpecification",method = RequestMethod.POST)
	public Result updateSpecification(@RequestBody Specification specification) {
		Result result = new Result();
		try {
			specificationService.update(specification);
			result.setSuccess(true);
			
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			System.out.println(result.isSuccess());
			
		}
		return result;
	}
	
	@RequestMapping(value = "/searchSpecification",method = RequestMethod.POST)
	public PageResult search(int pageNum,int pageSize,@RequestBody TbSpecification specification){
		
		return specificationService.findSpecName(pageNum, pageSize,specification);
	}
	
	@RequestMapping(value = "/selectOptionList",method = RequestMethod.GET)
	public List<Map> selectOptionList(){
		
		return specificationService.selectOptionList();
	}

}
