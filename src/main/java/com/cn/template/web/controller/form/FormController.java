package com.cn.template.web.controller.form;

import java.util.Date;
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

import com.cn.template.entity.form.Form;
import com.cn.template.service.form.FieldService;
import com.cn.template.service.form.FormService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Maps;

/**
 * 表单信息的业务代理.
 * @author Libra
 *
 */
@Controller
@RequestMapping(value = "/form")
public class FormController {
	private static final Logger logger = LoggerFactory.getLogger(FormController.class);
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "标题");
	}

	/** 表单管理的业务逻辑 */
	@Autowired
	private FormService formService;
	
	@Autowired
	private FieldService fieldService;

	/**
	 * 表单列表.
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
		Page<Form> forms = formService.getForm(searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("forms", forms);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "form/form-list";
	}

	/**
	 * 进入表单创建页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("form", new Form());
		model.addAttribute("action", "create");
		return "form/form-form";
	}

	/**
	 * 创建表单.
	 * @param newForm
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Form newForm, RedirectAttributes redirectAttributes) {
		newForm.setCreateTime(new Date());
		newForm.setUpdateTime(new Date());
		try{
		formService.saveForm(newForm);
		}catch(Exception e){
			e.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", "创建表单成功");
		return "redirect:/form/";
	}

	/**
	 * 进入表单更新页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		model.addAttribute("form", formService.getForm(id));
		model.addAttribute("action", "update");
		return "form/form-form";
	}

	/**
	 * 更新表单.
	 * @param form
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("form") Form form, RedirectAttributes redirectAttributes) {
		form.setUpdateTime(new Date());
		formService.saveForm(form);
		redirectAttributes.addFlashAttribute("message", "更新表单成功");
		return "redirect:/form/";
	}

	/**
	 * 删除表单.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		formService.deleteForm(id);
		redirectAttributes.addFlashAttribute("message", "删除表单成功");
		return "redirect:/form/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出Form对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getForm(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("form", formService.getForm(id));
		}
	}

}
