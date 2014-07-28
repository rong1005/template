package com.cn.template.web.controller.experiment;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.template.entity.experiment.Apply;
import com.cn.template.entity.form.Form;
import com.cn.template.service.experiment.ApplyService;
import com.cn.template.service.form.FieldService;

/**
 * 实验委托申请信息管理的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/apply")
public class ApplyController {
	
	/** 日志信息 */
	private static final Logger logger = LoggerFactory.getLogger(ApplyController.class);
	
	/** 字段信息的业务处理 */
	@Autowired
	private FieldService fieldService;
	
	/** 实验委托申请管理的业务逻辑. */
	@Autowired
	private ApplyService applyService;
	
	
	/**
	 * 进入委托申请页面.
	 * @param formId
	 * @param model
	 */
	@RequestMapping(value="create/{formId}",method = RequestMethod.GET)
	public String apply(@PathVariable(value = "formId") Long formId,Model model){
		model.addAttribute("fields", fieldService.getAllField(formId));
		model.addAttribute("formId", formId);
		model.addAttribute("apply", new Apply());
		model.addAttribute("action", "create");
		return "experiment/apply-create";
	}
	
	/**
	 * 创建委托申请.
	 * @param newForm
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String apply(@Valid Apply newApply,ServletRequest request,RedirectAttributes redirectAttributes) {
		logger.info("提交实验委托申请");
		try{
			applyService.saveApply(newApply,request);
		}catch(Exception e){
			e.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", "创建委托申请成功");
		return "redirect:/form/";
	}

}
