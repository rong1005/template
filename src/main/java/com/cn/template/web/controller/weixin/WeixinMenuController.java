package com.cn.template.web.controller.weixin;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.cn.template.entity.weixin.WeixinMenu;
import com.cn.template.service.weixin.WeixinMenuService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.Utils;
import com.cn.template.xutil.enums.Whether;
import com.cn.template.xutil.web.Servlets;
import com.cn.template.xutil.weixin.AccessTokenUtil;
import com.cn.template.xutil.weixin.WeixinConstants;
import com.google.common.collect.Maps;

/**
 * 微信菜单管理的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/wxmenu")
public class WeixinMenuController {

	private static Logger logger=LoggerFactory.getLogger(WeixinMenuController.class);
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "名称");
	}

	/** 微信菜单管理的业务逻辑 */
	@Autowired
	private WeixinMenuService weixinMenuService;

	/**
	 * 微信菜单列表.
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

		Page<WeixinMenu> weixinMenus = weixinMenuService.getWeixinMenu(searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("weixinMenus", weixinMenus);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "wxmenu/wxmenu-list";
	}

	/**
	 * 进入微信菜单创建页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("weixinMenu", new WeixinMenu());
		model.addAttribute("action", "create");
		return "wxmenu/wxmenu-form";
	}

	/**
	 * 创建微信菜单.
	 * @param newWeixinMenu
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid WeixinMenu newWeixinMenu, RedirectAttributes redirectAttributes) {
		weixinMenuService.saveWeixinMenu(newWeixinMenu);
		redirectAttributes.addFlashAttribute("message", "创建微信菜单成功");
		return "redirect:/wxmenu/";
	}

	/**
	 * 进入微信菜单更新页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		model.addAttribute("weixinMenu", weixinMenuService.getWeixinMenu(id));
		model.addAttribute("action", "update");
		return "wxmenu/wxmenu-form";
	}

	/**
	 * 更新微信菜单.
	 * @param menu
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("weixinMenu") WeixinMenu weixinMenu, RedirectAttributes redirectAttributes) {
		weixinMenuService.saveWeixinMenu(weixinMenu);
		redirectAttributes.addFlashAttribute("message", "更新微信菜单成功");
		return "redirect:/wxmenu/";
	}

	/**
	 * 删除微信菜单.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		weixinMenuService.deleteWeixinMenu(id);
		redirectAttributes.addFlashAttribute("message", "删除微信菜单成功");
		return "redirect:/wxmenu/";
	}
	
	/**
	 * 将微信菜单发布到微信平台.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "publish/{id}", method = RequestMethod.GET)
	public String publish(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, HttpServletResponse response){
		try{
			WeixinMenu menu = weixinMenuService.getWeixinMenu(id);
			menu.setIsUsing(Whether.YES);
			weixinMenuService.saveWeixinMenu(menu);
			logger.info(Utils.contentPublish(String.format(WeixinConstants.CREATE_MENU_URL, AccessTokenUtil.getAccessToken(false)), menu.getDescription()));
			redirectAttributes.addFlashAttribute("message", "微信菜单发布成功");
		}catch(Exception e){
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "微信菜单发布失败");
		}
		return "redirect:/wxmenu/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出WeixinMenu对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getWeixinMenu(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("weixinMenu", weixinMenuService.getWeixinMenu(id));
		}
	}

}
