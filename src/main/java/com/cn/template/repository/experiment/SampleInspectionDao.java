package com.cn.template.repository.experiment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.experiment.SampleInspection;

/**
 * 实验样品巡检信息的数据访问接口.
 * @author Libra
 *
 */
public interface SampleInspectionDao extends PagingAndSortingRepository<SampleInspection, Long>, JpaSpecificationExecutor<SampleInspection> {

	/**
	 * 取得巡检记录对应的样品巡检信息.
	 * @param id
	 * @return
	 */
	public List<SampleInspection> findByInspectionRecord_Id(Long id);
	
	/**
	 * 取得样品巡检信息.
	 * @param id
	 * @return
	 */
	public List<SampleInspection> findBySchedule_Sample_Id(Long id);
	
}
