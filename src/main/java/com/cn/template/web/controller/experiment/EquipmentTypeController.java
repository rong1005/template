package com.cn.template.web.controller.experiment;

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

import com.cn.template.entity.experiment.EquipmentType;
import com.cn.template.service.experiment.EquipmentTypeService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Maps;

/**
 * 实验设备类型的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/equipment/type")
public class EquipmentTypeController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "标题");
	}

	/** 实验设备类型的业务逻辑 */
	@Autowired
	private EquipmentTypeService equipmentTypeService;

	/**
	 * 实验设备类型列表.
	 * 
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
		Page<EquipmentType> equipmentTypes = equipmentTypeService.getEquipmentType(searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("equipmentTypes", equipmentTypes);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "experiment/equipment-type-list";
	}

	/**
	 * 进入实验设备类型创建页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("equipmentType", new EquipmentType());
		model.addAttribute("action", "create");
		return "experiment/equipment-type-form";
	}

	/**
	 * 创建实验设备类型.
	 * @param newEquipmentType
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid EquipmentType newEquipmentType, RedirectAttributes redirectAttributes) {
		newEquipmentType.setCreateTime(new Date());
		newEquipmentType.setUpdateTime(new Date());
		
		equipmentTypeService.saveEquipmentType(newEquipmentType);
		redirectAttributes.addFlashAttribute("message", "创建实验设备类型成功");
		return "redirect:/equipment/type/";
	}

	/**
	 * 进入实验设备类型更新页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		model.addAttribute("equipmentType", equipmentTypeService.getEquipmentType(id));
		model.addAttribute("action", "update");
		return "experiment/equipment-type-form";
	}

	/**
	 * 更新实验设备类型.
	 * @param equipmentType
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("equipmentType") EquipmentType equipmentType, RedirectAttributes redirectAttributes) {
		equipmentType.setUpdateTime(new Date());
		equipmentTypeService.saveEquipmentType(equipmentType);
		redirectAttributes.addFlashAttribute("message", "更新实验设备类型成功");
		return "redirect:/equipment/type/";
	}

	/**
	 * 删除实验设备类型.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		equipmentTypeService.deleteEquipmentType(id);
		redirectAttributes.addFlashAttribute("message", "删除实验设备类型成功");
		return "redirect:/equipment/type/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出EquipmentType对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getEquipmentType(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("equipmentType", equipmentTypeService.getEquipmentType(id));
		}
	}

}
