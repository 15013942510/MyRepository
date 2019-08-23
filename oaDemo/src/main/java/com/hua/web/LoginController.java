package com.hua.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hua.domain.User;
import com.hua.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	@RequestMapping(value="/login")
	public String login(String userName,String passWord,HttpServletRequest request,HttpServletResponse resp) {
		User user = userService.isLogin(userName, passWord);
		HttpSession session = request.getSession();
		//session.invalidate();
		if(user != null) {
			session.setAttribute("user", user);
			System.out.println("µÇÂ¼³É¹¦£º" + user.getUserName());
		}else {
			System.out.println("µÇÂ¼Ê§°Ü");
		}
		return "index";
	}
}
