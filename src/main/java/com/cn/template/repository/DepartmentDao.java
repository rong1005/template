package com.cn.template.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.Department;
import com.cn.template.xutil.enums.DepartmentType;

/**
 * 部门管理的数据访问接口.
 * @author Libra
 *
 */
public interface DepartmentDao extends PagingAndSortingRepository<Department, Long>,JpaSpecificationExecutor<Department> {
	
	/**
	 * 获得所有的顶级部门（上级部门为空）
	 * @return
	 */
	public List<Department> findByHigherDepartmentNull();
	
	/**
	 * 获得指定类型的所有顶级部门（上级部门为空）
	 * @param departmentType
	 * @return
	 */
	public List<Department> findByDepartmentTypeAndHigherDepartmentNull(DepartmentType departmentType);
	
	/**
	 * 获得指定机构的直属子部门.
	 * @param department
	 * @return
	 */
	public List<Department> findByHigherDepartment(Department department); 
	
	/**
	 * 获得指定机构的直属子部门.
	 * @param departmentType
	 * @param department
	 * @return
	 */
	public List<Department> findByDepartmentTypeAndHigherDepartment(DepartmentType departmentType,Department department); 
}
