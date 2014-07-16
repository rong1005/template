package com.cn.template.web.controller.authority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.template.entity.authority.Role;
import com.cn.template.entity.authority.Sidebar;
import com.cn.template.service.authority.RoleService;
import com.cn.template.service.authority.SidebarService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Maps;

/**
 * 角色信息管理的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController {

	private static Logger logger=LoggerFactory.getLogger(RoleController.class);
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "名称");
	}
	
	/** 角色管理的业务逻辑 */
	@Autowired
	private RoleService roleService;
	
	/** 菜单管理的业务逻辑 */
	@Autowired
	private SidebarService sidebarService;

	/**
	 * 角色列表.
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
		Page<Role> roles = roleService.getRole(searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("roles", roles);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "authority/role-list";
	}

	/**
	 * 进入角色创建页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("role", new Role());
		model.addAttribute("action", "create");
		return "authority/role-form";
	}
	
	/**
	 * 创建角色.
	 * @param newRole
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Role newRole, RedirectAttributes redirectAttributes) {
		roleService.saveRole(newRole);
		redirectAttributes.addFlashAttribute("message", "创建角色成功");
		return "redirect:/role/";
	}


	/**
	 * 进入角色更新页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		model.addAttribute("role", roleService.getRole(id));
		model.addAttribute("action", "update");
		return "authority/role-form";
	}

	/**
	 * 提交更新的角色信息.
	 * @param role
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("role") Role role, RedirectAttributes redirectAttributes) {
		logger.info(role.toString());
		roleService.saveRole(role);
		redirectAttributes.addFlashAttribute("message", "更新角色‘" + role.getName() + "’成功");
		return "redirect:/role";
	}
	
	/**
	 * 删除角色记录.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		Role role = roleService.getRole(id);
		roleService.deleteRole(id);
		redirectAttributes.addFlashAttribute("message", "删除角色" + role.getName() + "成功");
		return "redirect:/role";
	}
	
	/**
	 * 进入配置角色菜单信息.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="sidebar/{id}" ,method=RequestMethod.GET)
	public String sidebar(@PathVariable("id") Long id, Model model){
		Role role = roleService.getRole(id);
		model.addAttribute("role", roleService.getRole(id));
		model.addAttribute("topSidebar", sidebarService.getTopSidebar());
		model.addAttribute("isExist", isExist(sidebarService.getAllSidebar(), role.getSidebars()));
		return "authority/role-sidebar";
	}
	
	/**
	 * 配置角色菜单信息.
	 * @param role
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "sidebar", method = RequestMethod.POST)
	public String sidebar(@Valid @ModelAttribute("role") Role role, @RequestParam("sidebars.id") List<Long> sidebarIds, RedirectAttributes redirectAttributes) {
		role.setSidebars(new ArrayList<Sidebar>());
		for(Long sidebarId : sidebarIds){
			role.getSidebars().add(sidebarService.getSidebar(sidebarId));
		}
		logger.info(role.toString());
		roleService.saveRole(role);
		redirectAttributes.addFlashAttribute("message", "更新角色‘" + role.getName() + "’成功");
		return "redirect:/role";
	}
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出Role对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getRole(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("role", roleService.getRole(id));
		}
	}
	
	/**
	 * 判断角色是否有查阅菜单权限.
	 * @param allSidebar
	 * @param roleSidebar
	 * @return
	 */
	private Map<Long,Boolean> isExist(List<Sidebar> allSidebar,List<Sidebar> roleSidebar){
		Map<Long,Boolean> map = Maps.newHashMap();
		for(Sidebar sidebar:allSidebar){
			if(roleSidebar.contains(sidebar)){
				map.put(sidebar.getId(), true);
			}else{
				map.put(sidebar.getId(), false);
			}
		}
		return map;
	}
}
