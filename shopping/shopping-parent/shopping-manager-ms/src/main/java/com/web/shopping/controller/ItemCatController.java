package com.web.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.shopping.entity.PageResult;
import com.web.shopping.entity.Result;
import com.web.shopping.pojo.TbItemCat;
import com.web.shopping.service.ItemCatService;

@RestController
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	//返回所有数据列表
	@RequestMapping(value = "/findAllItemCat",method = RequestMethod.GET)
	public List<TbItemCat> findAll(){
	
		return itemCatService.findAll();
	}
	
	@RequestMapping(value = "/findPageItemCat",method = RequestMethod.GET)
	public PageResult findPage(int pageNum,int PageSize) {
		
		return itemCatService.findPage(pageNum,PageSize);
	}
	
	//增加
	@RequestMapping(value = "/addItemCat",method = RequestMethod.POST)
	public Result add(@RequestBody TbItemCat itemCat) {
		
		try {
			itemCatService.add(itemCat);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(false, "添加失败");
		}
	}
	
	//修改
	@RequestMapping(value = "/updateItemCat",method = RequestMethod.POST)
	public Result update(@RequestBody TbItemCat itemCat) {
		
		try {
			itemCatService.update(itemCat);
			return new Result(true, "修改成功");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}
	
	//根据id获取实体
	@RequestMapping(value = "/findOneItemCat/{id}",method = RequestMethod.GET)
	public TbItemCat findOne (@PathVariable Long id) {
		
		return itemCatService.findOne(id);
	}
	
	//批量删除/删除
	@RequestMapping(value = "/deleteItemCat",method = RequestMethod.GET)
	public Result delete(Long[] ids) {
		
		try {
			itemCatService.delete(ids);
			return new Result(true, "删除成功");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
	//搜索/返回所有列表
	@RequestMapping(value = "/searchItemCat",method = RequestMethod.POST)
	public PageResult search(@RequestBody TbItemCat itemCat,int pageNum,int pageSize) {
		
		return itemCatService.search(itemCat,pageNum,pageSize);
	}
	
	@RequestMapping(value = "/findByParentId/{parentId}",method = RequestMethod.GET)
	public List<TbItemCat> findByParentId(@PathVariable Long parentId){
		
		return itemCatService.findByParentId(parentId);
	}
	
}
