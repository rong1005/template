package com.cn.template.web.controller.authority;

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

import com.cn.template.entity.authority.Sidebar;
import com.cn.template.service.authority.SidebarService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Maps;

/**
 * 菜单信息管理的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/sidebar")
public class SidebarController {

	private static Logger logger=LoggerFactory.getLogger(SidebarController.class);
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "名称");
	}
	
	/** 菜单管理的业务逻辑 */
	@Autowired
	private SidebarService sidebarService;

	/**
	 * 菜单列表.
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE_10) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<Sidebar> sidebars = sidebarService.getSidebar(searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("sidebars", sidebars);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "authority/sidebar-list";
	}

	/**
	 * 进入菜单创建页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("sidebar", new Sidebar());
		model.addAttribute("topSidebars", sidebarService.getTopSidebar());
		model.addAttribute("action", "create");
		return "authority/sidebar-form";
	}

	/**
	 * 创建菜单.
	 * @param newSidebar
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Sidebar newSidebar, RedirectAttributes redirectAttributes) {
		sidebarService.saveSidebar(newSidebar);
		redirectAttributes.addFlashAttribute("message", "创建菜单成功");
		return "redirect:/sidebar/";
	}


	/**
	 * 进入菜单更新页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		model.addAttribute("sidebar", sidebarService.getSidebar(id));
		model.addAttribute("topSidebars", sidebarService.getTopSidebar());
		model.addAttribute("action", "update");
		return "authority/sidebar-form";
	}

	/**
	 * 提交更新的菜单信息.
	 * @param sidebar
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("sidebar") Sidebar sidebar, RedirectAttributes redirectAttributes) {
		sidebarService.saveSidebar(sidebar);
		redirectAttributes.addFlashAttribute("message", "更新菜单‘" + sidebar.getName() + "’成功");
		return "redirect:/sidebar";
	}
	
	/**
	 * 删除菜单记录.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		Sidebar sidebar = sidebarService.getSidebar(id);
		sidebarService.deleteSidebar(id);
		redirectAttributes.addFlashAttribute("message", "删除菜单" + sidebar.getName() + "成功");
		return "redirect:/sidebar";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出Sidebar对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getSidebar(@RequestParam(value = "id", defaultValue = "-1") Long id,@RequestParam(value = "preSidebar.id", defaultValue = "-1") Long preSidebarId, Model model) {
		if (id != -1) {
			Sidebar sidebar=sidebarService.getSidebar(id);
			if(preSidebarId>0){
				sidebar.setPreSidebar(sidebarService.getSidebar(preSidebarId));
			}else{
				sidebar.setPreSidebar(null);
			}
			model.addAttribute("sidebar", sidebar);
		}
	}
}
