package com.cn.template.repository.authority;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.authority.Role;

/**
 * 角色信息的数据访问接口.
 * @author Libra
 *
 */
public interface RoleDao extends PagingAndSortingRepository<Role, Long>, JpaSpecificationExecutor<Role> {

}
