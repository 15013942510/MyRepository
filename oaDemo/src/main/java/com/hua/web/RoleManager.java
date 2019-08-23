package com.hua.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hua.domain.Function;
import com.hua.domain.Role;
import com.hua.domain.RoleFunction;
import com.hua.service.FunctionService;
import com.hua.service.RoleFunctionSerive;
import com.hua.service.RoleService;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

//角色管理
@Controller
public class RoleManager {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private FunctionService functionService;
	
	@Autowired
	private RoleFunctionSerive roleFunctionService;
	
	@RequestMapping(value = "/roleManager.do")
	public String openRoleManagerPage() {
		return "roleManager";
	}
	
	@RequestMapping(value = "/findRoleByPage.do")
	public void findAllRoleList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		//接受datagrid传递过来的小弟 page rows
		
		String page = request.getParameter("page");
		
		String rows = request.getParameter("rows");
		
		//判断 page 和 rows 的异常情况
		if (page == null && "".equals(page)) {
			page = "1";	//第一页
		}
		if (rows == null && "".equals(rows)) {
			rows = "10";	//每次显示10条数据
		}
		
		//将datagrid传递过来的页码参数进行转换给 mysql 的 limit 使用
		
		int pageIndex = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		
		int pageSize = Integer.parseInt(rows);
		
		//调用 service 发送请求查询
		
		List<Role> rolesList = (List<Role>) roleService.getAllRolesByLimit(pageIndex,pageSize);
		
		//取出total
		
		int total = rolesList.size();
		
		if (rolesList != null && rolesList.size() > 0) {
			jsonObject.put("rows", rolesList);
			jsonObject.put("success", true);
			jsonObject.put("msg", "获取角色信息成功");
			jsonObject.put("total", total);
		}else {
			jsonObject.put("success", false);
			jsonObject.put("msg", "获取角色信息失败");
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//打开角色授权的页面
	@RequestMapping(value="/rolePerm.do")
	public String openRolePermPage(HttpServletRequest request,HttpServletResponse response) {
		
		String roleId = request.getParameter("id");
		request.setAttribute("roleId", roleId);
		
		return "rolePerm";
	}
	
	//三部曲（获取所有的权限信息）
	//把 roleId 提前准备好，为第二步和第三步做准备
	@RequestMapping(value = "/findAllFunction.do")
	public void findAllFunction (HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		List<Function> functionList = functionService.getAllFunctionList();
		
		if (functionList != null && functionList.size() > 0) {
			jsonObject.put("data", functionList);
			jsonObject.put("success", true);
			jsonObject.put("msg", "获取所有权限信息成功");
		}else {
			jsonObject.put("success", false);
			jsonObject.put("msg", "获取所有权限信息失败");
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//三部曲 第二步 根据角色 Id 获取当前角色下的所有全选
	@RequestMapping(value = "findFunctionByRoleId.do")
	public void findFunctionByRoleId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		//取出角色
		String roleId = request.getParameter("id");
		
		List<RoleFunction> allFunctionList = roleFunctionService.findFunctionByRoleId(roleId);
				
		if (allFunctionList != null && allFunctionList.size() > 0) {
			jsonObject.put("data", allFunctionList);
			jsonObject.put("msg", "当前roleId获取权限成功");
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "save_roleFunction.do")
	public void save_roleFunction(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		//获取 roleId 和 ids
		
		String roleId = request.getParameter("roleId");
		
		String parameter_ids = request.getParameter("ids");
		
		int res = 0;
		
		if (roleId != null && !"".equals(roleId)) {
			if (parameter_ids != null && !"".equals(parameter_ids)) {
				
				//先删除之前的所有权限， 再添加新的权限进去
				
				roleFunctionService.deleteFunctionByRoleId(roleId);
				
				//先切割
				String[] ids = parameter_ids.split(",");
				
				for (String funcId : ids) {
					
					//将roleId 和 functionId 封装成 RoleFunction
					
					RoleFunction roleFunction = new RoleFunction();
					roleFunction.setRoleId(Integer.parseInt(roleId));
					roleFunction.setFuncId(Integer.parseInt(funcId));
					
					res += roleFunctionService.addFunction(roleFunction);
				}
				if (res > 0) {
					jsonObject.put("msg", "角色授权成功");
					jsonObject.put("success", true);
				}else {
					jsonObject.put("msg", "角色授权失败");
					jsonObject.put("success", false);
				}
			}
		}
		
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "addRole.do")
	public String addRolePage() {
		return "addRole";
	}
	
	@RequestMapping(value = "savaRole.dao")
	public void savaRole(Role role,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		
		JSONObject jsonObject = new JSONObject();
		
		if(role != null) {
			
			//将新增的角色数据的时间写入
			role.setCreateTime(new Date());
			
			int res = roleService.savaRole(role);
			
			if (res >= 1) {
				jsonObject.put("msg", "添加角色成功");
				jsonObject.put("success", true);
			}else {
				jsonObject.put("msg", "添加角色失败");
				jsonObject.put("success", false);
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "delRole.do")
	public void deleteRole(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		//获取参数
		String parmeter_ids = request.getParameter("ids");
		
		if (parmeter_ids != null && !"".equals(parmeter_ids)) {
			
			int res = 0;
			String[] ids = parmeter_ids.split(",");
			
			for (String roleId : ids) {
				
				res += roleService.deleteRole(roleId);
			}
			if (res > 0) {
				jsonObject.put("msg", "删除角色成功");
				jsonObject.put("success", true);
			}else {
				jsonObject.put("msg", "删除角色失败");
				jsonObject.put("success", false);
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "updateRole.do")
	public String updateRole(HttpServletRequest request, HttpServletResponse response) {
		
		String roleId = request.getParameter("id");
		
		if (roleId != null && !"".equals(roleId)) {
			
			Role role = roleService.getRoleId(roleId);
			
			request.setAttribute("role", role);
		}
		return "updateRole";
	}
	
	@RequestMapping(value = "savaUpdateRole.do")
	public void savaUpdateRole(Role role,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		if(role != null ) {
			
			//修改时间
			role.setCreateTime(new Date());
			
			int res = roleService.updateRole(role);
			
			if (res > 0) {
				jsonObject.put("msg", "角色信息修改成功");
				jsonObject.put("success", true);
			}else {
				jsonObject.put("msg", "角色信息修改失败");
				jsonObject.put("success", false);
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
}
