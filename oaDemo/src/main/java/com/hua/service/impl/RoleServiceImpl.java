package com.hua.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hua.dao.IRoleMapper;
import com.hua.domain.Role;
import com.hua.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private IRoleMapper roleMapper;

	@Override
	public List<Role> findAllRoleList() {
		
		return roleMapper.selectAll();
	}

	@Override
	public List<Role> getAllRolesByLimit(Integer pageIndex, Integer pageSize) {
		
		return roleMapper.getAllRolesByLimit(pageIndex,pageSize);
	}

	@Override
	public int savaRole(Role role) {
		
		return roleMapper.insert(role);
	}

	@Override
	public int deleteRole(String roleId) {
		
		return roleMapper.deleteByPrimaryKey(Integer.parseInt(roleId));
	}

	@Override
	public Role getRoleId(String roleId) {
		
		return roleMapper.selectByPrimaryKey(Integer.parseInt(roleId));
	}

	@Override
	public int updateRole(Role role) {
		
		return roleMapper.updateByPrimaryKey(role);
	}
	
}
