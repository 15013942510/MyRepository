package com.web.shopping.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.shopping.entity.PageResult;
import com.web.shopping.entity.Result;
import com.web.shopping.mapper.TbBrandMapper;
import com.web.shopping.mapper.TbGoodsDescMapper;
import com.web.shopping.mapper.TbGoodsMapper;
import com.web.shopping.mapper.TbItemCatMapper;
import com.web.shopping.mapper.TbItemMapper;
import com.web.shopping.mapper.TbSellerMapper;
import com.web.shopping.pojo.TbBrand;
import com.web.shopping.pojo.TbGoods;
import com.web.shopping.pojo.TbGoodsDesc;
import com.web.shopping.pojo.TbGoodsExample;
import com.web.shopping.pojo.TbItem;
import com.web.shopping.pojo.TbItemCat;
import com.web.shopping.pojo.TbItemExample;
import com.web.shopping.pojo.TbSeller;
import com.web.shopping.pojogroup.Goods;
import com.web.shopping.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {
	
	@Autowired
	private TbGoodsMapper goodsMapper;
	
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Autowired
	private TbBrandMapper brandMapper;
	
	@Autowired
	private TbSellerMapper sellerMapper;

	/**
	 * 查询所有数据
	 */
	@Override
	public List<TbGoods> findAll() {
		
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(null);
		
		return new PageResult(page.getResult(), page.getTotal());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Goods goods) {
		
		//设置审核的状态
		goods.getGoods().setAuditStatus("0");
		
		//插入商品信息
		goodsMapper.insert(goods.getGoods());
		
		goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
		
		//插入商品的扩展信息
		goodsDescMapper.insert(goods.getGoodsDesc());
		
		setItemList(goods);
		
	}
	
	private void setItemList(Goods goods) {
		System.out.println("是否启用规格：" + goods.getGoods().getIsEnableSpec());
		
		if ("1".equals(goods.getGoods().getIsEnableSpec())) {
			
			//启用规格
			//保存SKU列表信息
			for (TbItem item : goods.getItemsList()) {
				
				//设置SKU的数据：
				//item.setTitle(title);
				
				String title = goods.getGoods().getGoodsName();
				
				Map<String, String> map = JSON.parseObject(item.getSpec(), Map.class);
				
				for (String key : map.keySet()) {
					
					title += " " + map.get(key);
				}
				item.setTitle(title);
				
				setValue(goods, item);
			}
			
		} else {
			
			//没有启用规格
			TbItem item = new TbItem();
			
			item.setTitle(goods.getGoods().getGoodsName());
			item.setPrice(goods.getGoods().getPrice());
			item.setNum(999);
			item.setStatus("0");
			item.setIsDefault("1");
			item.setSpec("{}");
			
			setValue(goods, item);
			itemMapper.insert(item);
		}
		
	}
	
	private void setValue(Goods goods,TbItem item) {
		
		List<Map> imageList = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
		
		if (imageList.size() > 0) {
			
			item.setImage((String) imageList.get(0).get("url"));
		}
		
		//保存三级分类的id
		item.setCategoryid(goods.getGoods().getCategory3Id());
		item.setCreateTime(new Date());
		item.setUpdateTime(new Date());
		
		//设置商品ID
		item.setGoodsId(goods.getGoods().getId());
		item.setSellerId(goods.getGoods().getSellerId());
		
		TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());
		item.setCategory(itemCat.getName());
		
		TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
		item.setBrand(brand.getName());
		
		TbSeller seller = sellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());
		item.setSeller(seller.getNickName());
	}

	/**
	 * 根据id获取实体
	 */
	@Override
	public Goods findOne(Long id) {
		
		Goods goods = new Goods();
		
		//查询商品表的信息
		TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
		goods.setGoods(tbGoods);
		
		//查询商品扩展表的信息
		TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(id);
		goods.setGoodsDesc(goodsDesc);
		
		//传SKU表的信息
		TbItemExample example = new TbItemExample();
		TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(id);
		
		List<TbItem> list = itemMapper.selectByExample(example);
		goods.setItemsList(list);
		
		return goods;
	}

	/**
	 * 修改
	 */
	@Override
	public void update(Goods goods) {
		
		//修改商品细腻些
		goods.getGoods().setAuditStatus("0");
		goodsMapper.updateByPrimaryKey(goods.getGoods());
		
		//修改商品扩展信息
		goodsDescMapper.updateByPrimaryKey(goods.getGoodsDesc());
		
		//修改SKU信息
		//先删除再保存
		//删除SKU的信息
		TbItemExample example = new TbItemExample();
		TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(goods.getGoods().getId());
		itemMapper.deleteByExample(example);
		
		//保存SKU的信息
		setItemList(goods);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		
		for (Long id : ids) {
			TbGoods goods = goodsMapper.selectByPrimaryKey(id);
			goods.setIsDelete("1");
			goodsMapper.updateByPrimaryKey(goods);
		}
	}

	/**
	 * 查询+分页
	 */
	@Override
	public PageResult search(TbGoods goods, int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example = new TbGoodsExample();
		TbGoodsExample.Criteria criteria = example.createCriteria();
		
		if (goods != null) {
			
//			if (goods.getSellerId() != null && goods.getSellerId().length() > 0) {
//				
//				System.out.println("设置sellerId：" + goods.getSellerId());
//				criteria.andSellerIdEqualTo(goods.getSellerId());
//			}
			if (goods.getGoodsName() != null && goods.getGoodsName().length() > 0) {
				criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
			}
			if (goods.getAuditStatus() != null && goods.getAuditStatus().length() > 0) {
				criteria.andAuditStatusLike("%" + goods.getAuditStatus() + "%");
			}
			if (goods.getIsMarketable() != null && goods.getIsMarketable().length() > 0) {
				criteria.andIsMarketableLike("%" + goods.getIsMarketable()+ "%");
			}
			if (goods.getCaption() != null && goods.getCaption().length() > 0) {
				criteria.andCaptionLike("%" + goods.getCaption() + "%");
			}
			if (goods.getSmallPic() != null && goods.getSmallPic().length() > 0) {
				criteria.andSmallPicLike("%" + goods.getSmallPic() + "%");
			}
			if (goods.getIsEnableSpec() != null && goods.getIsEnableSpec().length() > 0) {
				criteria.andIsEnableSpecLike("%" + goods.getIsEnableSpec() + "%");
			}
			if (goods.getIsDelete() != null && goods.getIsDelete().length() > 0) {
				criteria.andIsDeleteLike("%" + goods.getIsDelete() + "%");
			}
		}
		
		Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(example);
		System.out.println("测试：" + page.getResult());
		System.out.println("测试：" + page.getTotal());
		
		return new PageResult(page.getResult(),page.getTotal());
	}

	@Override
	public void updateStatus(Long[] ids, String status) {
		
		for (Long id : ids) {
			
			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
			
			tbGoods.setAuditStatus(status);
			
			goodsMapper.updateByPrimaryKey(tbGoods);
		}
	}

	@Override
	public List<TbItem> findItemListByGoodsIdListAndStatus(Long[] ids, String status) {
		
		System.out.println("Goods Id列表" + ids);
		System.out.println("状态：" + status);
		
		TbItemExample example = new TbItemExample();
		TbItemExample.Criteria criteria = example.createCriteria();
		
		criteria.andStatusEqualTo(status);
		//指定条件：SPUID集合
		criteria.andGoodsIdIn(Arrays.asList(ids));
		
		return itemMapper.selectByExample(example);
	}
	
	

}
