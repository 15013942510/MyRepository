package com.web.shopping.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopping.entity.PageResult;
import com.web.shopping.entity.Result;
import com.web.shopping.pojo.TbBrand;
import com.web.shopping.service.BrandService;

@RestController
public class BrandController {

	@Autowired
	private BrandService brandService;
	
	//查询所有品牌
	@RequestMapping(value = "/findAllBrands",method = RequestMethod.GET)
	public List<TbBrand> findAllBrands(){
		List<TbBrand> list = brandService.findAll();
		System.out.println(list);
		return list;
	}
	
	@RequestMapping(value = "/findBrandPage",method = RequestMethod.GET)
	public PageResult findPage(int pageNum,int pageSize) {
		
		return brandService.findPage(pageNum,pageSize);
	}
	
	@RequestMapping(value = "/addBrand",method = RequestMethod.POST)
	public Result addBrand(@RequestBody TbBrand brand) {
		
		try {
			
			brandService.add(brand);
			return new Result(true,"添加品牌信息成功！");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return new Result(true,"添加品牌信息失败！");
			
		}
	}
	
	@RequestMapping(value = "/deleteBrand",method = RequestMethod.GET)
	public Result deleteBrand(Long[] ids) {
		
		try {
			
			brandService.delete(ids);
			return new Result(true, "删除品牌信息成功！");
		} catch (Exception e) {
			
			e.printStackTrace();
			return new Result(false, "删除品牌信息失败！");
		}
	}
	
	@RequestMapping(value = "/findBrand/{id}",method = RequestMethod.GET)
	public TbBrand findOne(@PathVariable Long id) {
		
		return brandService.findId(id);
	}
	
	@RequestMapping(value = "/updateBrand",method = RequestMethod.POST)
	public Result updateBrand(@RequestBody TbBrand brand) {
		try {
			brandService.update(brand);
			return new Result(true, "修改品牌信息成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改品牌信息失败！");
		}
	}
	
	@RequestMapping(value = "/searchBrand",method = RequestMethod.POST)
	public PageResult search(@RequestBody TbBrand brand,int pageNum,int pageSize) {
		
		return brandService.search(brand,pageNum,pageSize);
	}
	
	@RequestMapping(value = "/selectBrandList",method = RequestMethod.GET)
	public List<Map> selectBrandList(){
		
		return brandService.selectBrandList();
	}

}
