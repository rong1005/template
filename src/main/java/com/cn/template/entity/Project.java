package com.cn.template.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cn.template.xutil.enums.Whether;

/**
 * 项目（工程）信息.
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_project")
public class Project extends IdEntity {

	/** 项目名称 */
	private String name;
	
	/** 项目介绍 */
	private String description;
	
	/** 总任务数 */
	private Integer totalTask;
	
	/** 已完成任务数 */
	private Integer finishTask;
	
	/** 项目进度 */
	private Double percents;
	
	/** 项目负责人 */
	private User director;
	
	/** 是否归档 */
	private Whether isPlaceOnFile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTotalTask() {
		return totalTask;
	}

	public void setTotalTask(Integer totalTask) {
		this.totalTask = totalTask;
	}

	public Integer getFinishTask() {
		return finishTask;
	}

	public void setFinishTask(Integer finishTask) {
		this.finishTask = finishTask;
	}

	public Double getPercent() {
		return percents;
	}

	public void setPercent(Double percent) {
		this.percents = percent;
	}

	/**
	 * JPA 基于DIRECTOR列的多对一关系定义(FK_USER)
	 * @return
	 */
	@ManyToOne
	@JoinColumn(name = "director")
	public User getDirector() {
		return director;
	}

	public void setDirector(User director) {
		this.director = director;
	}
	
	/**
	 * 以整形保存枚举值.
	 * @return
	 */
	@Enumerated(EnumType.ORDINAL)
	public Whether getIsPlaceOnFile() {
		return isPlaceOnFile;
	}

	public void setIsPlaceOnFile(Whether isPlaceOnFile) {
		this.isPlaceOnFile = isPlaceOnFile;
	}
	
}
