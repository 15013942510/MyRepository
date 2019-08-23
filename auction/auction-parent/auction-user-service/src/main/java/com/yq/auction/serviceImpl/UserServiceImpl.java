package com.yq.auction.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yq.auction.dao.AuctionUserMapper;
import com.yq.auction.pojo.AuctionUser;
import com.yq.auction.pojo.AuctionUserExample;
import com.yq.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuctionUserMapper auctionUserMapper;

    @Override
    public AuctionUser login(String username, String password) {
        AuctionUserExample auctionUserExample = new AuctionUserExample();
        AuctionUserExample.Criteria criteria = auctionUserExample.createCriteria();

        criteria.andUsernameEqualTo(username);
        criteria.andUserpasswordEqualTo(password);

        List<AuctionUser> list = auctionUserMapper.selectByExample(auctionUserExample);

        if (list != null && list.size() > 0){

            return list.get(0);

        }
        return null;
    }

	@Override
	public void addUser(AuctionUser user) {
		
		auctionUserMapper.insert(user);
	}

	@Override
	public AuctionUser findUserByName(String username) {
		
		AuctionUserExample example = new AuctionUserExample();
		
		AuctionUserExample.Criteria criteria = example.createCriteria();
		
		criteria.andUsernameEqualTo(username);
		
		List<AuctionUser> list = auctionUserMapper.selectByExample(example);
		
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
}
