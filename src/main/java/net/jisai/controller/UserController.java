package net.jisai.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.jisai.pojo.User;
import net.jisai.service.UserService;

@Controller
public class UserController {
	@Resource
	private UserService userServiceImpl;
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@RequestMapping("/reg")
	@ResponseBody
	public String register(User user) {
		int index = userServiceImpl.addUser(user);
		if (index>0) {
			return "success";
		}else {
			return "failed";
		}
	}
	@RequestMapping("/log")
	public String login(User user, HttpServletRequest request, Model model) {
		User loginuser = userServiceImpl.login(user);
		if (loginuser!=null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", loginuser);
			System.out.println(loginuser);
			return "redirect:/main";
		}else {
			model.addAttribute("msg","用户名或密码错误，请重新登录");
			return "redirect:/login";
		}
	}
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		return "redirect:/login";
	}
	
	/**
	 * 页面跳转，返回用户输入相对应的HTML
	 * @param page
	 * @return
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}
}
