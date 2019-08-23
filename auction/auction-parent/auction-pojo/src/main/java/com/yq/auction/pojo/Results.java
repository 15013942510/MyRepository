package com.yq.auction.pojo;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.PageInfo;

public class Results implements Serializable {
	
	private List datas;
	
	private PageInfo pageInfo;

	public List getDatas() {
		return datas;
	}

	public void setDatas(List datas) {
		this.datas = datas;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

}
