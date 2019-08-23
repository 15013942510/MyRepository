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
import com.web.shopping.pojo.TbContent;
import com.web.shopping.service.ContentService;

@RestController
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	/**
	 * 返回所有数据列表
	 * @return
	 */
	@RequestMapping(value = "/findAllContent",method = RequestMethod.GET)
	public List<TbContent> findAll(){
		
		return contentService.findAll();
	}
	
	/**
	 * 条件查询 + 分页
	 * @param content
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/searchContent",method = RequestMethod.POST)
	public PageResult search(@RequestBody TbContent content,int pageNum,int pageSize) {
		
		return contentService.search(content,pageNum,pageSize);
	}
	
	/**
	 * 增加
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/addContent",method = RequestMethod.POST)
	public Result add(@RequestBody TbContent content) {
		
		try {
			contentService.add(content);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/updateContent",method = RequestMethod.POST)
	public Result update(@RequestBody TbContent content) {
		
		try {
			contentService.update(content);
			return new Result(false, "修改成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}
	
	/**
	 * 根据id获取实体——查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findOneContent/{id}",method = RequestMethod.GET)
	public TbContent findOne(@PathVariable Long id) {
	
		return contentService.findOne(id);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteContent",method = RequestMethod.GET)
	public Result delete(Long[] ids) {
		
		try {
			contentService.delete(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
	@RequestMapping(value = "/findByCategoryId",method = RequestMethod.GET)
	public List<TbContent> findByCategoryId(Long categoryId){
		
		return contentService.findByCategoryId(categoryId);
	}
}
