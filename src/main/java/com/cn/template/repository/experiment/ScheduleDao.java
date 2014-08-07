package com.cn.template.repository.experiment;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.experiment.Equipment;
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
	
	/**
	 * 取得设备在指定时间内的排班记录.
	 * @param equipment
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Schedule> findByEquipmentAndRealStartTimeBeforeAndRealEndTimeAfter(Equipment equipment,Date startTime,Date endTime);
	
}
