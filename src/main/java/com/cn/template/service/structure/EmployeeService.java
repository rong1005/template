package com.cn.template.service.structure;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.template.entity.structure.Employee;
import com.cn.template.repository.structure.EmployeeDao;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;

/**
 * 员工管理的业务逻辑.
 * @author Libra
 *
 */
@Component  // Spring Bean的标识.
@Transactional // 类中所有public函数都纳入事务管理的标识.
public class EmployeeService {
	
	/** 员工管理的数据访问接口 */
	private EmployeeDao employeeDao;
	
	@Autowired
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	/**
	 * 根据ID获得员工记录.
	 * @param id
	 * @return
	 */
	public Employee getEmployee(Long id) {
		return employeeDao.findOne(id);
	}

	/**
	 * 保存员工信息.
	 * @param entity
	 */
	public void saveEmployee(Employee entity) {
		employeeDao.save(entity);
	}

	/**
	 * 单个删除员工记录.
	 * @param id
	 */
	public void deleteEmployee(Long id) {
		employeeDao.delete(id);
	}

	/**
	 * 获得所有的员工记录.
	 * @return
	 */
	public List<Employee> getAllEmployee() {
		return (List<Employee>) employeeDao.findAll();
	}
	
	/**
	 * 获得标识下的员工信息.
	 * @param openid
	 * @return
	 */
	public Employee findByOpenid(String openid){
		return employeeDao.findByOpenid(openid);
	}
	
	/**
	 * 根据工号获得员工信息.
	 * @param openid
	 * @return
	 */
	public Employee findByCode(String code){
		return employeeDao.findByCode(code);
	}

	/**
	 * 获取员工记录[查询、分页、排序].
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Employee> getEmployee( Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Employee> spec = buildSpecification(searchParams);

		return employeeDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 * @param pageNumber
	 * @param pagzSize
	 * @param sortType
	 * @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("name".equals(sortType)) {
			sort = new Sort(Direction.ASC, "name");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 * @param userId
	 * @param searchParams
	 * @return
	 */
	private Specification<Employee> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Employee> spec = DynamicSpecifications.bySearchFilter(filters.values(), Employee.class);
		return spec;
	}
	
}
