package com.cn.template.service.experiment;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.template.entity.authority.User;
import com.cn.template.entity.experiment.ExceptionHandle;
import com.cn.template.entity.experiment.InspectionRecord;
import com.cn.template.entity.experiment.Sample;
import com.cn.template.entity.experiment.SampleDetail;
import com.cn.template.entity.experiment.Schedule;
import com.cn.template.repository.experiment.ExceptionHandleDao;
import com.cn.template.repository.experiment.SampleDetailDao;
import com.cn.template.repository.experiment.ScheduleDao;
import com.cn.template.web.form.ExceptionHandleForm;
import com.cn.template.xutil.Utils;
import com.cn.template.xutil.enums.ExceptionHandleType;
import com.cn.template.xutil.enums.Whether;
import com.google.common.collect.Maps;


/**
 * 异常处理的业务逻辑.
 * @author Libra
 *
 */
@Component  // Spring Bean的标识.
@Transactional // 类中所有public函数都纳入事务管理的标识.
public class ExceptionHandleService {

	/** 异常处理信息的数据访问接口 */
	private ExceptionHandleDao exceptionHandleDao;
	
	/** 实验样品的业务逻辑 */
	private SampleDetailDao sampleDetailDao ;
	
	/** 实验排期的数据访问接口 */
	private ScheduleDao scheduleDao;
	
	/** 巡检记录的业务逻辑 */
	private InspectionRecordService inspectionRecordService;
	
	

	@Autowired
	public void setExceptionHandleDao(ExceptionHandleDao exceptionHandleDao) {
		this.exceptionHandleDao = exceptionHandleDao;
	}

	@Autowired
	public void setSampleDetailDao(SampleDetailDao sampleDetailDao) {
		this.sampleDetailDao = sampleDetailDao;
	}

	@Autowired
	public void setScheduleDao(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}

	@Autowired
	public void setInspectionRecordService(
			InspectionRecordService inspectionRecordService) {
		this.inspectionRecordService = inspectionRecordService;
	}

	/**
	 * 取得巡检异常处理消息.
	 * @param inspectionRecord
	 * @return
	 */
	public List<ExceptionHandle> getExceptionHandle(InspectionRecord inspectionRecord){
		List<ExceptionHandle> exceptionHandleList=exceptionHandleDao.findByInspectionRecord(inspectionRecord);
		if(exceptionHandleList!=null&&!exceptionHandleList.isEmpty()){
			return exceptionHandleList;
		}
		return null;
	}

	
	/**
	 * 保存异常处理信息.
	 * @param inspectionRecordId
	 * @param exceptionHandleForm
	 */
	public void saveExceptionHandle(Long inspectionRecordId,ExceptionHandleForm exceptionHandleForm){
		
		InspectionRecord inspectionRecord = inspectionRecordService.getInspectionRecord(inspectionRecordId);
		List<Schedule> scheduleList = scheduleDao.findByEquipmentAndRealStartTimeBeforeAndRealEndTimeAfter(inspectionRecord.getEquipment(), inspectionRecord.getCreateTime(), inspectionRecord.getCreateTime());
		if(scheduleList!=null&&!scheduleList.isEmpty()){
			for(Schedule schedule:scheduleList){
				ExceptionHandle exceptionHandle=new ExceptionHandle();
				exceptionHandle.setCreateTime(new Date());
				exceptionHandle.setUpdateTime(new Date());
				exceptionHandle.setUser(new User(Utils.getCurrentUserId()));
				exceptionHandle.setInspectionRecord(inspectionRecord);
				exceptionHandle.setSchedule(schedule);
				exceptionHandle.setExceptionHandleType(exceptionHandleForm.getExceptionHandleType());
				exceptionHandle.setDescription(exceptionHandleForm.getDescription());
				exceptionHandle.setStopTime(exceptionHandleForm.getStopTime());
				exceptionHandle.setRestratTime(exceptionHandleForm.getRestratTime());
				exceptionHandle.setEndTimeBefore(schedule.getRealEndTime());
				if(exceptionHandleForm.getExceptionHandleType().equals(ExceptionHandleType.SUSPEND)){
					Long timeDiffer = exceptionHandleForm.getRestratTime().getTime()-exceptionHandleForm.getStopTime().getTime();
					Date endTime=new Date(schedule.getRealEndTime().getTime()+timeDiffer);
					exceptionHandleForm.setEndTimeNow(endTime);
					schedule.setRealEndTime(endTime);
				}else{
					exceptionHandle.setEndTimeNow(exceptionHandleForm.getEndTimeNow());
				}
				exceptionHandle.setEquipmentBefore(schedule.getEquipment());
				exceptionHandle.setEquipmentNow(exceptionHandleForm.getEquipment());
				exceptionHandleDao.save(exceptionHandle);
				
				inspectionRecord.setIsHandle(Whether.YES);
				inspectionRecordService.saveInspectionRecord(inspectionRecord);
				
				if(exceptionHandleForm.getExceptionHandleType().equals(ExceptionHandleType.STOP)){
					schedule.setRealEndTime(exceptionHandleForm.getEndTimeNow());
				}
				
				if(exceptionHandleForm.getExceptionHandleType().equals(ExceptionHandleType.CHANGE)){
					schedule.setEquipment(exceptionHandleForm.getEquipment());
				}
				
				scheduleDao.save(schedule);
				
				SampleDetail sampleDetail = new SampleDetail();
				sampleDetail.setSample(schedule.getSample());
				sampleDetail.setUser(new User(Utils.getCurrentUserId()));
				sampleDetail.setCreateTime(new Date());
				sampleDetail.setUpdateTime(new Date());
				sampleDetail.setContent("异常处理-"+exceptionHandleForm.getExceptionHandleType().getValue());
				
				sampleDetailDao.save(sampleDetail);
			}
		}
	}
	
	/**
	 * 取得样品在巡检异常的处理信息.
	 * @param sample
	 * @return
	 */
	public Map<Long,ExceptionHandle> findExceptionHandleMap(Sample sample){
		Map<Long,ExceptionHandle> map=Maps.newHashMap();
		for(ExceptionHandle exceptionHandle:exceptionHandleDao.findBySchedule_Sample(sample)){
			map.put(exceptionHandle.getInspectionRecord().getId(), exceptionHandle);
		}
		return map;
	}
	
}
