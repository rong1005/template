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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.template.entity.experiment.Price;
import com.cn.template.service.experiment.EquipmentTypeService;
import com.cn.template.service.experiment.PriceService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Maps;

/**
 * 收费管理的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/price")
public class PriceController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("project", "实验项目");
	}

	/** 收费管理的业务逻辑 */
	@Autowired
	private PriceService priceService;
	
	/** 实验设备的业务逻辑 */
	@Autowired
	private EquipmentTypeService equipmentTypeService; 
	
	/**
	 * 收费列表.
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
		Page<Price> prices = priceService.getPrice(searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("prices", prices);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "experiment/price-list";
	}

	/**
	 * 进入收费创建页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("equipments", equipmentTypeService.getAllEquipmentType()); 
		model.addAttribute("price", new Price());
		model.addAttribute("action", "create");
		return "experiment/price-form";
	}

	/**
	 * 创建收费.
	 * @param newPrice
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Price newPrice, RedirectAttributes redirectAttributes) {
		newPrice.setCreateTime(new Date());
		newPrice.setUpdateTime(new Date());
		priceService.savePrice(newPrice);
		redirectAttributes.addFlashAttribute("message", "创建收费成功");
		return "redirect:/price/";
	}

	/**
	 * 进入收费更新页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		model.addAttribute("equipments", equipmentTypeService.getAllEquipmentType()); 
		model.addAttribute("price", priceService.getPrice(id));
		model.addAttribute("action", "update");
		return "experiment/price-form";
	}

	/**
	 * 更新收费.
	 * @param price
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("price") Price price, RedirectAttributes redirectAttributes) {
		priceService.savePrice(price);
		redirectAttributes.addFlashAttribute("message", "更新收费成功");
		return "redirect:/price/";
	}

	/**
	 * 删除收费.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		priceService.deletePrice(id);
		redirectAttributes.addFlashAttribute("message", "删除收费成功");
		return "redirect:/price/";
	}
	
	
	/**
	 * JSon返回详细的报价信息.
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "show/{id}")
	@ResponseBody
	public Price jsonShow(@PathVariable("id") Long id){
		return priceService.getPrice(id);
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出Price对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getPrice(@RequestParam(value = "id", defaultValue = "-1") Long id,@RequestParam(value = "equipmentType.id", defaultValue = "-1") Long equipmentTypeId, Model model) {
		if (id != -1) {
			Price price = priceService.getPrice(id);
			if(equipmentTypeId!=-1){
				price.setEquipmentType(equipmentTypeService.getEquipmentType(equipmentTypeId));
			}
			model.addAttribute("price", price);
		}
	}

}
