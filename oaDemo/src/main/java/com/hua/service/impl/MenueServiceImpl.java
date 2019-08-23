package com.hua.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hua.dao.IFunctionMapper;
import com.hua.domain.Function;
import com.hua.menue.Menue;
import com.hua.service.MenueService;

@Service
public class MenueServiceImpl implements MenueService {
	@Autowired
	private IFunctionMapper functionMapper;

	public List<Menue> getMenuList(String userId) {

		List<Menue> menueList = new ArrayList<Menue>();

		List<Function> functionList = functionMapper.getMenuList(userId);

		if (functionList != null && functionList.size() > 0) {
			for (Function function : functionList) {

				Menue m = new Menue();

				/*
				 * 先从function 取出所有的值 （数据给你）
				 * 
				 * 分别使用menu 的set方法设置进去进去！ (Ztree需要的)
				 * 
				 */
				m.setId(function.getFuncId());
				m.setName(function.getFuncName());
				m.setUrl(function.getFuncUrl());
				if (function.getParentId() != null) {
					m.setpId(function.getParentId());
					m.setOpen(false);
				} 
				else {
					m.setOpen(true);
				}
				m.setChecked(false); // 项目中没有单选，所以设置false

				menueList.add(m);

			}
		}
		return menueList;
	}
	
	
}
