package com.hua.service;

import java.util.List;

import com.hua.menue.Menue;

public interface MenueService {
	List<Menue> getMenuList(String userId);
}
