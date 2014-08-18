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
import com.cn.template.service.authority.UserService;
import com.cn.template.service.experiment.ApplyService;
import com.cn.template.service.experiment.PriceService;
import com.cn.template.service.experiment.ScheduleService;
import com.cn.template.service.form.FieldService;
import com.cn.template.service.form.FormService;
import com.cn.template.service.form.NodeService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.Utils;
import com.cn.template.xutil.enums.ApplyStatus;
import com.cn.template.xutil.enums.Whether;
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
	
	/** 实验排期信息的业务处理类. */
	@Autowired
	private ScheduleService scheduleService;
	
	/** 用户管理的业务逻辑 */
	@Autowired
	private UserService userService;
	
	/** 表单信息的业务处理 */
	@Autowired
	private FormService formService;
	
	
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
		if(apply.getReceiptTime()==null){
			apply.setReceiptTime(new Date());
		}
		
		model.addAttribute("loginUser", Utils.getCurrentUserId());
		model.addAttribute("users", userService.getAllUser());
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
	 * 查看实验信息.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "browse/{id}")
	public String browse(@PathVariable("id") Long id, Model model){
		Apply apply = applyService.getApply(id);
		model.addAttribute("nodeMap",nodeService.nodeMap(ApplyStatus.BROWSE, apply.getForm().getId()));
		model.addAttribute("apply", apply);
		model.addAttribute("customField", applyService.getApplyCustomField(apply));
		//排期信息
		model.addAttribute("schedules", scheduleService.findApplySchedule(id));
		return "experiment/apply-browse";
	}
	
	/**
	 * 补充实验信息.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "replenish/{id}", method = RequestMethod.GET)
	public String replenish(@PathVariable("id") Long id, Model model){
		Apply apply = applyService.getApply(id);
		model.addAttribute("nodeMap",nodeService.nodeMap(ApplyStatus.BE_IN_PROGRESS, apply.getForm().getId()));
		model.addAttribute("apply", apply);
		model.addAttribute("customField", applyService.getApplyCustomField(apply));
		//排期信息
		model.addAttribute("schedules", scheduleService.findApplySchedule(id));
		model.addAttribute("action", "replenish");
		return "experiment/apply-replenish";
	}
	
	/**
	 * 审核委托申请.
	 * @param apply
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "replenish", method = RequestMethod.POST)
	public String replenish(@Valid @ModelAttribute("apply") Apply apply, ServletRequest request, RedirectAttributes redirectAttributes) {
		apply.setUpdateTime(new Date());
		apply.setApplyStatus(ApplyStatus.BE_IN_PROGRESS);
		applyService.updateApply(apply,request);
		redirectAttributes.addFlashAttribute("message", "实验资料补充提交成功!");
		return "redirect:/apply/";
		
	}
	
	/**
	 * 实验委托申请界面.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "entrust")
	public String entrust(Model model){
		//取得所有的委托申请表单
		model.addAttribute("forms",formService.getAllForm());
		return "experiment/experiment-entrust";
	}
	
	/**
	 * 实验委托信息查询.
	 * @param applyCode
	 * @param experimentCode
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "search")
	public String search(@RequestParam(value = "applyCode",defaultValue="") String applyCode,
			@RequestParam(value = "experimentCode",defaultValue="") String experimentCode,
			RedirectAttributes redirectAttributes){
		Apply apply = applyService.getApply(applyCode,experimentCode);
		if(apply==null){
			redirectAttributes.addFlashAttribute("message", "无法找到匹配的委托信息，请确认您输入的编号是否正确!");
			redirectAttributes.addFlashAttribute("applyCode", applyCode);
			redirectAttributes.addFlashAttribute("experimentCode", experimentCode);
			return "redirect:/apply/entrust";
		}
		if(apply.getIsPass()!=null&&apply.getIsPass().equals(Whether.YES)){
			return "redirect:/apply/browse/"+apply.getId();
		}
		return "redirect:/apply/update/"+apply.getId();
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出Form对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getApply(@RequestParam(value = "id", defaultValue = "-1") Long id,@RequestParam(value = "userid", defaultValue = "-1") Long userid, Model model) {
		if (id != -1) {
			Apply apply=applyService.getApply(id);
			if(userid!=-1){
				apply.setUser(userService.getUser(userid));
			}
			model.addAttribute("apply", apply);
		}
	}
	

}
