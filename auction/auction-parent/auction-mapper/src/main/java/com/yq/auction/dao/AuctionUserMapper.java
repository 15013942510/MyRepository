package com.yq.auction.dao;

import com.yq.auction.pojo.AuctionUser;
import com.yq.auction.pojo.AuctionUserExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface AuctionUserMapper {
    int countByExample(AuctionUserExample example);

    int deleteByExample(AuctionUserExample example);

    int deleteByPrimaryKey(Integer userid);

    int insert(AuctionUser record);

    int insertSelective(AuctionUser record);

    List<AuctionUser> selectByExample(AuctionUserExample example);

    AuctionUser selectByPrimaryKey(Integer userid);

    int updateByExampleSelective(@Param("record") AuctionUser record, @Param("example") AuctionUserExample example);

    int updateByExample(@Param("record") AuctionUser record, @Param("example") AuctionUserExample example);

    int updateByPrimaryKeySelective(AuctionUser record);

    int updateByPrimaryKey(AuctionUser record);
}