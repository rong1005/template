package com.cn.template.entity.experiment;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;

/**
 * 实验样品巡检记录表.
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_sample_inspection")
public class SampleInspection extends IdEntity{

	/** 样品排期 */
	private Schedule schedule;
	
	/** 巡检记录 */
	private InspectionRecord inspectionRecord;
	
	@ManyToOne
	@JoinColumn(name="schedule_id")
	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	@ManyToOne
	@JoinColumn(name="inspection_record_id")
	public InspectionRecord getInspectionRecord() {
		return inspectionRecord;
	}

	public void setInspectionRecord(InspectionRecord inspectionRecord) {
		this.inspectionRecord = inspectionRecord;
	}
}
