package com.web.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.shopping.entity.PageResult;
import com.web.shopping.mapper.TbContentMapper;
import com.web.shopping.pojo.TbContent;
import com.web.shopping.pojo.TbContentExample;
import com.web.shopping.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;
	
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 查看所有的数据列表
	 */
	@Override
	public List<TbContent> findAll() {
		
		return contentMapper.selectByExample(null);
	}

	/**
	 * 条件查询 + 分页
	 */
	@Override
	public PageResult search(TbContent content,int pageNum,int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		TbContentExample example = new TbContentExample();
		TbContentExample.Criteria criteria = example.createCriteria();
		
		if (content != null) {
			if (content.getTitle() != null && content.getTitle().length() > 0) {
				criteria.andTitleLike("%" + content.getTitle() + "%");
			}
			if (content.getUrl() != null && content.getUrl().length() > 0) {
				criteria.andUrlLike("%" + content.getUrl() + "%");
			}
			if (content.getPic() != null && content.getPic().length() > 0) {
				criteria.andPicLike("%" + content.getPic() + "%");
			}
			if (content.getStatus() != null && content.getStatus().length() > 0) {
				criteria.andStatusLike("%" + content.getStatus() + "%");
			}
		}
		
		Page<TbContent> page = (Page<TbContent>) contentMapper.selectByExample(example);
		
		return new PageResult(page.getResult(),page.getTotal());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbContent content) {
		
		contentMapper.insert(content);
		
		redisTemplate.boundHashOps("content").delete(content.getCategoryId());
	}

	/**
	 * 修改
	 */
	@Override
	public void update(TbContent content) {
		
		TbContent OldContent = contentMapper.selectByPrimaryKey(content.getId());
		
		//清除之前分类的广告缓存
		redisTemplate.boundHashOps("content").delete(OldContent.getCategoryId());
		
		contentMapper.updateByPrimaryKey(content);
		
		//清除缓存
		if (content.getCategoryId() != OldContent.getCategoryId()) {
			redisTemplate.boundHashOps("content").delete(content.getCategoryId());
		}
	}

	/**
	 * 根据id查询实体信息
	 */
	@Override
	public TbContent findOne(Long id) {
		
		return contentMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		
		for (Long id : ids) {
			TbContent  tbContent = contentMapper.selectByPrimaryKey(id);
			
			redisTemplate.boundHashOps("content").delete(tbContent.getCategoryId());
			
			contentMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public List<TbContent> findByCategoryId(Long categoryId) {
		
		//加入缓存的代码：
		List<TbContent> list = (List<TbContent>) redisTemplate.boundHashOps("content").get(categoryId);
		
		if (list == null) {
			System.out.println("查询数据库====================");
			TbContentExample example = new TbContentExample();
			TbContentExample.Criteria criteria = example.createCriteria();
			
			//有效广告：
			criteria.andStatusEqualTo("1");
			
			criteria.andCategoryIdEqualTo(categoryId);
			
			//排序
			example.setOrderByClause("sort_order");
			
			list = contentMapper.selectByExample(example);
			
			redisTemplate.boundHashOps("content").put(categoryId, list);
		}else {
			System.out.println("从缓存中获取=============");
		}
		return list;
	}

}
