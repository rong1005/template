package com.cn.template.web.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.template.entity.Role;
import com.cn.template.entity.User;
import com.cn.template.service.user.RoleService;
import com.cn.template.service.user.UserService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.Utils;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Maps;

/**
 * 用户信息管理的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "姓名");
	}
	
	/** 用户管理的业务逻辑 */
	@Autowired
	private UserService userService;

	/** 角色管理的业务处理 */
	@Autowired
	private RoleService roleService;
	
	/**
	 * 用户列表.
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE_3) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<User> users = userService.getUser(searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("users", users);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "user/user-list";
	}

	/**
	 * 进入用户创建页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		User user = new User();
		List<Role> allRole = roleService.getAllRole();
		model.addAttribute("user", user);
		model.addAttribute("allRole", allRole);
		model.addAttribute("isExist", isExist(allRole, new ArrayList<Role>()));
		model.addAttribute("action", "create");
		return "user/user-form";
	}

	/**
	 * 创建用户.
	 * @param newUser
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid User newUser, @RequestParam(value="roles.id") List<Long> rolesIds, RedirectAttributes redirectAttributes) {
		newUser.setRoles(new ArrayList<Role>());
		for(Long rolesId : rolesIds){
			newUser.getRoles().add(roleService.getRole(rolesId));
		}
		userService.registerUser(newUser);
		redirectAttributes.addFlashAttribute("message", "创建用户‘" + newUser.getName() + "’成功");
		return "redirect:/user";
	}


	/**
	 * 进入用户更新页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		User user = userService.getUser(id);
		List<Role> allRole = roleService.getAllRole();
		model.addAttribute("user", user);
		model.addAttribute("allRole", allRole);
		model.addAttribute("isExist", isExist(allRole, user.getRoles()));
		model.addAttribute("action", "update");
		return "user/user-form";
	}

	/**
	 * 提交更新的用户信息.
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("user") User user, @RequestParam(value="roles.id") List<Long> rolesIds, RedirectAttributes redirectAttributes) {
		user.setRoles(new ArrayList<Role>());
		for(Long rolesId : rolesIds){
			user.getRoles().add(roleService.getRole(rolesId));
		}
		userService.updateUser(user);
		redirectAttributes.addFlashAttribute("message", "更新用户‘" + user.getName() + "’成功");
		return "redirect:/user";
	}
	
	/**
	 * 用户密码修改.
	 * @return
	 */
	@RequestMapping(value = "updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public String updatePassword(@RequestParam(value = "oldPassword") String oldPassword,
			@RequestParam(value = "newPassword") String newPassword,
			@RequestParam(value = "confirmPassword") String confirmPassword){
		
		if(!newPassword.equals(confirmPassword)){
			return "{\"status\":false,\"message\":\"您两次输入的新密码不一致，请确认！\"}";
		}
		
		User user=userService.getUser(Utils.getCurrentUserId());
		if(!user.getPassword().equals(userService.entryptPassword(user.getSalt(), oldPassword))){
			return "{\"status\":false,\"message\":\"请输入正确的当前密码！\"}";
		}
			
		return "{\"status\":true,\"message\":\"您的密码修改成功，请重新登录！\"}";
	}

	/**
	 * 删除用户记录.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		User user = userService.getUser(id);
		userService.deleteUser(id);
		redirectAttributes.addFlashAttribute("message", "删除用户" + user.getLoginName() + "成功");
		return "redirect:/user";
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
	 * 判断用户是否有该角色权限.
	 * @param allRole
	 * @param userRole
	 * @return
	 */
	private Map<Long,Boolean> isExist(List<Role> allRole,List<Role> userRole){
		Map<Long,Boolean> map =Maps.newHashMap();
		
		for(Role role : allRole){
			if(userRole.contains(role)){
				map.put(role.getId(), true);
			}else{
				map.put(role.getId(), false);
			}
		}
		
		return map;
	}
}
