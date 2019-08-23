package com.hua.service;

import java.util.List;

import com.hua.domain.Role;

public interface RoleService {
	List<Role> findAllRoleList();

	List<Role> getAllRolesByLimit(Integer pageIndex,Integer pageSize);

	int savaRole(Role role);

	int deleteRole(String roleId);

	Role getRoleId(String roleId);

	int updateRole(Role role);
}
