package com.cn.template.repository.project;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.project.Project;

/**
 * 项目管理的数据访问接口.
 * @author Libra
 *
 */
public interface ProjectDao extends PagingAndSortingRepository<Project, Long>, JpaSpecificationExecutor<Project> {

	
}
