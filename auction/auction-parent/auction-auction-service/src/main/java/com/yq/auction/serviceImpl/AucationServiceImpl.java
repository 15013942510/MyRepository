package com.yq.auction.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yq.auction.dao.AuctionCustomMapper;
import com.yq.auction.dao.AuctionMapper;
import com.yq.auction.dao.AuctionRecordMapper;
import com.yq.auction.pojo.Auction;
import com.yq.auction.pojo.AuctionCustom;
import com.yq.auction.pojo.AuctionExample;
import com.yq.auction.pojo.AuctionRecord;
import com.yq.auction.pojo.Results;
import com.yq.auction.service.AuctionService;
import com.yq.auction.utils.AuctionPriceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AucationServiceImpl implements AuctionService {

    @Autowired
    private AuctionMapper auctionMapper;
    
    @Autowired
    private AuctionCustomMapper auctionCustomMapper;
    
    @Autowired
    private AuctionRecordMapper auctionRecordMapper;

    @Override
    public List<Auction> findAuctions(Auction auction) {
        AuctionExample example = new AuctionExample();

        AuctionExample.Criteria criteria = example.createCriteria();

        if (auction != null){
            //1、商品名称模糊查询
            if (auction.getAuctionname() != null && !"".equals(auction.getAuctionname())){
                criteria.andAuctionnameLike("%" + auction.getAuctionname() + "%");
            }
            //2、商品描述模糊查询
            if (auction.getAuctiondesc() != null && !"".equals(auction.getAuctiondesc())){
                criteria.andAuctiondescLike("%" + auction.getAuctiondesc() + "%");
            }
            //3、起拍时间：大于
            if (auction.getAuctionstarttime() != null){
                criteria.andAuctionstarttimeGreaterThan(auction.getAuctionstarttime());
            }
            //4、结束时间：小于
            if (auction.getAuctionendtime() != null){
                criteria.andAuctionendtimeLessThan(auction.getAuctionendtime());
            }
            //5、起拍价：大于
            if (auction.getAuctionstartprice() != null) {
				criteria.andAuctionstartpriceGreaterThan(auction.getAuctionstartprice());
			}
        }
        
      //按照起拍时间降序排列
        example.setOrderByClause("auctionstarttime desc");
        List<Auction> list = auctionMapper.selectByExample(example);
        
        return list;
    }

	@Override
	public void addAuction(Auction auction) {
		
		auctionMapper.insert(auction);
	}

	@Override
	public Auction findAuctionAndRecordListById(int auctionid) {
		
		return auctionCustomMapper.findAuctionAndRecordListById(auctionid);
	}
	
	/*
	 * 竞拍时间不能过期
     * 若是首次竞价，价格不能低于起拍价
     * 若是后续竞价，价格必须高于所有价格的最高价
     */
	@Override
	public void addAuctionRecord(AuctionRecord record) throws Exception {
		
		//1、查询出商品的详情数据
		Auction auction = auctionCustomMapper.findAuctionAndRecordListById(record.getAuctionid());
		//auctionRecordMapper.selectByPrimaryKey(record.getAuctionid());
		
		//判断拍卖时间
		if (auction.getAuctionendtime().after(new Date()) == false) {	//过期
			
			//抛异常
			throw new AuctionPriceException("拍卖时间已经过期！");
		}else {
			//判断价格
			if (auction.getAuctionrecordList() != null && auction.getAuctionrecordList().size() > 0) {	//后续竞拍
				
				//竞拍记录的最高价
				AuctionRecord maxRecord = auction.getAuctionrecordList().get(0);
				if (record.getAuctionprice() < maxRecord.getAuctionprice()) {
					throw new AuctionPriceException("价格必须高于所有价格的最高价！");
				}
			}else {	//首次竞拍
				
				if (record.getAuctionprice() < auction.getAuctionstartprice()) {
					throw new AuctionPriceException("价格不能低于起拍价！");
				}
			}
		}
		auctionRecordMapper.insert(record);
	}

	@Override
	public List<AuctionCustom> findAuctionEndtime() {
		
		return auctionCustomMapper.findAuctionEndtime();
	}

	@Override
	public List<Auction> findAuctionNoendtime() {
		
		return auctionCustomMapper.findAuctionNoendtime();
	}

	@Override
	public Results findAuctions(int pageNum, int pageSize, Auction auction) {
		
		Results results = new Results();
		
		PageHelper.startPage(pageNum, pageSize);
		
		AuctionExample example = new AuctionExample();

        AuctionExample.Criteria criteria = example.createCriteria();

        if (auction != null){
            //1、商品名称模糊查询
            if (auction.getAuctionname() != null && !"".equals(auction.getAuctionname())){
                criteria.andAuctionnameLike("%" + auction.getAuctionname() + "%");
            }
            //2、商品描述模糊查询
            if (auction.getAuctiondesc() != null && !"".equals(auction.getAuctiondesc())){
                criteria.andAuctiondescLike("%" + auction.getAuctiondesc() + "%");
            }
            //3、起拍时间：大于
            if (auction.getAuctionstarttime() != null){
                criteria.andAuctionstarttimeGreaterThan(auction.getAuctionstarttime());
            }
            //4、结束时间：小于
            if (auction.getAuctionendtime() != null){
                criteria.andAuctionendtimeLessThan(auction.getAuctionendtime());
            }
            //5、起拍价：大于
            if (auction.getAuctionstartprice() != null) {
				criteria.andAuctionstartpriceGreaterThan(auction.getAuctionstartprice());
			}
        }
        
      //按照起拍时间降序排列
        example.setOrderByClause("auctionstarttime desc");
        List<Auction> list = auctionMapper.selectByExample(example);
        
        PageInfo pageInfo = new PageInfo<>(list);
        
        results.setDatas(list);
        results.setPageInfo(pageInfo);
        
        return results;
	}
}
