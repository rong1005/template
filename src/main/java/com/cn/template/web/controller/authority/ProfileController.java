package com.cn.template.web.controller.authority;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cn.template.entity.authority.User;
import com.cn.template.service.authority.UserService;
import com.cn.template.web.shiro.ShiroUser;
import com.cn.template.xutil.Utils;

/**
 * 用户修改自己资料的业务代理.
 * @author Libra
 *
 */
@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

	/** 用户管理的业务逻辑 */
	@Autowired
	private UserService userService;

	/**
	 * 进入个人信息更新页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String update(Model model) {
		Long id = Utils.getCurrentUserId();
		model.addAttribute("user", userService.getUser(id));
		return "authority/profile";
	}

	/**
	 * 修改个人信息.
	 * @param user
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("user") User user) {
		userService.updateUser(user);
		updateCurrentUserName(user.getName());
		return "redirect:/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("user", userService.getUser(id));
		}
	}


	/**
	 * 更新Shiro中当前用户的用户名.
	 */
	private void updateCurrentUserName(String userName) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		user.name = userName;
	}
}
