package com.web.shopping.controller;

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
import com.web.shopping.pojo.TbTypeTemplate;
import com.web.shopping.service.TypeTemplateService;

@RestController
public class TypeTemplateController {

	@Autowired
	private TypeTemplateService typeTemplateService;
	
	//返回所有列表
	@RequestMapping(value = "/findAllTypeTemplate",method = RequestMethod.GET)
	public List<TbTypeTemplate> findAll(){
		
		return typeTemplateService.findAll();
	}
	
	//分页返回所有列表
	@RequestMapping(value = "/findTypeTemplatePage",method = RequestMethod.GET)
	public PageResult findPage(int pageNum,int pageSize) {
		
		return typeTemplateService.findPage(pageNum,pageSize);
	}
	
	//增加
	@RequestMapping(value = "/addTypeTemplate",method = RequestMethod.POST)
	public Result add(@RequestBody TbTypeTemplate typeTemplate) {
		
		try {
			
			typeTemplateService.add(typeTemplate);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	//修改
	@RequestMapping(value = "/updateTypeTemplate",method = RequestMethod.POST)
	public Result update(@RequestBody TbTypeTemplate typeTemplate) {
		
		try {
			typeTemplateService.update(typeTemplate);
			return new Result(true, "修改失败");
		} catch (Exception e) {
			
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}
	
	//根据id获取实体
	@RequestMapping(value = "/findOneTypeTemplate/{id}",method = RequestMethod.GET)
	public TbTypeTemplate findOne(@PathVariable Long id) {
		
		return typeTemplateService.findOne(id);
	}
	
	//删除
	@RequestMapping(value = "deleteTypeTemplate",method = RequestMethod.GET)
	public Result delete(Long[] ids) {
		
		try {
			
			typeTemplateService.delete(ids);
			return new Result(true, "删除成功");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
		
	}
	
	//查询 + 分页
	@RequestMapping(value = "/searchTypeTemplate",method = RequestMethod.POST)
	public PageResult search(@RequestBody TbTypeTemplate typeTemplate,int pageNum,int pageSize) {
		
		return typeTemplateService.search(typeTemplate,pageNum, pageSize);
	}
	
	@RequestMapping(value = "/findTypeTemplateList/{id}",method = RequestMethod.GET)
	public List<Map> findTypeTemplateList(@PathVariable Long id){
		
		return typeTemplateService.findTypeTemplateList(id);
	}
}
