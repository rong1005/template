package com.cn.template.service.department;

import java.util.Date;
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

import com.cn.template.entity.Department;
import com.cn.template.repository.DepartmentDao;
import com.cn.template.xutil.enums.DepartmentType;
import com.cn.template.xutil.enums.Operator;
import com.cn.template.xutil.enums.Status;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;
import com.google.common.collect.Maps;

/**
 * 部门管理的业务逻辑.
 * @author Libra
 *
 */
@Component  // Spring Bean的标识.
@Transactional // 类中所有public函数都纳入事务管理的标识.
public class DepartmentService {
	
	/** 部门管理的数据访问接口 */
	private DepartmentDao departmentDao;
	
	@Autowired
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	/**
	 * 根据ID获得部门记录.
	 * @param id
	 * @return
	 */
	public Department getDepartment(Long id) {
		return departmentDao.findOne(id);
	}

	/**
	 * 保存部门信息.
	 * @param entity
	 */
	public void saveDepartment(Department entity) {
		//默认状态为‘正常’
		entity.setStatus(Status.NORMAL);
		//默认显示顺序为0
		entity.setShowOrder(0);
		//如果子类不存在或者为空，则设置为null,否则会出现下面的Error
		//org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing
		if(entity.getHigherDepartment()!=null&&entity.getHigherDepartment().getId()==null){
			entity.setHigherDepartment(null);
		}
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		departmentDao.save(entity);
	}

	/**
	 * 单个删除部门记录.
	 * @param id
	 */
	public void deleteDepartment(Long id) {
		departmentDao.delete(id);
	}

	/**
	 * 获得所有的部门记录.
	 * @return
	 */
	public List<Department> getAllDepartment() {
		return (List<Department>) departmentDao.findAll();
	}
	
	/**
	 * 获得指定机构的直属子部门(如果指定机构为空，取得顶级部门).
	 * @param department
	 * @return
	 */
	public List<Department> findDepartment(Department department){
		if(department!=null){
			return departmentDao.findByHigherDepartment(department);
		}
		return departmentDao.findByHigherDepartmentNull();
	}
	
	/**
	 * 获得指定机构的直属子部门(如果指定机构为空，取得顶级部门).
	 * @param department
	 * @return
	 */
	public List<Department> findDepartment(Department department,DepartmentType departmentType){
		if(department!=null){
			return departmentDao.findByDepartmentTypeAndHigherDepartment(departmentType,department);
		}
		return departmentDao.findByDepartmentTypeAndHigherDepartmentNull(departmentType);
	}

	/**
	 * 获取部门记录[查询、分页、排序].
	 * @param userId
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Department> getDepartment(Long nodeid,String name, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Map<String, SearchFilter> filters = Maps.newHashMap();
		if(nodeid!=null){
			filters.put("higherDepartment.id", new SearchFilter("higherDepartment.id", Operator.EQ, nodeid));
		}
		if(name!=null){
			filters.put("name", new SearchFilter("name", Operator.LIKE, name));
		}
		Specification<Department> spec = DynamicSpecifications.bySearchFilter(filters.values(), Department.class);
		return departmentDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页/排序请求.
	 * @param pageNumber
	 * @param pagzSize
	 * @param sortType
	 * @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.ASC, "id");
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
	private Specification<Department> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = Maps.newHashMap();
		Specification<Department> spec = DynamicSpecifications.bySearchFilter(filters.values(), Department.class);
		return spec;
	}
	
}
