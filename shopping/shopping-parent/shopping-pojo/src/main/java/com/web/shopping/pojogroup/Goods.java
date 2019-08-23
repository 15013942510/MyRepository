package com.web.shopping.pojogroup;

import java.io.Serializable;
import java.util.List;

import com.web.shopping.pojo.TbGoods;
import com.web.shopping.pojo.TbGoodsDesc;
import com.web.shopping.pojo.TbItem;

public class Goods implements Serializable {

	private TbGoods goods;	//商品信息
	
	private TbGoodsDesc goodsDesc;	//商品扩展信息
	
	private List<TbItem> itemsList;	//SKU的列表信息

	public TbGoods getGoods() {
		return goods;
	}

	public void setGoods(TbGoods goods) {
		this.goods = goods;
	}

	public TbGoodsDesc getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(TbGoodsDesc goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public List<TbItem> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<TbItem> itemsList) {
		this.itemsList = itemsList;
	}
	
	
}
