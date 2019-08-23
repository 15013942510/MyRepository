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
import com.web.shopping.pojo.TbContentCategory;
import com.web.shopping.service.ContentCategoryService;

@RestController
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/**
	 *列出所有数据
	 * @return
	 */
	@RequestMapping(value = "/findAllContentCategory",method = RequestMethod.GET)
	public List<TbContentCategory> findAll(){
		
		return contentCategoryService.findAll();
	}
	
	/**
	 * 分页
	 * @return
	 */
	@RequestMapping(value = "/findPageContentCategory",method = RequestMethod.GET)
	public PageResult findPage(int pageNum,int pageSize) {
		
		return contentCategoryService.findPage(pageNum,pageSize);
	}
	
	/**
	 * 增加
	 * @return
	 */
	@RequestMapping(value = "/addContentCategory",method = RequestMethod.POST)
	public Result add(@RequestBody TbContentCategory contentCategory) {
		
		try {
			contentCategoryService.add(contentCategory);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @return
	 */
	@RequestMapping(value = "/updateContentCategory",method = RequestMethod.POST)
	public Result update(@RequestBody TbContentCategory contentCategory) {
		
		try {
			contentCategoryService.update(contentCategory);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}
	
	/**
	 * 根据id获取实体信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findOneContentCategory/{id}",method = RequestMethod.GET)
	public TbContentCategory findOne(@PathVariable Long id) {
		
		return contentCategoryService.findOne(id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteContentCategory",method = RequestMethod.GET)
	public Result delete(Long[] ids) {
		
		try {
			contentCategoryService.delete(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
	/**
	 * 条件查询 + 分页
	 * @param contentCategory
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/searchContentCategory",method = RequestMethod.POST)
	public PageResult search(@RequestBody TbContentCategory contentCategory,int pageNum,int pageSize) {
		
		return contentCategoryService.search(contentCategory,pageNum,pageSize);
	}
}
