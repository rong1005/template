package com.cn.template.web.controller.experiment;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.cn.template.entity.experiment.Apply;
import com.cn.template.service.experiment.ApplyService;
import com.cn.template.service.experiment.PriceService;
import com.cn.template.service.form.FieldService;
import com.cn.template.service.form.NodeService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.enums.ApplyStatus;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Maps;

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
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "标题");
	}
	
	/** 字段信息的业务处理 */
	@Autowired
	private FieldService fieldService;
	
	/** 实验委托申请管理的业务逻辑. */
	@Autowired
	private ApplyService applyService;
	
	/** 节点权限信息的业务处理 */
	@Autowired
	private NodeService nodeService;
	
	/** 收费管理的业务逻辑. */
	@Autowired
	private PriceService priceService;
	
	
	/**
	 * 委托申请列表.
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
		Page<Apply> applys = applyService.getApply(searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("applys", applys);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "experiment/apply-list";
	}
	
	
	/**
	 * 进入委托申请页面.
	 * @param formId
	 * @param model
	 */
	@RequestMapping(value="create/{formId}",method = RequestMethod.GET)
	public String create(@PathVariable(value = "formId") Long formId,Model model){
		model.addAttribute("nodeMap",nodeService.nodeMap(ApplyStatus.REQUEST, formId));
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
	public String create(@Valid Apply newApply,ServletRequest request,RedirectAttributes redirectAttributes) {
		logger.info("提交实验委托申请");
		try{
			applyService.saveApply(newApply,request);
		}catch(Exception e){
			e.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", "创建委托申请成功");
		return "redirect:/apply/";
	}
	
	/**
	 * 进入委托申请更新页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		Apply apply = applyService.getApply(id);
		
		model.addAttribute("nodeMap",nodeService.nodeMap(ApplyStatus.REQUEST, apply.getForm().getId()));
		model.addAttribute("apply", apply);
		logger.info(applyService.getApplyCustomField(apply).toString());
		model.addAttribute("customField", applyService.getApplyCustomField(apply));
		model.addAttribute("action", "update");
		return "experiment/apply-update";
	}

	/**
	 * 更新委托申请.
	 * @param apply
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("apply") Apply apply, ServletRequest request, RedirectAttributes redirectAttributes) {
		apply.setUpdateTime(new Date());
		apply.setApplyStatus(ApplyStatus.REQUEST);
		applyService.updateApply(apply,request);
		redirectAttributes.addFlashAttribute("message", "更新委托申请成功");
		return "redirect:/apply/";
	}
	
	
	/**
	 * 进入委托申请审核页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "audit/{id}", method = RequestMethod.GET)
	public String audit(@PathVariable("id") Long id, Model model) {
		Apply apply = applyService.getApply(id);
		
		model.addAttribute("prices", priceService.getAllPrice());
		model.addAttribute("nodeMap",nodeService.nodeMap(ApplyStatus.AUDITING, apply.getForm().getId()));
		model.addAttribute("apply", apply);
		logger.info(applyService.getApplyCustomField(apply).toString());
		model.addAttribute("customField", applyService.getApplyCustomField(apply));
		model.addAttribute("action", "audit");
		return "experiment/apply-audit";
	}

	/**
	 * 审核委托申请.
	 * @param apply
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "audit", method = RequestMethod.POST)
	public String audit(@Valid @ModelAttribute("apply") Apply apply, ServletRequest request, RedirectAttributes redirectAttributes) {
		apply.setUpdateTime(new Date());
		apply.setApplyStatus(ApplyStatus.AUDITING);
		applyService.updateApply(apply,request);
		redirectAttributes.addFlashAttribute("message", "委托申请审核 '"+apply.getIsPass().getValue()+"' 通过");
		return "redirect:/apply/";
	}
	

	/**
	 * 删除委托申请.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		applyService.deleteApply(id);
		redirectAttributes.addFlashAttribute("message", "删除委托申请成功");
		return "redirect:/apply/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出Form对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getApply(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("apply", applyService.getApply(id));
		}
	}
	

}
