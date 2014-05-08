package com.mao.jf.beans;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;

@Entity
public class EquipmentPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	@JoinColumn(name = "equipment", referencedColumnName = "id")
	private Equipment equipment;
	private int num;

	@ManyToOne
	@JoinColumn(name = "operationPlan", referencedColumnName = "id")
	private OperationPlan operationPlan;

	private Date planStartTime;
	private Date planEndTime;
	private int planUseTimes;
	private int freeTime;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public OperationPlan getOperationPlan() {
		return operationPlan;
	}
	public void setOperationPlan(OperationPlan operationPlan) {
		this.operationPlan = operationPlan;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	public Date getPlanStartTime() {
		return planStartTime;
	}
	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;

	}
	public int getPlanUseTimes() {
		return planUseTimes;
	}
	public void setPlanUseTimes(int planUseTimes) {
		this.planUseTimes = planUseTimes;
	}
	public Date getPlanEndTime() {
		return planEndTime;
	}
	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}

	public int getFreeTime() {
		return freeTime;
	}
	public void setFreeTime(int freeTime) {
		this.freeTime = freeTime;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}


}
