package com.cn.template.repository.experiment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.experiment.ExceptionHandle;
import com.cn.template.entity.experiment.InspectionRecord;

/**
 * 异常处理信息的数据访问接口.
 * @author Libra
 *
 */
public interface ExceptionHandleDao extends PagingAndSortingRepository<ExceptionHandle, Long>, JpaSpecificationExecutor<ExceptionHandle> {

	/**
	 * 取得巡检异常处理消息.
	 * @param inspectionRecord
	 * @return
	 */
	public List<ExceptionHandle> findByInspectionRecord(InspectionRecord inspectionRecord);
}
