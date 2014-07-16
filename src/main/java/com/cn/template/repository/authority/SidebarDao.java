package com.cn.template.repository.authority;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.authority.Sidebar;

/**
 * 侧边栏菜单信息的数据访问接口.
 * @author Libra
 *
 */
public interface SidebarDao extends PagingAndSortingRepository<Sidebar, Long>, JpaSpecificationExecutor<Sidebar> {
	
	/**
	 * 获得所有顶级菜单信息(上级菜单为空).
	 * @return
	 */
	public List<Sidebar> findByPreSidebarIsNull();
}
