package com.cn.template.repository.experiment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.experiment.ApplyPrice;

/**
 * 实验费用清单的数据访问接口.
 * @author Libra
 *
 */
public interface ApplyPriceDao extends PagingAndSortingRepository<ApplyPrice, Long>, JpaSpecificationExecutor<ApplyPrice> {

	/**
	 * 取得委托申请下的所有费用明细信息.
	 * @param id
	 * @return
	 */
	public List<ApplyPrice> findByApply_Id(Long id);
}
