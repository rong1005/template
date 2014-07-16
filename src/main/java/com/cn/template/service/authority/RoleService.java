package com.cn.template.service.authority;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.template.entity.authority.Role;
import com.cn.template.repository.authority.RoleDao;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;

/**
 * 角色管理的业务逻辑.
 * @author Libra
 *
 */
@Component
@Transactional
public class RoleService {
	/** 日志 */
	private static Logger logger = LoggerFactory.getLogger(RoleService.class);

	/** 角色信息的数据访问接口 */
	private RoleDao roleDao;
	
	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * 通过ID取得角色信息.
	 * @param id
	 * @return
	 */
	public Role getRole(Long id) {
		return roleDao.findOne(id);
	}
	
	/**
	 * 保存角色信息.
	 * @param entity
	 */
	public void saveRole(Role entity){
		roleDao.save(entity);
	}
	
	/**
	 * 删除单个角色记录.
	 * @param id
	 */
	public void deleteRole(Long id) {
		roleDao.delete(id);
	}
	
	/**
	 * 取得所有的角色信息.
	 * @return
	 */
	public List<Role> getAllRole() {
		return (List<Role>) roleDao.findAll();
	}

	/**
	 * 获取角色信息[查询、分页、排序].
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Role> getRole(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Role> spec = buildSpecification(searchParams);
		return roleDao.findAll(spec, pageRequest);
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
	 * @param roleId
	 * @param searchParams
	 * @return
	 */
	private Specification<Role> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Role> spec = DynamicSpecifications.bySearchFilter(filters.values(), Role.class);
		return spec;
	}
	
}
