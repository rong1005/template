package com.cn.template.web.controller.employee;

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

import com.cn.template.entity.Employee;
import com.cn.template.service.employee.EmployeeService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Maps;

/**
 * 员工管理的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "名称");
	}

	/** 员工管理的业务逻辑 */
	@Autowired
	private EmployeeService employeeService;

	/**
	 * 员工列表.
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE_3) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<Employee> employees = employeeService.getEmployee(searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("employees", employees);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "employee/employee-list";
	}

	/**
	 * 进入员工创建页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("action", "create");
		return "employee/employee-form";
	}

	/**
	 * 创建员工.
	 * @param newEmployee
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Employee newEmployee, RedirectAttributes redirectAttributes) {
		employeeService.saveEmployee(newEmployee);
		redirectAttributes.addFlashAttribute("message", "创建员工成功");
		return "redirect:/employee/";
	}

	/**
	 * 进入员工更新页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		model.addAttribute("employee", employeeService.getEmployee(id));
		model.addAttribute("action", "update");
		return "employee/employee-form";
	}

	/**
	 * 更新员工.
	 * @param employee
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("employee") Employee employee, RedirectAttributes redirectAttributes) {
		employeeService.saveEmployee(employee);
		redirectAttributes.addFlashAttribute("message", "更新员工成功");
		return "redirect:/employee/";
	}

	/**
	 * 删除员工.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		employeeService.deleteEmployee(id);
		redirectAttributes.addFlashAttribute("message", "删除员工成功");
		return "redirect:/employee/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出Employee对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getEmployee(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("employee", employeeService.getEmployee(id));
		}
	}

}
