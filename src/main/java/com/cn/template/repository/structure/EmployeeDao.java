package com.cn.template.repository.structure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.structure.Employee;
import com.cn.template.xutil.enums.Whether;

/**
 * 用户信息的数据访问接口.
 * @author Libra
 *
 */
public interface EmployeeDao extends PagingAndSortingRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
	
	/**
	 * 获得标识下的员工信息.
	 * @param openid
	 * @return
	 */
	public Employee findByOpenid(String openid);
	
	/**
	 * 根据工号获得员工信息.
	 * @param openid
	 * @return
	 */
	public Employee findByCode(String code);
	
	/**
	 * 根据认证情况查询员工信息.
	 * @param whether
	 * @return
	 */
	public List<Employee> findByWhether(Whether whether);
	
}
