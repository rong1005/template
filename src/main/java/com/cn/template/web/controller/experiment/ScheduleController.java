package com.cn.template.web.controller.experiment;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.template.entity.experiment.Apply;
import com.cn.template.service.experiment.ApplyService;
import com.cn.template.service.experiment.EquipmentTypeService;
import com.cn.template.service.experiment.ScheduleService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.Utils;
import com.google.common.collect.Maps;

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
	public String add(@PathVariable(value = "applyId") Long applyId,Model model){
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
	public String add(@PathVariable(value = "applyId") Long applyId,ServletRequest request,RedirectAttributes redirectAttributes){
		try{
		scheduleService.saveSchedule(applyId,request);
		}catch(Exception e){
			e.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", "已添加实验排期信息.");
		return "redirect:/apply/";
	}
	
	/**
	 * 计算返回结束时间
	 * @param datetime
	 * @param hour
	 * @return
	 */
	@RequestMapping(value="/count/time")
	@ResponseBody
	public Map<String,String> countTime(@RequestParam(value = "datetime") String datetime,@RequestParam(value = "hour") Integer hour ){
		Date date=Utils.parseDate(datetime);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);
		Map<String,String> map= Maps.newHashMap();
		map.put("endTime", Utils.datef(calendar.getTime(), Constants.DATETIME_MIN_FORMAT));
		return map;
	}
	
}
