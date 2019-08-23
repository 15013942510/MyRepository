package com.hua.service;

import java.util.List;

import com.hua.domain.User;

public interface UserService {

	User isLogin(String userName, String passWord);

	List<User> getUserListByLimit(Integer pageIndex, Integer pageSize);

	public int savaUser(User user);

	int delete_user_statusByUserId(String userId, int status);

	int deleteUserById(String userId);

	User getUserById(String userId);

	int updateUser(User user);

}
