package com.hua.service;

import java.util.List;

import com.hua.domain.RoleFunction;

public interface RoleFunctionSerive {

	public List<RoleFunction> findFunctionByRoleId(String roleId);

	public int deleteFunctionByRoleId(String roleId);

	public int addFunction(RoleFunction roleFunction);

	public List<RoleFunction> findRoleListByFuncId(String funcId);

}
