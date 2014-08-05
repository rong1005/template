package com.cn.template.web.controller.experiment;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cn.template.service.experiment.ScheduleService;


/**
 * 实验管理的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/experiment")
public class ExperimentController {

	/** 实验排期信息的业务处理类 */
	@Autowired
	private ScheduleService scheduleService;
	
	/**
	 * 进入实验开始界面.
	 * @return
	 */
	@RequestMapping(value="start",method = RequestMethod.GET)
	public String start(){
		
		return "experiment/experiment-start";
	}
	
	
	/**
	 * 实验开始.
	 * @return
	 */
	@RequestMapping(value="start",method = RequestMethod.POST)
	public String start(ServletRequest request,Model model){
		try{
			scheduleService.experimentStart(request);
			model.addAttribute("message", "实验开始");
		}catch(Exception e){
			model.addAttribute("message", "实验提交失败，请重新执行.");
		}
		return "experiment/experiment-start";
	}
	
	
	/**
	 * 进入实验结束界面.
	 * @return
	 */
	@RequestMapping(value="end",method = RequestMethod.GET)
	public String end(){
		
		return "experiment/experiment-end";
	}
	
	
	/**
	 * 实验结束.
	 * @return
	 */
	@RequestMapping(value="end",method = RequestMethod.POST)
	public String end(ServletRequest request,Model model){
		try{
			scheduleService.experimentEnd(request);
			model.addAttribute("message", "实验结束");
		}catch(Exception e){
			model.addAttribute("message", "实验提交失败，请重新执行.");
		}
		return "experiment/experiment-start";
	}
	
}
