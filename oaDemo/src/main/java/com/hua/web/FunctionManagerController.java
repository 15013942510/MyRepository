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

	// �������е�Ȩ����Ϣ
	@RequestMapping(value = "/findFunctionList.do")
	public void findFunctionList(HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		JSONObject jsonObject = new JSONObject();

		List<Function> allfunctionList = functionService.getAllFunctionList();

		if (allfunctionList != null && allfunctionList.size() > 0) {
			jsonObject.put("data", allfunctionList);
			jsonObject.put("msg", "��ȡ����Ȩ�޳ɹ�");
		} else {
			jsonObject.put("msg", "��ȡ����Ȩ��ʧ��");
		}

		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}

	// �������Ȩ����Ϣ�Ĵ���
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

			// ��ȡȨ�����ƣ��û��ж�Ȩ�������Ƿ����
			String funcName = function.getFuncName();

			if (funcName != null && !"".equals(funcName)) {

				Function fun = functionService.findFunctionByName(funcName);

				if (fun != null) {
					jsonObject.put("msg", "��Ȩ�������Ѵ���");
					jsonObject.put("success", false);
				} else {

					// �������Ȩ��ʱ��
					function.setCreateTime(new Date());

					int res = functionService.addFunction(function);

					if (res > 0) {
						jsonObject.put("msg", "���Ȩ�޳ɹ�");
						jsonObject.put("success", true);
					} else {
						jsonObject.put("msg", "���Ȩ��ʧ��");
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
		String[] ids = paramter_ids.split(","); // �и�

		// ����������Ϣ

		String msg = "";

		int res = 0;
		
		if (paramter_ids != null &&!"".equals(paramter_ids)) {
			
			for (String funcId : ids) {
				
				// 2.1.ɾ����ʱ�����ĳ��Ȩ���Ѿ������˽�ɫ��Ϣ��������ֱ��ɾ���������Ƚ��������ϵ��
				List<RoleFunction> roleList = roleFunctionService.findRoleListByFuncId(funcId);
				
				if (roleList != null && roleList.size() > 0) {
					
					msg = "��ǰȨ�޺ͽ�ɫ���ڹ�����ϵ�����Ƚ����";
					break;
					
				} else {
					
					// 2.2.ɾ����ʱ�����ֱ��ɾ�����˵�������������
					List<Function> funcList = functionService.findFuncListByFunId(funcId);
					
					if (funcList != null && funcList.size() > 0) {
						
						msg = "����ֱ��ɾ�����˵�";
						break;
						
					} else {
						
						res += functionService.deleteFunctionByFunId(funcId);
					}
				}
			}

		}

		if (res > 0) {
			jsonObject.put("msg", "ɾ��Ȩ�޳ɹ�");
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
			// ��װ����ʱ��
			function.setUpdateTime(new Date());

			int res = functionService.updateFunction(function);

			if (res > 0) {
				jsonObject.put("msg", "����Ȩ�޳ɹ�");
				jsonObject.put("success", true);
			} else {
				jsonObject.put("msg", "����Ȩ��ʧ��");
				jsonObject.put("success", false);
			}
		}

		PrintWriter out = response.getWriter();
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
}
