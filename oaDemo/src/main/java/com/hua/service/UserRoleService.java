package com.hua.service;

import java.util.List;

import com.hua.domain.UserRole;

public interface UserRoleService {
	List<UserRole> findRoleByUserId(String userId);

	public int deleteRoleByUserId(String userId);

	public int addUserRole(UserRole userRole);
}
