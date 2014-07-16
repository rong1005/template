package com.cn.template.repository.authority;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.authority.User;

/**
 * 用户信息的数据访问接口.
 * @author Libra
 *
 */
public interface UserDao extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {
	
	/**
	 * 登录,通过用户名取得用户信息.
	 * @param loginName
	 * @return
	 */
	User findByLoginName(String loginName);
}
