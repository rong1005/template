package com.cn.template.web.controller.experiment;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.template.entity.experiment.Apply;
import com.cn.template.service.experiment.ApplyService;
import com.cn.template.service.experiment.EquipmentTypeService;
import com.cn.template.service.experiment.ScheduleService;

/**
 * 实验排期的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/schedule")
public class ScheduleController {

	/** 实验排期信息的业务处理类. */
	@Autowired
	private ScheduleService scheduleService;
	
	/** 实验委托申请管理的业务逻辑 */
	@Autowired
	private ApplyService applyService;
	
	/** 实验设备类型的业务逻辑. */
	@Autowired
	private EquipmentTypeService equipmentTypeService;
	
	
	/**
	 * 进入添加实验排期
	 * @param applyId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add/{applyId}",method = RequestMethod.GET)
	private String add(@PathVariable(value = "applyId") Long applyId,Model model){
		Apply apply = applyService.getApply(applyId);
		model.addAttribute("apply", apply);
		model.addAttribute("customField", applyService.getApplyCustomField(apply));
		model.addAttribute("schedules", scheduleService.findApplySchedule(applyId));
		model.addAttribute("equipmentTypes", equipmentTypeService.getAllEquipmentType());
		return "experiment/schedule-add";
	}
	
	
	/**
	 * 保存实验排期信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add/{applyId}",method = RequestMethod.POST)
	private String add(@PathVariable(value = "applyId") Long applyId,ServletRequest request,RedirectAttributes redirectAttributes){
		try{
		scheduleService.saveSchedule(applyId,request);
		}catch(Exception e){
			e.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", "已添加实验排期信息.");
		return "redirect:/apply/";
	}
	
}
