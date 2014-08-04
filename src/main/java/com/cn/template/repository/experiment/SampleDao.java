package com.cn.template.repository.experiment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.experiment.Sample;

/**
 * 实验样品信息的数据访问接口.
 * @author Libra
 *
 */
public interface SampleDao extends PagingAndSortingRepository<Sample, Long>, JpaSpecificationExecutor<Sample> {

	/**
	 * 取得申请下的所有样品信息.
	 * @param applyId
	 * @return
	 */
	public List<Sample> findByApply_Id(Long applyId);
}
