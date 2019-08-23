package com.hua.service;

import java.util.List;

import com.hua.domain.Function;

public interface FunctionService {
	public List<Function> getAllFunctionList();

	public int addFunction(Function function);

	public Function findFunctionByName(String funcName);

	public List<Function> findFuncListByFunId(String parentId);

	public int deleteFunctionByFunId(String funId);

	public Function findFunctionByFunId(String funcId);

	public int updateFunction(Function function);
}
