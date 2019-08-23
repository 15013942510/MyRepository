package com.hua.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hua.domain.Role;
import com.hua.domain.User;
import com.hua.domain.UserRole;
import com.hua.service.RoleService;
import com.hua.service.UserRoleService;
import com.hua.service.UserService;

import net.sf.json.JSONObject;

//用户管理的控制器
@Controller
public class UserManagerController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleServce;
	@Autowired
	private UserRoleService userRoleService;
	
	@RequestMapping(value = "/userList.do")
	public String openUserManager() {
		return "userList";
	}

	// 展示用户列表数据
	@RequestMapping(value = "/findByPage.do")
	public void getUserList(HttpServletRequest request, HttpServletResponse response) throws IOException {

		JSONObject jsonobject = new JSONObject();

		// 接收 datagrid 传递过来的小弟 （rows page）
		String page = request.getParameter("page");

		String rows = request.getParameter("rows");

		// 将datagrid传递过来的小弟参数 转为 mysql limit 需要的参数

		Integer pageIndex = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);

		Integer pageSize = Integer.parseInt(rows);

		List<User> userlist = userService.getUserListByLimit(pageIndex, pageSize);

		// 算出total

		int total = userlist.size();

		/**
		 * 处理返回给dataGrid 表格控件的json数据格式
		 * 
		 * total
		 * 
		 * rows
		 * 
		 * 
		 * msg: 获取数据成功
		 * 
		 * success:true
		 * 
		 * 
		 */

		jsonobject.put("rows", userlist);
		jsonobject.put("total", total);
		jsonobject.put("msg", "获取数据成功");
		jsonobject.put("success", true);

		PrintWriter out = response.getWriter();
		out.write(jsonobject.toString());
		out.flush();
		out.close();

	}
	
	@RequestMapping(value = "/addUser.do")
	public String openAddUserPage() {
		return "addUser";
	}
	
	@RequestMapping(value = "/savaUser.dao")
	public void SavaUser(User user,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		if (user != null) {
			
			//将新增的用户数据的时间写入
			user.setCreateTime(new Date());
			
			//加密密码处理（使用 MDS 处理）
			int res = userService.savaUser(user);
			
			if (res > 0) {
				jsonObject.put("msg", "添加用户数据成功");
				jsonObject.put("success", true);
			}else {
				jsonObject.put("msg", "添加用户数据失败");
				jsonObject.put("success", false);
			}
		}
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//物理删除
	@RequestMapping(value = "/delUser.do")
	public void delUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		//获取参数
		String parameter_ids = request.getParameter("ids");
		
		String[] ids = parameter_ids.split(",");
		
		String msg = "";
		
		int res = 0;
		
		if (parameter_ids != null &&! "".equals(parameter_ids)) {
			
			for (String userId : ids) {
				
				List<UserRole> roleList = userRoleService.findRoleByUserId(userId);
				if (roleList != null && roleList.size() > 0) {
					msg = "当前用户与角色存在关联关系，请先解除";
					break;
				}else {
						
					res += userService.deleteUserById(userId);
				}
			}
		}
		
		if (res > 0) {
			jsonObject.put("msg", "删除用户数据成功");
			jsonObject.put("success", true);
		}else {
			jsonObject.put("msg", msg);
			jsonObject.put("success", false);
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
/*	//逻辑删除
	@RequestMapping(value = "/delete_user_status.do")
	public void delete_user_status(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		//获取参数
		String parameter_ids = request.getParameter("ids");
		
		String[] ids = parameter_ids.split(",");
		
		if (ids != null &&! "".equals(ids)) {
			int res = 0;
			for (String userId : ids) {
				res += userService.delete_user_statusByUserId(userId,0);
			}
			if (res > 0) {
				jsonObject.put("msg", "逻辑删除成功");
				jsonObject.put("success", true);
			}else {
				jsonObject.put("msg", "；逻辑删除失败");
				jsonObject.put("success", false);
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}*/
	
	//用户点击分配角色弹出分配角色列表
	@RequestMapping(value = "/assignRole.do")
	public String openRoleList(HttpServletRequest request,HttpServletResponse response) {
		
		//id 存储起来 方便 三部曲中 后面两步操作
		String userId = request.getParameter("id");
		
		request.setAttribute("userId", userId);
		return "roleList";
		
	}
	
	@RequestMapping(value = "/findAllRoleList.do")
	public void findAllRoleList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		List<Role> roleList = roleServce.findAllRoleList();
		if (roleList != null && roleList.size() > 0) {
			jsonObject.put("msg", "获取角色列表信息成功");
			jsonObject.put("success", true);
			jsonObject.put("data", roleList);
		}else {
			jsonObject.put("msg", "获取角色信息失败");
			jsonObject.put("success", false);
		}
		
		PrintWriter out = response.getWriter();
		
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "/findRoleByUserId.do")
	public void findRoleByUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		//取出userId
		String userId = request.getParameter("userId");
		if (userId != null && !"".equals(userId)) {
			
			List<UserRole> roleList = userRoleService.findRoleByUserId(userId);
			
			jsonObject.put("msg", "获取当前用户角色信息成功");
			jsonObject.put("success", true);
			jsonObject.put("data", roleList);
		}else {
			jsonObject.put("msg", "获取当前用户角色信息失败");
			jsonObject.put("success", false);
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "/save_assignRole.do")
	public void sava_assignRole(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		//取出用户id
		String userId = request.getParameter("userId");
		
		//取出roleIds 数组
		String paramter_ids = request.getParameter("ids");
		
		int res = 0;
		//再添加新的角色信息
		if (paramter_ids != null && !"".equals(paramter_ids)) {
			
			//先删除之前的所有角色信息
			userRoleService.deleteRoleByUserId(userId);
			
			String[] ids = paramter_ids.split(",");
			
			for (String roleId : ids) {
				
				//发送请求给数据库添加新的角色 （userId + RoleId）
				
				UserRole userRole = new UserRole();
				userRole.setUserId(Integer.parseInt(userId));
				userRole.setRoleId(Integer.parseInt(roleId));
				
				res += userRoleService.addUserRole(userRole);
			}
		}
		if (res > 0) {
			jsonObject.put("msg", "分配角色信息成功");
			jsonObject.put("success", true);
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "updateUser.do")
	public String openUpdateUserPage(HttpServletRequest request,HttpServletResponse response) {
		
		String userId = request.getParameter("id");
		
		if (userId != null && !"".equals(userId)) {
			
			User user = userService.getUserById(userId);
			
			request.setAttribute("user", user);
		}
		
		return "updateUser";
	}
	
	@RequestMapping(value = "savaUpdateUser.do")
	public void savaUpdateUser(User user,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		if (user != null) {
			
			//修改时间
			user.setCreateTime(new Date());
			
			//密码处理
			
			int res = userService.updateUser(user);
			
			if(res > 0) {
				jsonObject.put("msg", "用户信息修改成功");
				jsonObject.put("success", true);
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
}
