package com.hua.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hua.dao.IUserMapper;
import com.hua.domain.User;
import com.hua.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private IUserMapper userMapper;

	public User isLogin(String userName, String passWord) {
		return userMapper.isLogin(userName, passWord);
	}

	public List<User> getUserListByLimit(Integer pageIndex, Integer pageSize) {
		return userMapper.getUserListByLimit(pageIndex,pageSize);

	}

	@Override
	public int savaUser(User user) {
		
		return userMapper.insert(user);
	}

	@Override
	public int delete_user_statusByUserId(String userId, int status) {
		
		return userMapper.delete_user_statusByUserId(Integer.parseInt(userId),status);
	}

	public int deleteUserById(String userId) {
		
		return userMapper.deleteByPrimaryKey(Integer.parseInt(userId));
	}

	@Override
	public User getUserById(String userId) {
		
		return userMapper.selectByPrimaryKey(Integer.parseInt(userId));
	}

	@Override
	public int updateUser(User user) {
		
		return userMapper.updateByPrimaryKey(user);
	}

}
