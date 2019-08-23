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

//�û�����Ŀ�����
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

	// չʾ�û��б�����
	@RequestMapping(value = "/findByPage.do")
	public void getUserList(HttpServletRequest request, HttpServletResponse response) throws IOException {

		JSONObject jsonobject = new JSONObject();

		// ���� datagrid ���ݹ�����С�� ��rows page��
		String page = request.getParameter("page");

		String rows = request.getParameter("rows");

		// ��datagrid���ݹ�����С�ܲ��� תΪ mysql limit ��Ҫ�Ĳ���

		Integer pageIndex = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);

		Integer pageSize = Integer.parseInt(rows);

		List<User> userlist = userService.getUserListByLimit(pageIndex, pageSize);

		// ���total

		int total = userlist.size();

		/**
		 * �����ظ�dataGrid ���ؼ���json���ݸ�ʽ
		 * 
		 * total
		 * 
		 * rows
		 * 
		 * 
		 * msg: ��ȡ���ݳɹ�
		 * 
		 * success:true
		 * 
		 * 
		 */

		jsonobject.put("rows", userlist);
		jsonobject.put("total", total);
		jsonobject.put("msg", "��ȡ���ݳɹ�");
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
			
			//���������û����ݵ�ʱ��д��
			user.setCreateTime(new Date());
			
			//�������봦��ʹ�� MDS ����
			int res = userService.savaUser(user);
			
			if (res > 0) {
				jsonObject.put("msg", "����û����ݳɹ�");
				jsonObject.put("success", true);
			}else {
				jsonObject.put("msg", "����û�����ʧ��");
				jsonObject.put("success", false);
			}
		}
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//����ɾ��
	@RequestMapping(value = "/delUser.do")
	public void delUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		//��ȡ����
		String parameter_ids = request.getParameter("ids");
		
		String[] ids = parameter_ids.split(",");
		
		String msg = "";
		
		int res = 0;
		
		if (parameter_ids != null &&! "".equals(parameter_ids)) {
			
			for (String userId : ids) {
				
				List<UserRole> roleList = userRoleService.findRoleByUserId(userId);
				if (roleList != null && roleList.size() > 0) {
					msg = "��ǰ�û����ɫ���ڹ�����ϵ�����Ƚ��";
					break;
				}else {
						
					res += userService.deleteUserById(userId);
				}
			}
		}
		
		if (res > 0) {
			jsonObject.put("msg", "ɾ���û����ݳɹ�");
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
	
/*	//�߼�ɾ��
	@RequestMapping(value = "/delete_user_status.do")
	public void delete_user_status(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		//��ȡ����
		String parameter_ids = request.getParameter("ids");
		
		String[] ids = parameter_ids.split(",");
		
		if (ids != null &&! "".equals(ids)) {
			int res = 0;
			for (String userId : ids) {
				res += userService.delete_user_statusByUserId(userId,0);
			}
			if (res > 0) {
				jsonObject.put("msg", "�߼�ɾ���ɹ�");
				jsonObject.put("success", true);
			}else {
				jsonObject.put("msg", "���߼�ɾ��ʧ��");
				jsonObject.put("success", false);
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}*/
	
	//�û���������ɫ���������ɫ�б�
	@RequestMapping(value = "/assignRole.do")
	public String openRoleList(HttpServletRequest request,HttpServletResponse response) {
		
		//id �洢���� ���� �������� ������������
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
			jsonObject.put("msg", "��ȡ��ɫ�б���Ϣ�ɹ�");
			jsonObject.put("success", true);
			jsonObject.put("data", roleList);
		}else {
			jsonObject.put("msg", "��ȡ��ɫ��Ϣʧ��");
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
		
		//ȡ��userId
		String userId = request.getParameter("userId");
		if (userId != null && !"".equals(userId)) {
			
			List<UserRole> roleList = userRoleService.findRoleByUserId(userId);
			
			jsonObject.put("msg", "��ȡ��ǰ�û���ɫ��Ϣ�ɹ�");
			jsonObject.put("success", true);
			jsonObject.put("data", roleList);
		}else {
			jsonObject.put("msg", "��ȡ��ǰ�û���ɫ��Ϣʧ��");
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
		
		//ȡ���û�id
		String userId = request.getParameter("userId");
		
		//ȡ��roleIds ����
		String paramter_ids = request.getParameter("ids");
		
		int res = 0;
		//������µĽ�ɫ��Ϣ
		if (paramter_ids != null && !"".equals(paramter_ids)) {
			
			//��ɾ��֮ǰ�����н�ɫ��Ϣ
			userRoleService.deleteRoleByUserId(userId);
			
			String[] ids = paramter_ids.split(",");
			
			for (String roleId : ids) {
				
				//������������ݿ�����µĽ�ɫ ��userId + RoleId��
				
				UserRole userRole = new UserRole();
				userRole.setUserId(Integer.parseInt(userId));
				userRole.setRoleId(Integer.parseInt(roleId));
				
				res += userRoleService.addUserRole(userRole);
			}
		}
		if (res > 0) {
			jsonObject.put("msg", "�����ɫ��Ϣ�ɹ�");
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
			
			//�޸�ʱ��
			user.setCreateTime(new Date());
			
			//���봦��
			
			int res = userService.updateUser(user);
			
			if(res > 0) {
				jsonObject.put("msg", "�û���Ϣ�޸ĳɹ�");
				jsonObject.put("success", true);
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
}
