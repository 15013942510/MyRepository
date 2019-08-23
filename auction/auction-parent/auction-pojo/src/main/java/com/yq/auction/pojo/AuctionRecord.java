package com.yq.auction.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AuctionRecord implements Serializable {
    private Integer id;

    private Integer userid;

    private Integer auctionid;

    private Date auctiontime;

    private Double auctionprice;
    
    private AuctionUser user;	//一方用户的记录

    public AuctionUser getUser() {
		return user;
	}

	public void setUser(AuctionUser user) {
		this.user = user;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getAuctionid() {
        return auctionid;
    }

    public void setAuctionid(Integer auctionid) {
        this.auctionid = auctionid;
    }

    public Date getAuctiontime() {
        return auctiontime;
    }

    public void setAuctiontime(Date auctiontime) {
        this.auctiontime = auctiontime;
    }

    public Double getAuctionprice() {
        return auctionprice;
    }

    public void setAuctionprice(Double auctionprice) {
        this.auctionprice = auctionprice;
    }
}