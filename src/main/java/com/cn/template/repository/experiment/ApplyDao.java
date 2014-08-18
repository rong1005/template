package com.cn.template.repository.experiment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.experiment.Apply;

/**
 * 实验委托请求信息的数据访问接口.
 * @author Libra
 *
 */
public interface ApplyDao extends PagingAndSortingRepository<Apply, Long>, JpaSpecificationExecutor<Apply> {

	/**
	 * 根据实验流水号取得实验申请信息.
	 * @param serialNumber
	 * @return
	 */
	public Apply findBySerialNumber(String serialNumber);
}
