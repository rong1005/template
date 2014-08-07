package com.cn.template.entity.experiment;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;
import com.cn.template.entity.authority.User;

/**
 * 实验样品明细信息.
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_sample_detail")
public class SampleDetail extends IdEntity {
	
	/** 样品 */
	private Sample sample;
	
	/** 操作人 */
	private User user;
	
	/** 内容 */
	private String content;

	@ManyToOne
	@JoinColumn(name="sample_id")
	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
