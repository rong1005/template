package com.cn.template.repository.experiment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.experiment.ApplyRemark;

/**
 * 实验申请备注信息的数据访问接口.
 * @author Libra
 *
 */
public interface ApplyRemarkDao extends PagingAndSortingRepository<ApplyRemark, Long>, JpaSpecificationExecutor<ApplyRemark> {

}
