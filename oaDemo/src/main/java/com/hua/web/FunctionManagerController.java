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
import com.hua.domain.RoleFunction;
import com.hua.service.FunctionService;
import com.hua.service.RoleFunctionSerive;

import net.sf.json.JSONObject;

@Controller
public class FunctionManagerController {

	@Autowired
	private FunctionService functionService;

	@Autowired
	private RoleFunctionSerive roleFunctionService;

	@RequestMapping(value = "/functionManager.do")
	public String openfunctionManagerPage() {

		return "functionManager";
	}

	// 加载所有的权限信息
	@RequestMapping(value = "/findFunctionList.do")
	public void findFunctionList(HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		JSONObject jsonObject = new JSONObject();

		List<Function> allfunctionList = functionService.getAllFunctionList();

		if (allfunctionList != null && allfunctionList.size() > 0) {
			jsonObject.put("data", allfunctionList);
			jsonObject.put("msg", "获取所有权限成功");
		} else {
			jsonObject.put("msg", "获取所有权限失败");
		}

		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}

	// 弹出添加权限信息的窗口
	@RequestMapping(value = "/addPerm.do")
	public String openAddPermPage(HttpServletRequest request) {

		String parentId = request.getParameter("parentId");
		request.setAttribute("parentId", parentId);
		return "addFunction";
	}

	@RequestMapping(value = "/savePerm.do")
	public void savePermData(Function function, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		JSONObject jsonObject = new JSONObject();

		if (function != null) {

			// 获取权限名称，用户判断权限名称是否存在
			String funcName = function.getFuncName();

			if (funcName != null && !"".equals(funcName)) {

				Function fun = functionService.findFunctionByName(funcName);

				if (fun != null) {
					jsonObject.put("msg", "该权限名称已存在");
					jsonObject.put("success", false);
				} else {

					// 设置添加权限时间
					function.setCreateTime(new Date());

					int res = functionService.addFunction(function);

					if (res > 0) {
						jsonObject.put("msg", "添加权限成功");
						jsonObject.put("success", true);
					} else {
						jsonObject.put("msg", "添加权限失败");
						jsonObject.put("success", false);
					}
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}

	@RequestMapping(value = "/delPermById.do")
	public void delPermById(HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		JSONObject jsonObject = new JSONObject();

		String paramter_ids = request.getParameter("ids");
		String[] ids = paramter_ids.split(","); // 切割

		// 用来接收消息

		String msg = "";

		int res = 0;
		
		if (paramter_ids != null &&!"".equals(paramter_ids)) {
			
			for (String funcId : ids) {
				
				// 2.1.删除的时候，如果某个权限已经关联了角色信息，不允许直接删除，必须先解除关联关系！
				List<RoleFunction> roleList = roleFunctionService.findRoleListByFuncId(funcId);
				
				if (roleList != null && roleList.size() > 0) {
					
					msg = "当前权限和角色存在关联关系，请先解除！";
					break;
					
				} else {
					
					// 2.2.删除的时候，如果直接删除父菜单，不允许！！！
					List<Function> funcList = functionService.findFuncListByFunId(funcId);
					
					if (funcList != null && funcList.size() > 0) {
						
						msg = "不能直接删除父菜单";
						break;
						
					} else {
						
						res += functionService.deleteFunctionByFunId(funcId);
					}
				}
			}

		}

		if (res > 0) {
			jsonObject.put("msg", "删除权限成功");
			jsonObject.put("success", true);
		} else {
			jsonObject.put("msg", msg);
			jsonObject.put("success", false);
		}

		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}

	@RequestMapping(value = "/updatePerm.do")
	public String openUpdatePermPage(HttpServletRequest request, HttpServletResponse response) {

		String funcId = request.getParameter("id");
		request.setAttribute("id", funcId);

		Function function = functionService.findFunctionByFunId(funcId);

		request.setAttribute("function", function);

		return "updateFunction";
	}

	@RequestMapping(value = "/updatePermById.do")
	public void updatePermById(Function function, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		JSONObject jsonObject = new JSONObject();

		if (function != null) {
			// 封装更新时间
			function.setUpdateTime(new Date());

			int res = functionService.updateFunction(function);

			if (res > 0) {
				jsonObject.put("msg", "更新权限成功");
				jsonObject.put("success", true);
			} else {
				jsonObject.put("msg", "更新权限失败");
				jsonObject.put("success", false);
			}
		}

		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
}
