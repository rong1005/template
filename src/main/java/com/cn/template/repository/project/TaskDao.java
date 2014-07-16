package com.cn.template.repository.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.project.Task;

/**
 * 任务管理的数据访问接口.
 * @author Libra
 *
 */
public interface TaskDao extends PagingAndSortingRepository<Task, Long>, JpaSpecificationExecutor<Task> {

	/**
	 * 获得用户的任务记录(分页).
	 * @param id
	 * @param pageRequest 
	 * @return
	 */
	Page<Task> findByUserId(Long id, Pageable pageRequest);

	/**
	 * 删除用户下的所有任务.
	 * @param id
	 */
	@Modifying
	@Query("delete from Task task where task.user.id=?1")
	void deleteByUserId(Long id);
}
