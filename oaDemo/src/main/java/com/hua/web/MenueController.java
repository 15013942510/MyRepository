package com.hua.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hua.domain.User;
import com.hua.menue.Menue;
import com.hua.service.MenueService;

@Controller
public class MenueController {
	@Autowired
	private MenueService menueService;
	@ResponseBody
	@RequestMapping(value="/menu.do")
   public List<Menue> getMenuList(HttpServletRequest request,HttpServletResponse response)
   {

		// ��ȡsession
		HttpSession session = request.getSession();
		// ��session����ȡ��user����
		User user = (User) session.getAttribute("user");
		
		// ��user��ȡ��userId
		
		List<Menue> menuList = menueService.getMenuList(String.valueOf(user.getUserId()));
		
		return menuList;	
   }
}
