package com.yq.auction.pojo;

import java.io.Serializable;

/*
 * 扩展商品的pojo类
 */
public class AuctionCustom extends Auction implements Serializable {
	
	private String auctionprice;
	
	private String username;

	public String getAuctionprice() {
		return auctionprice;
	}

	public void setAuctionprice(String auctionprice) {
		this.auctionprice = auctionprice;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
