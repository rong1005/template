package com.cn.template.web.controller.user;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 负责打开登录页面(GET请求)和登录出错页面(POST请求)，真正登录的POST请求由ShiroDbRealm完成,
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {

	/**
	 * 打开登录页面(GET请求).
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		return "user/login-bg";
	}

	/**
	 * 登录出错页面(POST请求).
	 * @param userName
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "user/login-bg";
	}

}
