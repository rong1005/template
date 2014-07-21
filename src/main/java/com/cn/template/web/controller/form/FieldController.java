package com.cn.template.web.controller.form;

import java.util.Date;
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

import com.cn.template.entity.form.Field;
import com.cn.template.service.form.FieldService;
import com.cn.template.service.form.FormService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Maps;

/**
 * 字段信息的业务代理.
 * @author Libra
 *
 */
@Controller
@RequestMapping(value = "/field")
public class FieldController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "标题");
	}

	/** 字段信息的业务逻辑 */
	@Autowired
	private FieldService fieldService;
	
	/** 表单信息的业务处理 */
	@Autowired
	private FormService formService;
	

	/**
	 * 字段列表.
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{formId}",method = RequestMethod.GET)
	public String list(@PathVariable(value = "formId") Long formId,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE_10) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<Field> fields = fieldService.getFormField(formId, searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("fields", fields);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		
		model.addAttribute("form", formService.getForm(formId));

		return "form/field-list";
	}

	/**
	 * 进入字段创建页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(@RequestParam(value = "formId") Long formId,Model model) {
		Field field = new Field();
		field.setForm(formService.getForm(formId));
		model.addAttribute("field", field);
		model.addAttribute("action", "create");
		return "form/field-form";
	}

	/**
	 * 创建字段.
	 * @param newField
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Field newField, RedirectAttributes redirectAttributes) {
		newField.setCreateTime(new Date());
		newField.setUpdateTime(new Date());
		try{
			newField.setForm(formService.getForm(newField.getForm().getId()));
			fieldService.saveField(newField);
		}catch(Exception e){
			e.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", "创建字段成功");
		return "redirect:/field/"+newField.getForm().getId();
	}

	/**
	 * 进入字段更新页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		model.addAttribute("field", fieldService.getField(id));
		model.addAttribute("action", "update");
		return "form/field-form";
	}

	/**
	 * 更新字段.
	 * @param field
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("field") Field field, RedirectAttributes redirectAttributes) {
		fieldService.saveField(field);
		redirectAttributes.addFlashAttribute("message", "更新字段成功");
		return "redirect:/field/"+field.getForm().getId();
	}

	/**
	 * 删除字段.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, @RequestParam(value = "formId") Long formId, RedirectAttributes redirectAttributes) {
		fieldService.deleteField(id);
		redirectAttributes.addFlashAttribute("message", "删除字段成功");
		return "redirect:/field/"+formId;
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出Field对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getField(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("field", fieldService.getField(id));
		}
	}

}
