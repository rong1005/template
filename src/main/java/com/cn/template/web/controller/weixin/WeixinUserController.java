package com.cn.template.web.controller.weixin;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cn.template.entity.weixin.WeixinUser;
import com.cn.template.service.weixin.WeixinUserService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Maps;

/**
 * 微信订阅者信息的业务代理.
 * @author Libra
 *
 */
@Controller
@RequestMapping(value="wxuser")
public class WeixinUserController {

	private static Logger logger=LoggerFactory.getLogger(WeixinController.class);
	
	/** 微信订阅者信息的业务处理 */
	@Autowired
	private WeixinUserService weixinUserService;
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("nickname", "名称");
	}
	
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
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE_3) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<WeixinUser> weixinUsers = weixinUserService.getWeixinUser(searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("weixinUsers", weixinUsers);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "weixin/wxuser-list";
	}
	
}
