package com.cn.template.repository.experiment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.experiment.Price;

/**
 * 实验收费明细信息的数据访问接口.
 * @author Libra
 *
 */
public interface PriceDao extends PagingAndSortingRepository<Price, Long>, JpaSpecificationExecutor<Price> {

}
