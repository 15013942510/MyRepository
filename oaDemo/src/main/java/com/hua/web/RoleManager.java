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

//��ɫ����
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
		
		//����datagrid���ݹ�����С�� page rows
		
		String page = request.getParameter("page");
		
		String rows = request.getParameter("rows");
		
		//�ж� page �� rows ���쳣���
		if (page == null && "".equals(page)) {
			page = "1";	//��һҳ
		}
		if (rows == null && "".equals(rows)) {
			rows = "10";	//ÿ����ʾ10������
		}
		
		//��datagrid���ݹ�����ҳ���������ת���� mysql �� limit ʹ��
		
		int pageIndex = (Integer.parseInt(page) - 1) * Integer.parseInt(rows);
		
		int pageSize = Integer.parseInt(rows);
		
		//���� service ���������ѯ
		
		List<Role> rolesList = (List<Role>) roleService.getAllRolesByLimit(pageIndex,pageSize);
		
		//ȡ��total
		
		int total = rolesList.size();
		
		if (rolesList != null && rolesList.size() > 0) {
			jsonObject.put("rows", rolesList);
			jsonObject.put("success", true);
			jsonObject.put("msg", "��ȡ��ɫ��Ϣ�ɹ�");
			jsonObject.put("total", total);
		}else {
			jsonObject.put("success", false);
			jsonObject.put("msg", "��ȡ��ɫ��Ϣʧ��");
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//�򿪽�ɫ��Ȩ��ҳ��
	@RequestMapping(value="/rolePerm.do")
	public String openRolePermPage(HttpServletRequest request,HttpServletResponse response) {
		
		String roleId = request.getParameter("id");
		request.setAttribute("roleId", roleId);
		
		return "rolePerm";
	}
	
	//����������ȡ���е�Ȩ����Ϣ��
	//�� roleId ��ǰ׼���ã�Ϊ�ڶ����͵�������׼��
	@RequestMapping(value = "/findAllFunction.do")
	public void findAllFunction (HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		List<Function> functionList = functionService.getAllFunctionList();
		
		if (functionList != null && functionList.size() > 0) {
			jsonObject.put("data", functionList);
			jsonObject.put("success", true);
			jsonObject.put("msg", "��ȡ����Ȩ����Ϣ�ɹ�");
		}else {
			jsonObject.put("success", false);
			jsonObject.put("msg", "��ȡ����Ȩ����Ϣʧ��");
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//������ �ڶ��� ���ݽ�ɫ Id ��ȡ��ǰ��ɫ�µ�����ȫѡ
	@RequestMapping(value = "findFunctionByRoleId.do")
	public void findFunctionByRoleId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		//ȡ����ɫ
		String roleId = request.getParameter("id");
		
		List<RoleFunction> allFunctionList = roleFunctionService.findFunctionByRoleId(roleId);
				
		if (allFunctionList != null && allFunctionList.size() > 0) {
			jsonObject.put("data", allFunctionList);
			jsonObject.put("msg", "��ǰroleId��ȡȨ�޳ɹ�");
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
		
		//��ȡ roleId �� ids
		
		String roleId = request.getParameter("roleId");
		
		String parameter_ids = request.getParameter("ids");
		
		int res = 0;
		
		if (roleId != null && !"".equals(roleId)) {
			if (parameter_ids != null && !"".equals(parameter_ids)) {
				
				//��ɾ��֮ǰ������Ȩ�ޣ� ������µ�Ȩ�޽�ȥ
				
				roleFunctionService.deleteFunctionByRoleId(roleId);
				
				//���и�
				String[] ids = parameter_ids.split(",");
				
				for (String funcId : ids) {
					
					//��roleId �� functionId ��װ�� RoleFunction
					
					RoleFunction roleFunction = new RoleFunction();
					roleFunction.setRoleId(Integer.parseInt(roleId));
					roleFunction.setFuncId(Integer.parseInt(funcId));
					
					res += roleFunctionService.addFunction(roleFunction);
				}
				if (res > 0) {
					jsonObject.put("msg", "��ɫ��Ȩ�ɹ�");
					jsonObject.put("success", true);
				}else {
					jsonObject.put("msg", "��ɫ��Ȩʧ��");
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
			
			//�������Ľ�ɫ���ݵ�ʱ��д��
			role.setCreateTime(new Date());
			
			int res = roleService.savaRole(role);
			
			if (res >= 1) {
				jsonObject.put("msg", "��ӽ�ɫ�ɹ�");
				jsonObject.put("success", true);
			}else {
				jsonObject.put("msg", "��ӽ�ɫʧ��");
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
		
		//��ȡ����
		String parmeter_ids = request.getParameter("ids");
		
		if (parmeter_ids != null && !"".equals(parmeter_ids)) {
			
			int res = 0;
			String[] ids = parmeter_ids.split(",");
			
			for (String roleId : ids) {
				
				res += roleService.deleteRole(roleId);
			}
			if (res > 0) {
				jsonObject.put("msg", "ɾ����ɫ�ɹ�");
				jsonObject.put("success", true);
			}else {
				jsonObject.put("msg", "ɾ����ɫʧ��");
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
			
			//�޸�ʱ��
			role.setCreateTime(new Date());
			
			int res = roleService.updateRole(role);
			
			if (res > 0) {
				jsonObject.put("msg", "��ɫ��Ϣ�޸ĳɹ�");
				jsonObject.put("success", true);
			}else {
				jsonObject.put("msg", "��ɫ��Ϣ�޸�ʧ��");
				jsonObject.put("success", false);
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
}
