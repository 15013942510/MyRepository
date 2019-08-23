package com.yq.auction.service;

import com.yq.auction.pojo.Auction;
import com.yq.auction.pojo.AuctionCustom;
import com.yq.auction.pojo.AuctionRecord;
import com.yq.auction.pojo.Results;

import java.util.List;

public interface AuctionService {
	
    public List<Auction> findAuctions(Auction auction);
    
    public Results findAuctions(int pageNum,int pageSize,Auction auction);

	public void addAuctionRecord(AuctionRecord record) throws Exception;

	public void addAuction(Auction auction);
    
	public Auction findAuctionAndRecordListById(int auctionid);

	public List<AuctionCustom> findAuctionEndtime();

	public List<Auction> findAuctionNoendtime();
    
}