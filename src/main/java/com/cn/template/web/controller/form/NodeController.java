package com.cn.template.web.controller.form;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.template.service.form.FieldService;
import com.cn.template.service.form.NodeService;
import com.cn.template.web.form.NodePermissionForm;
import com.cn.template.xutil.enums.ApplyStatus;


/**
 * 表单信息的业务代理.
 * @author Libra
 *
 */
@Controller
@RequestMapping(value = "/node")
public class NodeController {
	/** 日志信息 */
	private static final Logger logger=LoggerFactory.getLogger(NodeController.class);
	
	/** 节点权限信息的业务处理 */
	@Autowired
	private NodeService nodeService;
	
	/** 字段信息的业务处理. */
	@Autowired
	private FieldService fieldService;
	
	/**
	 * 进入节点管理页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String node(@RequestParam(value = "formId") Long formId,Model model) {
		logger.info("节点权限管理..");
		model.addAttribute("formId", formId);
		return "form/form-node";
	}
	
	/**
	 * 进入节点授权界面.
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="permission",method = RequestMethod.GET)
	public String permission(@RequestParam(value = "formId") Long formId,
			@RequestParam(value = "applyStatus") ApplyStatus applyStatus,Model model){
		logger.info("为表单 {} 的节点 {} 授权",formId,applyStatus.getValue());
		model.addAttribute("applyStatus", applyStatus);
		model.addAttribute("formId", formId);
		//取得所有的自定义字段信息.
		model.addAttribute("fields", fieldService.getAllField(formId));
		//取得节点对应的授权权限Map
		model.addAttribute("nodeMap", nodeService.nodeMap(applyStatus, formId));
		return "form/node-permission";
	}
	
	/**
	 * 保存节点授权.
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="permission",method = RequestMethod.POST)
	public String permission(@Valid NodePermissionForm nodePermissionForm, RedirectAttributes redirectAttributes){
		logger.info(nodePermissionForm.toString());
		nodeService.save(nodePermissionForm);
		redirectAttributes.addFlashAttribute("message", "授权设置成功");
		return "redirect:/node?formId="+nodePermissionForm.getFormId();
	}
}
