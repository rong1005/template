package com.cn.template.web.controller.experiment;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.template.entity.experiment.InspectionRecord;
import com.cn.template.service.experiment.EquipmentTypeService;
import com.cn.template.service.experiment.ExceptionHandleService;
import com.cn.template.service.experiment.InspectionRecordService;
import com.cn.template.service.experiment.ScheduleService;
import com.cn.template.web.form.ExceptionHandleForm;

/**
 * 异常处理的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/ehandle")
public class ExceptionHandleController {

	/** 异常处理的业务逻辑. */
	@Autowired
	private ExceptionHandleService exceptionHandleService;
	
	/** 巡检记录的业务逻辑 */
	@Autowired
	private InspectionRecordService inspectionRecordService;
	
	/** 实验排期信息的业务处理类 */
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private EquipmentTypeService equipmentTypeService;
	
	/**
	 * 进入异常处理页面.
	 * @param inspectionRecordId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{inspectionRecordId}",method=RequestMethod.GET)
	public String handle(@PathVariable(value = "inspectionRecordId") Long inspectionRecordId,Model model){
		InspectionRecord inspectionRecord = inspectionRecordService.getInspectionRecord(inspectionRecordId);
		model.addAttribute("inspectionRecord", inspectionRecord);
		//取得实验异常时的实验样品.
		model.addAttribute("schedules", scheduleService.findExceptionSchedule(inspectionRecord));
		model.addAttribute("equipmentTypes", equipmentTypeService.getAllEquipmentType());
		
		model.addAttribute("exceptionHandles", exceptionHandleService.getExceptionHandle(inspectionRecord));
		
		return "experiment/exception-handle";
	}
	
	/**
	 * 异常处理页面.
	 * @param inspectionRecordId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{inspectionRecordId}",method=RequestMethod.POST)
	public String handle(@PathVariable(value = "inspectionRecordId") Long inspectionRecordId,
			@Valid ExceptionHandleForm exceptionHandleForm,
			RedirectAttributes redirectAttributes){
		
		exceptionHandleService.saveExceptionHandle(inspectionRecordId, exceptionHandleForm);
		redirectAttributes.addFlashAttribute("message", "异常处理成功!");
		return "redirect:/inspection/";
	}
	
}
