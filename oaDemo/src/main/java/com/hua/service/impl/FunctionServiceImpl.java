package com.hua.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hua.dao.IFunctionMapper;
import com.hua.domain.Function;
import com.hua.service.FunctionService;

@Service
public class FunctionServiceImpl implements FunctionService {
	
	@Autowired
	private IFunctionMapper functionMapper;

	@Override
	public List<Function> getAllFunctionList() {
		
		return functionMapper.selectAll();
	}

	@Override
	public int addFunction(Function function) {
		
		return functionMapper.insert(function);
	}

	@Override
	public Function findFunctionByName(String funcName) {
		
		return functionMapper.findFunctionByName(funcName);
	}

	@Override
	public List<Function> findFuncListByFunId(String parentId) {
		
		return functionMapper.findFuncListByFunId(parentId);
	}

	@Override
	public int deleteFunctionByFunId(String funId) {
		
		return functionMapper.deleteByPrimaryKey(Integer.parseInt(funId));
	}

	@Override
	public Function findFunctionByFunId(String funcId) {
		
		return functionMapper.selectByPrimaryKey(Integer.parseInt(funcId));
	}

	@Override
	public int updateFunction(Function function) {
		
		return functionMapper.updateByPrimaryKey(function);
	}
}
