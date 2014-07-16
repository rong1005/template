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

import com.cn.template.entity.authority.Sidebar;
import com.cn.template.repository.authority.SidebarDao;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;

/**
 * 菜单管理的业务逻辑.
 * @author Libra
 *
 */
@Component
@Transactional
public class SidebarService {
	/** 日志 */
	private static Logger logger = LoggerFactory.getLogger(SidebarService.class);

	/** 菜单信息的数据访问接口 */
	private SidebarDao sidebarDao;
	
	@Autowired
	public void setSidebarDao(SidebarDao sidebarDao) {
		this.sidebarDao = sidebarDao;
	}
	
	/**
	 * 通过ID取得菜单信息.
	 * @param id
	 * @return
	 */
	public Sidebar getSidebar(Long id) {
		return sidebarDao.findOne(id);
	}
	
	/**
	 * 保存菜单信息.
	 * @param entity
	 */
	public void saveSidebar(Sidebar entity){
		sidebarDao.save(entity);
	}
	
	/**
	 * 删除单个菜单记录.
	 * @param id
	 */
	public void deleteSidebar(Long id) {
		sidebarDao.delete(id);
	}

	/**
	 * 取得所有的菜单信息.
	 * @return
	 */
	public List<Sidebar> getAllSidebar() {
		return (List<Sidebar>) sidebarDao.findAll();
	}
	
	/**
	 * 获得所有顶级菜单信息.
	 * @return
	 */
	public List<Sidebar> getTopSidebar(){
		return sidebarDao.findByPreSidebarIsNull();
	}

	/**
	 * 获取菜单信息[查询、分页、排序].
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Sidebar> getSidebar(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Sidebar> spec = buildSpecification(searchParams);
		return sidebarDao.findAll(spec, pageRequest);
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
	 * @param sidebarId
	 * @param searchParams
	 * @return
	 */
	private Specification<Sidebar> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Sidebar> spec = DynamicSpecifications.bySearchFilter(filters.values(), Sidebar.class);
		return spec;
	}
	
}
