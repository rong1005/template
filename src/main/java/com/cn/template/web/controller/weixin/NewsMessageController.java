package com.cn.template.web.controller.weixin;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.template.entity.weixin.NewsMessage;
import com.cn.template.service.weixin.NewsCategoryService;
import com.cn.template.service.weixin.NewsMessageService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Maps;

/**
 * 图文消息的业务代理.
 * @author Libra
 *
 */
@Controller
@RequestMapping(value="news")
public class NewsMessageController {
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}
	
	/** 图文消息的业务处理 */
	@Autowired
	private NewsMessageService newsMessageService;
	
	/** 图文消息类别的业务处理 */
	@Autowired
	private NewsCategoryService newsCategoryService;

	/**
	 * 图文消息列表.
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{newsCategoryId}",method = RequestMethod.GET)
	public String list(@PathVariable(value = "newsCategoryId") Long newsCategoryId,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE_3) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<NewsMessage> newsMessages = newsMessageService.getNewsMessage(newsCategoryId,searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("newsMessages", newsMessages);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		model.addAttribute("newsCategory", newsCategoryService.getNewsCategory(newsCategoryId));

		return "message/news-list";
	}

	/**
	 * 进入图文消息创建页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(@RequestParam(value = "newsCategoryId") Long newsCategoryId,Model model) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setNewsCategory(newsCategoryService.getNewsCategory(newsCategoryId));
		model.addAttribute("newsMessage", newsMessage);
		model.addAttribute("action", "create");
		return "message/news-form";
	}

	/**
	 * 创建图文消息.
	 * @param newNewsMessage
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid NewsMessage newNewsMessage,
			@RequestParam(value="bigPic", required=false) MultipartFile bigPic,
			@RequestParam(value="smallPic", required=false) MultipartFile smallPic, 
			RedirectAttributes redirectAttributes) {
		newsMessageService.saveNewsMessage(newNewsMessage,bigPic,smallPic);
		redirectAttributes.addFlashAttribute("message", "创建图文消息成功");
		return "redirect:/news/"+newNewsMessage.getNewsCategory().getId();
	}

	/**
	 * 进入图文消息更新页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		model.addAttribute("newsMessage", newsMessageService.getNewsMessage(id));
		model.addAttribute("action", "update");
		return "message/news-form";
	}

	/**
	 * 更新图文消息.
	 * @param newsMessage
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("newsMessage") NewsMessage newsMessage, 
			@RequestParam(value="bigPic", required=false) MultipartFile bigPic,
			@RequestParam(value="smallPic", required=false) MultipartFile smallPic, 
			RedirectAttributes redirectAttributes) {
		newsMessageService.saveNewsMessage(newsMessage,bigPic,smallPic);
		redirectAttributes.addFlashAttribute("message", "更新图文消息成功");
		return "redirect:/news/"+newsMessage.getNewsCategory().getId();
	}

	/**
	 * 删除图文消息.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, @RequestParam(value = "newsCategoryId") Long newsCategoryId, RedirectAttributes redirectAttributes) {
		newsMessageService.deleteNewsMessage(id);
		redirectAttributes.addFlashAttribute("message", "删除图文消息成功");
		return "redirect:/news/"+newsCategoryId;
	}
	
	/**
	 * 发布图文消息.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "publish/{id}")
	public String publish(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		NewsMessage newsMessage = newsMessageService.getNewsMessage(id);
		newsMessageService.publishNewsMessage(newsMessage);
		redirectAttributes.addFlashAttribute("message", "发布图文消息成功");
		return "redirect:/news/"+newsMessage.getNewsCategory().getId();
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出NewsMessage对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getNewsMessage(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("newsMessage", newsMessageService.getNewsMessage(id));
		}
	}

	
}
