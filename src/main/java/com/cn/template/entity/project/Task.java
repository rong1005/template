package com.cn.template.entity.project;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.cn.template.entity.IdEntity;
import com.cn.template.entity.authority.User;

/**
 * 任务管理信息.
 * 
 * @author Libra
 * 
 */
@Entity
@Table(name = "tb_task")
public class Task extends IdEntity {

	/** 标题 */
	private String title;
	
	/** 描述 */
	private String description;
	
	/** 用户 */
	private User user;

	/** JSR303 BeanValidator的校验规则 */
	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/** JPA 基于USER_ID列的多对一关系定义 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
