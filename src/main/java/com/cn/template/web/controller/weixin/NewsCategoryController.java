package com.cn.template.web.controller.weixin;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.template.entity.weixin.NewsCategory;
import com.cn.template.service.weixin.NewsCategoryService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.enums.MessageCategory;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Maps;

/**
 * 图文消息类别管理的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/news-category")
public class NewsCategoryController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	/** 图文消息类别管理的业务逻辑 */
	@Autowired
	private NewsCategoryService newsCategoryService;

	/**
	 * 图文消息类别列表.
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE_3) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "messageCategory") MessageCategory messageCategory,
			Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<NewsCategory> newsCategorys = newsCategoryService.getNewsCategory(messageCategory, searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("newsCategorys", newsCategorys);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		
		model.addAttribute("messageCategory", messageCategory);

		return "message/news-category-list";
	}

	/**
	 * 进入图文消息类别创建页面.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(@RequestParam(value = "messageCategory") MessageCategory messageCategory, Model model) {
		NewsCategory newsCategory =new NewsCategory();
		newsCategory.setMessageCategory(messageCategory);
		model.addAttribute("newsCategory", newsCategory);
		model.addAttribute("action", "create");
		return "message/news-category-form";
	}

	/**
	 * 创建图文消息类别.
	 * 
	 * @param newNewsCategory
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid NewsCategory newNewsCategory,RedirectAttributes redirectAttributes) {
		newsCategoryService.saveNewsCategory(newNewsCategory);
		redirectAttributes.addFlashAttribute("message", "创建图文消息类别成功");
		return "redirect:/news-category?messageCategory="+newNewsCategory.getMessageCategory();
	}

	/**
	 * 进入图文消息类别更新页面.
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		NewsCategory newsCategory = newsCategoryService.getNewsCategory(id);
		model.addAttribute("newsCategory",newsCategory);
		model.addAttribute("messageCategory", newsCategory.getMessageCategory());
		model.addAttribute("action", "update");
		return "message/news-category-form";
	}

	/**
	 * 更新图文消息类别.
	 * 
	 * @param newsCategory
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("newsCategory") NewsCategory newsCategory, RedirectAttributes redirectAttributes) {
		newsCategoryService.saveNewsCategory(newsCategory);
		redirectAttributes.addFlashAttribute("message", "更新图文消息类别成功");
		return "redirect:/news-category?messageCategory="+newsCategory.getMessageCategory();
	}

	/**
	 * 删除图文消息类别.
	 * 
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, @RequestParam(value = "messageCategory") MessageCategory messageCategory, RedirectAttributes redirectAttributes) {
		newsCategoryService.deleteNewsCategory(id);
		redirectAttributes.addFlashAttribute("message", "删除图文消息类别成功");
		return "redirect:/news-category?messageCategory="+messageCategory;
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法,
	 * 实现预处理部分绑定的效果,先根据form的id从数据库查出NewsCategory对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getNewsCategory(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("newsCategory",newsCategoryService.getNewsCategory(id));
		}
	}

}
