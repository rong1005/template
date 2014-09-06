package com.cn.template.web.controller.project;

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

import com.cn.template.entity.authority.User;
import com.cn.template.entity.project.Project;
import com.cn.template.service.project.ProjectService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.Utils;
import com.cn.template.xutil.enums.Whether;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Maps;

/**
 * 项目管理的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/project")
public class ProjectController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "标题");
	}

	/** 项目管理的业务逻辑 */
	@Autowired
	private ProjectService projectService;

	/**
	 * 项目列表.
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
		Long userId = Utils.getCurrentUserId();

		Page<Project> projects = projectService.getUserProject(userId, searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("projects", projects);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "project/project-list";
	}

	/**
	 * 进入项目创建页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("project", new Project());
		model.addAttribute("action", "create");
		return "project/project-form";
	}

	/**
	 * 创建项目.
	 * @param newProject
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Project newProject, RedirectAttributes redirectAttributes) {
		User user = new User(Utils.getCurrentUserId());
		newProject.setDirector(user);
		newProject.setIsPlaceOnFile(Whether.NOT);
		newProject.setTotalTask(0);
		newProject.setFinishTask(0);
		newProject.setPercent(0.0);
		newProject.setCreateTime(new Date());
		newProject.setUpdateTime(new Date());
		projectService.saveProject(newProject);
		redirectAttributes.addFlashAttribute("message", "创建项目成功");
		return "redirect:/project/";
	}

	/**
	 * 进入项目更新页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		model.addAttribute("project", projectService.getProject(id));
		model.addAttribute("action", "update");
		return "project/project-form";
	}

	/**
	 * 更新项目.
	 * @param project
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("project") Project project, RedirectAttributes redirectAttributes) {
		projectService.saveProject(project);
		redirectAttributes.addFlashAttribute("message", "更新项目成功");
		return "redirect:/project/";
	}

	/**
	 * 删除项目.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		projectService.deleteProject(id);
		redirectAttributes.addFlashAttribute("message", "删除项目成功");
		return "redirect:/project/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出Project对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getProject(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("project", projectService.getProject(id));
		}
	}

}
