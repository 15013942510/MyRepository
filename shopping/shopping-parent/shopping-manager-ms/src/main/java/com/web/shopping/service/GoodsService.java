package com.web.shopping.service;

import java.util.List;

import com.web.shopping.entity.PageResult;
import com.web.shopping.pojo.TbGoods;
import com.web.shopping.pojo.TbItem;
import com.web.shopping.pojogroup.Goods;

public interface GoodsService {

	List<TbGoods> findAll();

	PageResult findPage(int pageNum, int pageSize);

	void add(Goods goods);

	Goods findOne(Long id);

	void update(Goods goods);

	void delete(Long[] ids);

	PageResult search(TbGoods goods, int pageNum, int pageSize);

	void updateStatus(Long[] ids, String status);

	List<TbItem> findItemListByGoodsIdListAndStatus(Long[] ids, String status);
}
