package com.yq.auction.dao;

import java.util.List;

import com.yq.auction.pojo.Auction;
import com.yq.auction.pojo.AuctionCustom;

public interface AuctionCustomMapper {
	
	public Auction findAuctionAndRecordListById(int auctionid);

	public List<AuctionCustom> findAuctionEndtime();

	public List<Auction> findAuctionNoendtime();
}
