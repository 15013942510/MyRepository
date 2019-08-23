package com.yq.auction.dao;

import com.yq.auction.pojo.AuctionRecord;
import com.yq.auction.pojo.AuctionRecordExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface AuctionRecordMapper {
    int countByExample(AuctionRecordExample example);

    int deleteByExample(AuctionRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AuctionRecord record);

    int insertSelective(AuctionRecord record);

    List<AuctionRecord> selectByExample(AuctionRecordExample example);

    AuctionRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AuctionRecord record, @Param("example") AuctionRecordExample example);

    int updateByExample(@Param("record") AuctionRecord record, @Param("example") AuctionRecordExample example);

    int updateByPrimaryKeySelective(AuctionRecord record);

    int updateByPrimaryKey(AuctionRecord record);
}