package com.yq.auction.service;

import com.yq.auction.pojo.AuctionUser;

public interface UserService {
    public AuctionUser login(String username, String password);

	public void addUser(AuctionUser user);

	//假设账号是唯一约束
	public AuctionUser findUserByName(String username);
}
