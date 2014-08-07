package com.cn.template.repository.experiment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.experiment.SampleDetail;

/**
 * 实验样品明细信息的数据访问接口.
 * @author Libra
 *
 */
public interface SampleDetailDao extends PagingAndSortingRepository<SampleDetail, Long>, JpaSpecificationExecutor<SampleDetail> {

}
