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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.template.entity.experiment.Apply;
import com.cn.template.entity.experiment.Sample;
import com.cn.template.service.experiment.SampleService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.enums.SampleStatus;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Maps;

/**
 * 实验样品的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/sample")
public class SampleController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "标题");
	}

	/** 实验样品的业务逻辑 */
	@Autowired
	private SampleService sampleService;

	/**
	 * 实验样品列表.
	 * 
	 * @param applyId
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{applyId}",method = RequestMethod.GET)
	public String list(@PathVariable(value = "applyId") Long applyId,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE_10) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<Sample> samples = sampleService.getApplySample(applyId,searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("samples", samples);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		model.addAttribute("applyId", applyId);
		return "experiment/sample-list";
	}

	/**
	 * 进入实验样品创建页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(@RequestParam(value = "applyId") Long applyId,Model model) {
		Sample sample = new Sample();
		Apply apply=new Apply();
		apply.setId(applyId);
		sample.setApply(apply);
		model.addAttribute("sample", sample);
		model.addAttribute("action", "create");
		return "experiment/sample-form";
	}

	/**
	 * 创建实验样品.
	 * @param newSample
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Sample newSample, RedirectAttributes redirectAttributes) {
		newSample.setCreateTime(new Date());
		newSample.setUpdateTime(new Date());
		newSample.setStatus(SampleStatus.WAIT_RECEIVE);
		sampleService.saveSample(newSample);
		redirectAttributes.addFlashAttribute("message", "创建实验样品成功");
		return "redirect:/sample/"+newSample.getApply().getId();
	}

	/**
	 * 进入实验样品更新页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		model.addAttribute("sample", sampleService.getSample(id));
		model.addAttribute("action", "update");
		return "experiment/sample-form";
	}

	/**
	 * 更新实验样品.
	 * @param sample
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("sample") Sample sample, RedirectAttributes redirectAttributes) {
		sample.setUpdateTime(new Date());
		sampleService.saveSample(sample);
		redirectAttributes.addFlashAttribute("message", "更新实验样品成功");
		return "redirect:/sample/"+sample.getApply().getId();
	}

	/**
	 * 删除实验样品.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		Sample sample = sampleService.getSample(id);
		sampleService.deleteSample(id);
		redirectAttributes.addFlashAttribute("message", "删除实验样品成功");
		return "redirect:/sample/"+sample.getApply().getId();
	}
	
	
	/**
	 * 样品接收界面
	 * @return
	 */
	@RequestMapping(value = "receive/{applyId}", method = RequestMethod.GET)
	public String receive(@PathVariable(value = "applyId") Long applyId, Model model){
		model.addAttribute("applyId", applyId);
		return "experiment/sample-receive";
	}
	
	/**
	 * 样品接收.
	 * @param applyId
	 * @param samples
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "receive/{applyId}", method = RequestMethod.POST)
	public String receive(@PathVariable(value = "applyId") Long applyId,
			@RequestParam(value = "samples") String samples, 
			RedirectAttributes redirectAttributes){
		sampleService.receiveSample(samples);
		redirectAttributes.addFlashAttribute("message", "实验样品接收成功");
		return "redirect:/sample/"+applyId;
	}
	
	
	/**
	 * 样品处理界面
	 * @return
	 */
	@RequestMapping(value = "handle/{applyId}", method = RequestMethod.GET)
	public String handle(@PathVariable(value = "applyId") Long applyId, Model model){
		model.addAttribute("applyId", applyId);
		return "experiment/sample-handle";
	}
	
	/**
	 * 样品处理.
	 * @param applyId
	 * @param samples
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "handle/{applyId}", method = RequestMethod.POST)
	public String handle(@PathVariable(value = "applyId") Long applyId,
			@RequestParam(value = "samples") String samples, 
			@RequestParam(value = "status") SampleStatus status,
			RedirectAttributes redirectAttributes){
		sampleService.handleSample(samples, status);
		redirectAttributes.addFlashAttribute("message", "实验样品处理成功");
		return "redirect:/sample/"+applyId;
	}
	
	/**
	 * 样品的明细消息.
	 * @param sampleId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{sampleId}")
	public String detail(@PathVariable(value = "sampleId") Long sampleId, Model model){
		model.addAttribute("sampleDetails", sampleService.getSampleDetail(sampleId));
		return "experiment/sample-detail";
	}
	
	
	

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出Sample对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getSample(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("sample", sampleService.getSample(id));
		}
	}

}
