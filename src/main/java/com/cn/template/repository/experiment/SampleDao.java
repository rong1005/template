package com.cn.template.repository.experiment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.experiment.Sample;

/**
 * 实验样品信息的数据访问接口.
 * @author Libra
 *
 */
public interface SampleDao extends PagingAndSortingRepository<Sample, Long>, JpaSpecificationExecutor<Sample> {

}
