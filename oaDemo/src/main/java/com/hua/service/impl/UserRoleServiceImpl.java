package com.hua.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hua.dao.IUserRoleMapper;
import com.hua.domain.UserRole;
import com.hua.service.UserRoleService;
@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private IUserRoleMapper userRoleMapper;
	@Override
	public List<UserRole> findRoleByUserId(String userId) {
		
		return userRoleMapper.findRoleByUserId(userId);
	}
	
	@Override
	public int deleteRoleByUserId(String userId) {
		
		return userRoleMapper.deleteRoleByUserId(userId);
	}

	@Override
	public int addUserRole(UserRole userRole) {
		
		return userRoleMapper.insert(userRole);
	}

}
