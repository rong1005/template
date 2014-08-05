package com.cn.template.repository.experiment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.experiment.Schedule;

/**
 * 实验排期的数据访问接口.
 * @author Libra
 *
 */
public interface ScheduleDao extends PagingAndSortingRepository<Schedule, Long>, JpaSpecificationExecutor<Schedule> {

	/**
	 * 获得实验的排期信息.
	 * @param applyId
	 * @return
	 */
	public List<Schedule> findBySample_Apply_Id(Long applyId);
	
	/**
	 * 获得设备样品对应的排期信息.
	 * @param equipmentId
	 * @param serialNumber
	 * @return
	 */
	public Schedule findByEquipment_IdAndSample_SerialNumber(Long equipmentId,String serialNumber);
}
