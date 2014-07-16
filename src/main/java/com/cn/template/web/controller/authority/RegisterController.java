package com.cn.template.web.controller.authority;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.template.entity.authority.User;
import com.cn.template.service.authority.UserService;

/**
 * 用户注册的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {

	/** 用户管理的业务逻辑 */
	@Autowired
	private UserService userService;

	/**
	 * 进入主页页面.
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String register() {
		return "authority/register";
	}

	/**
	 * 提交注册信息.
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid User user, RedirectAttributes redirectAttributes) {
		userService.registerUser(user);
		redirectAttributes.addFlashAttribute("username", user.getLoginName());
		redirectAttributes.addFlashAttribute("message", " 注册成功！请输入账号、密码登录");
		return "redirect:/login";
	}

	/**
	 * Ajax请求校验loginName是否唯一.
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkLoginName(@RequestParam("loginName") String loginName) {
		if (userService.findUserByLoginName(loginName) == null) {
			return "true";
		} else {
			return "false";
		}
	}
}
