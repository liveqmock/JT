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
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.DETACH;

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
	private long planUseTimes;

	
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
	public long getPlanUseTimesOf24Hour() {
		return (( planUseTimes)/equipment.getWorkTime())*86400000l+(planUseTimes%equipment.getWorkTime())*60000l;
	}
	public long getPlanUseTimes() {
		return planUseTimes;
	}
	public void setPlanUseTimes(long planUseTimes) {
		this.planUseTimes = planUseTimes;
	}
	public Date getPlanEndTime() {
		return planEndTime;
	}
	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public EquipmentFreeTime getEquipmentFreeTime() {
		
			EquipmentFreeTime equipmentFreeTime = new EquipmentFreeTime();
			equipmentFreeTime.setEquipment(this.equipment);
		return equipmentFreeTime;
	}
	public void computeEndDate() {
		planEndTime=new Date(getPlanStartTime().getTime()+getPlanUseTimesOf24Hour());
		
		
		
	}


}
