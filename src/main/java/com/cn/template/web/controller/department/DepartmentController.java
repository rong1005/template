package com.cn.template.web.controller.department;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.template.entity.Department;
import com.cn.template.service.department.DepartmentService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.enums.DepartmentType;
import com.google.common.collect.Maps;

/**
 * 部门管理的业务代理.
 * 
 * @author Libra
 */
@Controller
@RequestMapping(value = "/department")
public class DepartmentController {

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "名称");
	}

	/** 部门管理的业务逻辑 */
	@Autowired
	private DepartmentService departmentService;

	/**
	 * 转跳到部门列表查看页面.
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model) {
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		return "department/department-list";
	}
	
	/**
	 * 部门信息列表，返回JSON.(分页、查询)
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param name
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Page<Department> list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE_10) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "nodeid",required=false) Long nodeid,
			@RequestParam(value = "name",required=false) String name){
		
		return departmentService.getDepartment(nodeid,name, pageNumber, pageSize, sortType);
	}

	/**
	 * 进入部门创建页面.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		//取得所有的部门信息
		model.addAttribute("departmentList", departmentService.getAllDepartment());
		model.addAttribute("department", new Department());
		model.addAttribute("action", "create");
		return "department/department-form";
	}

	/**
	 * 创建部门.
	 * @param newDepartment
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Department newDepartment, RedirectAttributes redirectAttributes) {
		try{
		departmentService.saveDepartment(newDepartment);
		}catch (Exception e){
			e.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", "创建部门成功");
		return "redirect:/department/";
	}

	/**
	 * 进入部门更新页面.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		model.addAttribute("departmentList", departmentService.getAllDepartment());
		model.addAttribute("department", departmentService.getDepartment(id));
		model.addAttribute("action", "update");
		return "department/department-form";
	}

	/**
	 * 更新部门.
	 * @param department
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("department") Department department, RedirectAttributes redirectAttributes) {
		departmentService.saveDepartment(department);
		redirectAttributes.addFlashAttribute("message", "更新部门成功");
		return "redirect:/department/";
	}

	/**
	 * 删除部门.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		departmentService.deleteDepartment(id);
		redirectAttributes.addFlashAttribute("message", "删除部门成功");
		return "redirect:/department/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现预处理部分绑定的效果,先根据form的id从数据库查出Department对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getDepartment(@RequestParam(value = "id", defaultValue = "-1") Long id,@RequestParam(value = "higherDepartment.id", defaultValue = "0") Long hid, Model model) {
		if (id != -1) {
			Department department=departmentService.getDepartment(id);
			if(hid > 0){
				department.setHigherDepartment(departmentService.getDepartment(hid));
			}else{
				department.setHigherDepartment(null);
			}
			model.addAttribute("department", department);
		}
	}
	
	
	/**
	 * 取得所有部门信息，组合树JSON格式返回.
	 * @return
	 */
	@RequestMapping(value="tree")
	@ResponseBody
	public String tree(){

		StringBuffer sb=new StringBuffer();
		sb.append("[");
		for(Department department : departmentService.findDepartment(null)){
			childTree(sb,department);
		}
		sb.append("]");
		return sb.toString().replace(",]", "]");
	}
	
	/**
	 * 取得所有集团、分公司信息，组合树JSON格式返回（异步）.
	 * @return
	 */
	@RequestMapping(value="asyn_tree")
	@ResponseBody
	public String asynTree(){
		StringBuffer sb=new StringBuffer();
		sb.append("[");
		for(Department department : departmentService.findDepartment(null,DepartmentType.GROUP)){
			asynChildTree(sb,department);
		}
		sb.append("]");
		return sb.toString().replace(",]", "]");
	}
	
	/**
	 * 
	 * @param nodeid
	 * @return
	 */
	@RequestMapping(value="asyn_children")
	@ResponseBody
	public String asynchildren(@RequestParam(value = "nodeid") Long nodeid){
		List<Department> departments = departmentService.findDepartment(new Department(nodeid));
		StringBuffer sb=new StringBuffer();
		sb.append("[");
		if(!departments.isEmpty()){
			for(Department department : departments){
				sb.append("{ \"id\":"+department.getId()+",\"name\":\""+department.getName()+"\"");
				if(!department.getDepartments().isEmpty()){
					sb.append(",\"hasChild\":\"true\"");
				}
				sb.append("},");	
			}
		}
		sb.append("]");
		
		return sb.toString().replace(",]", "]");
	}
	
	/**
	 * 递归获取部门的直属下级(组合JSON格式的部门树).
	 * @param sb
	 * @param department
	 * @return
	 */
	private void childTree(StringBuffer sb,Department department ){
		sb.append("{ \"id\":"+department.getId()+",\"name\":\""+department.getName()+"\"");
		List<Department> list = departmentService.findDepartment(department);
		if(!list.isEmpty()){
			sb.append(",\"children\":[");
			for(Department child : list){
				childTree(sb,child);
			}
			sb.append("]");
		}
		sb.append("},");
	}
	
	/**
	 * 递归获取部门的直属下级(组合JSON格式的部门树)（异步）.
	 * @param sb
	 * @param department
	 * @return
	 */
	private void asynChildTree(StringBuffer sb,Department department ){
		sb.append("{ \"id\":"+department.getId()+",\"name\":\""+department.getName()+"\"");
		if(!department.getDepartments().isEmpty()&&department.getDepartmentType().equals(DepartmentType.COMPANY)){
			sb.append(",\"hasChild\":\"true\"");
		}
		List<Department> list = departmentService.findDepartment(department,DepartmentType.COMPANY);
		if(!list.isEmpty()){
			sb.append(",\"children\":[");
			for(Department child : list){
				asynChildTree(sb,child);
			}
			sb.append("]");
		}
		sb.append("},");
	}

}
