package com.cn.template.web.controller.experiment;

import java.util.Date;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.template.entity.experiment.Equipment;
import com.cn.template.entity.experiment.EquipmentType;
import com.cn.template.service.experiment.EquipmentService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Maps;

/**
 * 实验设备的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/equipment")
public class EquipmentController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "标题");
	}

	/** 实验设备的业务逻辑 */
	@Autowired
	private EquipmentService equipmentService;

	/**
	 * 实验设备列表.
	 * 
	 * @param typeId
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{typeId}",method = RequestMethod.GET)
	public String list(@PathVariable(value = "typeId") Long typeId,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE_3) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<Equipment> equipments = equipmentService.getTypeEquipment(typeId,searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("equipments", equipments);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		model.addAttribute("typeId", typeId);
		return "experiment/equipment-list";
	}

	/**
	 * 进入实验设备创建页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(@RequestParam(value = "typeId") Long typeId,Model model) {
		Equipment equipment = new Equipment();
		EquipmentType equipmentType= new EquipmentType();
		equipmentType.setId(typeId);
		equipment.setEquipmentType(equipmentType);
		model.addAttribute("equipment", equipment);
		model.addAttribute("action", "create");
		return "experiment/equipment-form";
	}

	/**
	 * 创建实验设备.
	 * @param newEquipment
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Equipment newEquipment, 
			@RequestParam(value="picture", required=false) MultipartFile picture,
			RedirectAttributes redirectAttributes) {
		try{
		newEquipment.setCreateTime(new Date());
		newEquipment.setUpdateTime(new Date());
		equipmentService.saveEquipment(newEquipment,picture);
		}catch(Exception e){
			e.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", "创建实验设备成功");
		return "redirect:/equipment/"+newEquipment.getEquipmentType().getId();
	}

	/**
	 * 进入实验设备更新页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		model.addAttribute("equipment", equipmentService.getEquipment(id));
		model.addAttribute("action", "update");
		return "experiment/equipment-form";
	}

	/**
	 * 更新实验设备.
	 * @param equipment
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("equipment") Equipment equipment, 
			@RequestParam(value="picture", required=false) MultipartFile picture, 
			RedirectAttributes redirectAttributes) {
		try{
		equipment.setUpdateTime(new Date());
		equipmentService.saveEquipment(equipment,picture);
		}catch(Exception e){
			e.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", "更新实验设备成功");
		return "redirect:/equipment/"+equipment.getEquipmentType().getId();
	}

	/**
	 * 删除实验设备.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		Equipment equipment = equipmentService.getEquipment(id);
		equipmentService.deleteEquipment(id);
		redirectAttributes.addFlashAttribute("message", "删除实验设备成功");
		return "redirect:/equipment/"+equipment.getEquipmentType().getId();
	}
	
	
	/**
	 * JSon返回设备列表.
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "list/{id}")
	@ResponseBody
	public List<Equipment> jsonList(@PathVariable("id") Long id){
		return equipmentService.getTypeEquipments(id);
	}
	
	/**
	 * JSon返回设备信息.
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "show/{code}")
	@ResponseBody
	public Equipment show(@PathVariable("code") String code){
		Equipment equipment = equipmentService.getEquipment(code);
		if(equipment!=null){
			return equipment;
		}else{
			return null;
		}
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出Equipment对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getEquipment(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("equipment", equipmentService.getEquipment(id));
		}
	}

}
