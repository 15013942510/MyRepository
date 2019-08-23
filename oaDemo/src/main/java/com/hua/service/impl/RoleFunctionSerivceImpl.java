package com.hua.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hua.dao.IRoleFunctionMapper;
import com.hua.domain.RoleFunction;
import com.hua.service.RoleFunctionSerive;

@Service
public class RoleFunctionSerivceImpl implements RoleFunctionSerive {

	@Autowired
	private IRoleFunctionMapper roleFunctionMapper;
	
	@Override
	public List<RoleFunction> findFunctionByRoleId(String roleId) {
		
		return roleFunctionMapper.findFunctionByRoleId(roleId);
	}

	@Override
	public int deleteFunctionByRoleId(String roleId) {
		
		return roleFunctionMapper.deleteFunctionByRoleId(roleId);
	}

	@Override
	public int addFunction(RoleFunction roleFunction) {
		
		return roleFunctionMapper.insert(roleFunction);
	}

	@Override
	public List<RoleFunction> findRoleListByFuncId(String funcId) {
		
		return roleFunctionMapper.findRoleListByFuncId(funcId);
	}
}
