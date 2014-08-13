package com.cn.template.service.experiment;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.template.entity.experiment.InspectionRecord;
import com.cn.template.entity.experiment.SampleInspection;
import com.cn.template.entity.experiment.Schedule;
import com.cn.template.repository.experiment.InspectionRecordDao;
import com.cn.template.repository.experiment.SampleInspectionDao;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;

/**
 * 巡检记录的业务逻辑.
 * @author Libra
 *
 */
@Component  // Spring Bean的标识.
@Transactional // 类中所有public函数都纳入事务管理的标识.
public class InspectionRecordService {

	/** 巡检记录的数据访问接口 */
	private InspectionRecordDao inspectionRecordDao;
	
	/** 实验样品巡检信息的数据访问接口 */
	private SampleInspectionDao sampleInspectionDao;
	
	/** 实验排期信息的业务处理类 */
	private ScheduleService scheduleService;

	@Autowired
	public void setInspectionRecordDao(InspectionRecordDao inspectionRecordDao) {
		this.inspectionRecordDao = inspectionRecordDao;
	}
	
	@Autowired
	public void setSampleInspectionDao(SampleInspectionDao sampleInspectionDao) {
		this.sampleInspectionDao = sampleInspectionDao;
	}
	 
	@Autowired
	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	/**
	 * 根据ID获得巡检记录记录.
	 * @param id
	 * @return
	 */
	public InspectionRecord getInspectionRecord(Long id) {
		return inspectionRecordDao.findOne(id);
	}

	/**
	 * 保存巡检记录信息.
	 * @param entity
	 */
	public void saveInspectionRecord(InspectionRecord entity) {
		//新增巡检记录
		if(entity.getId()==null){
			//保存巡检设备对应的样品记录信息.
			//取得巡检记录对应的排期信息
			List<Schedule> scheduleList = scheduleService.findInspectionSchedule(entity);
			if(scheduleList!=null&&!scheduleList.isEmpty()){
				//取得设备有哪些实验样品.
				for(Schedule schedule : scheduleList){
					SampleInspection sampleInspection=new SampleInspection();
					sampleInspection.setCreateTime(new Date());
					sampleInspection.setUpdateTime(new Date());
					sampleInspection.setInspectionRecord(entity);
					sampleInspection.setSchedule(schedule);
					sampleInspectionDao.save(sampleInspection);
				}
			}
		}
		inspectionRecordDao.save(entity);
	}

	/**
	 * 单个删除巡检记录记录.
	 * @param id
	 */
	public void deleteInspectionRecord(Long id) {
		inspectionRecordDao.delete(id);
		List<SampleInspection> sampleInspectionList = sampleInspectionDao.findByInspectionRecord_Id(id);
		if(sampleInspectionList!=null&&!sampleInspectionList.isEmpty()){
			sampleInspectionDao.delete(sampleInspectionList);
		}
	}

	/**
	 * 获得所有的巡检记录记录.
	 * @return
	 */
	public List<InspectionRecord> getAllInspectionRecord() {
		return (List<InspectionRecord>) inspectionRecordDao.findAll();
	}

	/**
	 * 获取巡检记录记录[查询、分页、排序].
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<InspectionRecord> getInspectionRecord(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<InspectionRecord> spec = buildSpecification(searchParams);
		return inspectionRecordDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页\排序请求.
	 * @param pageNumber
	 * @param pagzSize
	 * @param sortType
	 * @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.ASC, "createTime");
		} else if ("name".equals(sortType)) {
			sort = new Sort(Direction.ASC, "name");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 * @param searchParams
	 * @return
	 */
	private Specification<InspectionRecord> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<InspectionRecord> spec = DynamicSpecifications.bySearchFilter(filters.values(), InspectionRecord.class);
		return spec;
	}
	
	/**
	 * 取得样品对应的巡检记录信息.
	 * @param sampleId
	 * @return
	 */
	public List<SampleInspection> findSampleInspection(Long sampleId){
		return sampleInspectionDao.findBySchedule_Sample_Id(sampleId);
	}
	
}
