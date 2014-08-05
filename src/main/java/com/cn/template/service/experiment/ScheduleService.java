package com.cn.template.service.experiment;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.template.entity.experiment.Sample;
import com.cn.template.entity.experiment.Schedule;
import com.cn.template.repository.experiment.ScheduleDao;
import com.cn.template.xutil.Utils;
import com.cn.template.xutil.enums.SampleStatus;

/**
 * 实验排期信息的业务处理类.
 * @author Libra
 *
 */
@Component  // Spring Bean的标识.
@Transactional // 类中所有public函数都纳入事务管理的标识.
public class ScheduleService {

	/** 实验排期的数据访问接口 */
	private ScheduleDao scheduleDao;

	@Autowired
	public void setScheduleDao(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	} 
	
	/** 实验样品的业务逻辑 */
	private SampleService sampleService;
	
	/** 实验设备的业务逻辑 */
	private EquipmentService equipmentService;
	
	@Autowired
	public void setSampleService(SampleService sampleService) {
		this.sampleService = sampleService;
	}

	@Autowired
	public void setEquipmentService(EquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}

	/**
	 * 保存排期信息.
	 * @param schedule
	 */
	public void saveSchedule(Schedule schedule){
		scheduleDao.save(schedule);
	}
	
	/**
	 * 保存排期信息.
	 * @param schedule
	 */
	public void saveSchedule(Long applyId,ServletRequest request){
		Map<String,String[]> map = request.getParameterMap();
		
		if(map.containsKey("equipmentId")&&map.containsKey("usedTime")&&map.containsKey("startTime")&&map.containsKey("endTime")&&map.containsKey("sampleId")){
			//清除现有的排期信息，重新添加.
			List<Schedule> scheduleList=findApplySchedule(applyId);
			if(scheduleList!=null&&!scheduleList.isEmpty()){
				deleteSchedule(scheduleList);
			}
			String[] equipmentId=map.get("equipmentId");
			String[] usedTime=map.get("usedTime");
			String[] startTime=map.get("startTime");
			String[] endTime=map.get("endTime");
			String[] sampleId=map.get("sampleId");
			
			for(int i=0;i<sampleId.length;i++){
				Sample sample = sampleService.getSample(Long.parseLong(sampleId[i]));
				sample.setStatus(SampleStatus.RESCHEDULING);
				sampleService.saveSample(sample);
				Schedule schedule = new Schedule();
				schedule.setCreateTime(new Date());
				schedule.setUpdateTime(new Date());
				schedule.setEquipment(equipmentService.getEquipment(Long.parseLong(equipmentId[i])));
				schedule.setSample(sample);
				schedule.setUsedTime(Integer.parseInt(usedTime[i]));
				schedule.setStartTime(Utils.parseDate(startTime[i]));
				schedule.setEndTime(Utils.parseDate(endTime[i]));
				scheduleDao.save(schedule);
			}
		}
		
	}
	
	/**
	 * 更新排期信息
	 * @param request
	 */
	public void experimentStart(ServletRequest request){
		Map<String, String[]> map = request.getParameterMap();
		if(map.containsKey("equipmentId")&&map.containsKey("sampleSerialNumber")){
			String[] equipmentId=map.get("equipmentId");
			String[] sampleSerialNumber=map.get("sampleSerialNumber");
			for(int i=0;i<equipmentId.length;i++){
				String[] serialNumbers = sampleSerialNumber[i].split(";");
				
				for(String serialNumber:serialNumbers){
					Schedule schedule = scheduleDao.findByEquipment_IdAndSample_SerialNumber(Long.parseLong(equipmentId[i]), serialNumber);
					Sample sample=schedule.getSample();
					sample.setStatus(SampleStatus.EXPERIMENT);
					sampleService.saveSample(sample);
					Date date = new Date();
					schedule.setUpdateTime(date);
					schedule.setRealStartTime(date);
					scheduleDao.save(schedule);
				}
			}
		}
		
	}
	
	
	/**
	 * 批量删除排期信息.
	 * @param entities
	 */
	public void deleteSchedule(List<Schedule> entities){
		scheduleDao.delete(entities);
	}
	
	/**
	 * 取得实验排期信息.
	 * @param applyId
	 * @return
	 */
	public List<Schedule> findApplySchedule(Long applyId){
		return scheduleDao.findBySample_Apply_Id(applyId);
	}
 
}
