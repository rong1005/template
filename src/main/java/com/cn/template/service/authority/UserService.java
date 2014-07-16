package com.cn.template.service.authority;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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

import com.cn.template.entity.authority.User;
import com.cn.template.repository.authority.UserDao;
import com.cn.template.repository.project.TaskDao;
import com.cn.template.service.ServiceException;
import com.cn.template.web.shiro.ShiroUser;
import com.cn.template.xutil.Clock;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.Encodes;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;
import com.cn.template.xutil.security.Digests;

/**
 * 用户管理的业务逻辑.
 * @author Libra
 *
 */
@Component
@Transactional
public class UserService {
	/** 日志 */
	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	/** 用户信息的数据访问接口 */
	private UserDao userDao;
	
	/** 任务管理的数据访问接口 */
	private TaskDao taskDao;
	
	/** 提供当前时间 */
	private Clock clock = Clock.DEFAULT;
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	/**
	 * 取得所有的用户信息.
	 * @return
	 */
	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}

	/**
	 * 通过ID取得用户信息.
	 * @param id
	 * @return
	 */
	public User getUser(Long id) {
		return userDao.findOne(id);
	}

	/**
	 * 登录,通过用户名取得用户信息.
	 * @param loginName
	 * @return
	 */
	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}

	/**
	 * 用户注册.
	 * @param user
	 */
	public void registerUser(User user) {
		entryptPassword(user);
//		user.setRoles("user");
		user.setCreateTime(clock.getCurrentDate());
		user.setUpdateTime(clock.getCurrentDate());
		user.setRegisterDate(clock.getCurrentDate());
		userDao.save(user);
	}

	/**
	 * 更新用户信息.
	 * @param user
	 */
	public void updateUser(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		userDao.save(user);
	}

	/**
	 * 删除用户信息.
	 * @param id
	 */
	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
		taskDao.deleteByUserId(id);
	}

	/**
	 * 判断是否超级管理员.
	 * @param id
	 * @return
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 * @return
	 */
	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash.
	 * @param user
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(Constants.SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, Constants.HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 将传入的密码进行指定的盐值加密，返回加密后的字符串，主要用于判断两个密码是否相同.
	 * @param salt
	 * @param password
	 * @return
	 */
	public String entryptPassword(String salt,String password){
		byte[] hashPassword = Digests.sha1(password.getBytes(), Encodes.decodeHex(salt), Constants.HASH_INTERATIONS);
		return Encodes.encodeHex(hashPassword);
	}

	/**
	 * 获取用户信息[查询、分页、排序].
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<User> getUser(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<User> spec = buildSpecification(searchParams);
		return userDao.findAll(spec, pageRequest);
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
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 * @param userId
	 * @param searchParams
	 * @return
	 */
	private Specification<User> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<User> spec = DynamicSpecifications.bySearchFilter(filters.values(), User.class);
		return spec;
	}
	
}
