package com.cn.template.repository.experiment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.experiment.InspectionRecord;

/**
 * 巡检记录的数据访问接口.
 * @author Libra
 *
 */
public interface InspectionRecordDao extends PagingAndSortingRepository<InspectionRecord, Long>, JpaSpecificationExecutor<InspectionRecord> {

}
