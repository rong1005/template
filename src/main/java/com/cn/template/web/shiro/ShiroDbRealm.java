package com.cn.template.web.shiro;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.cn.template.entity.authority.Role;
import com.cn.template.entity.authority.Sidebar;
import com.cn.template.entity.authority.User;
import com.cn.template.service.authority.UserService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.Encodes;
import com.google.common.collect.Lists;

/**
 * Shiro的安全认证.
 * @author Libra
 *
 */
public class ShiroDbRealm extends AuthorizingRealm {

	/** 用户管理的业务逻辑 */
	protected UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 认证回调函数,登录时调用,判断用户是否登录成功.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		//取得用户输入的认证信息（用户名、密码）.
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userService.findUserByLoginName(token.getUsername());
		if (user != null) {
			byte[] salt = Encodes.decodeHex(user.getSalt());
			return new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getLoginName(), user.getName(),user.getPhoto()),
					user.getPassword(), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = userService.findUserByLoginName(shiroUser.loginName);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		//许可（资源）
		List<String> permissions=Lists.newArrayList();
		//角色
		List<String> roles=Lists.newArrayList();
		for(Role role : user.getRoles()){
			roles.add(role.getCode());
			for(Sidebar sidebar : role.getSidebars()){
				permissions.add(sidebar.getCode());
			}
		}
		info.addStringPermissions(permissions);
		info.addRoles(roles);
		return info;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Constants.HASH_ALGORITHM);
		matcher.setHashIterations(Constants.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}

}
