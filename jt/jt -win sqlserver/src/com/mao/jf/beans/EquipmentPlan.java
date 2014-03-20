package com.mao.jf.beans;


import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

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
import static javax.persistence.CascadeType.ALL;

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
	
	@OneToOne(fetch = FetchType.LAZY, cascade = { PERSIST, MERGE, REFRESH }, orphanRemoval = true)
	@JoinColumn(name = "preEquipmentPlan", referencedColumnName = "id")
	private EquipmentPlan	preEquipmentPlan;
	
	@OneToOne(fetch = FetchType.LAZY,cascade = { PERSIST, MERGE, REFRESH }, orphanRemoval = true)
	@JoinColumn(name = "nextEquipmentPlan", referencedColumnName = "id")
	private EquipmentPlan	nextEquipmentPlan;
	public void	toNextPlan(EquipmentPlan equipmentPlan) throws Exception {
		equipmentPlan.setEquipment(equipment);
		equipmentPlan.setPlanStartTime(getPlanEndTime());
		if(nextEquipmentPlan!=null){
			nextEquipmentPlan.setPreEquipmentPlan(equipmentPlan);
			equipmentPlan.setNextEquipmentPlan(nextEquipmentPlan);			
			equipmentPlan.setFreeTime(((Long)(nextEquipmentPlan.getPlanStartTime().getTime()-equipmentPlan.getPlanEndTime().getTime())).intValue()/60000);
		}
		equipmentPlan.setPreEquipmentPlan(this);
		this.setNextEquipmentPlan(equipmentPlan);
		
		
	}
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
		setPlanEndTime(new Date(getPlanStartTime().getTime()+planUseTimes*60000));
		if(getPreEquipmentPlan()!=null){
			long c =( planStartTime.getTime()-getPreEquipmentPlan().getPlanEndTime().getTime())/60000;
			getPreEquipmentPlan().setFreeTime(((Long)c).intValue());
			
		}
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
	public EquipmentPlan getPreEquipmentPlan() {
		return preEquipmentPlan;
	}
	public void setPreEquipmentPlan(EquipmentPlan preEquipmentPlan) {
		this.preEquipmentPlan = preEquipmentPlan;
	}
	public EquipmentPlan getNextEquipmentPlan() {
		return nextEquipmentPlan;
	}
	public void setNextEquipmentPlan(EquipmentPlan nextEquipmentPlan) {
		this.nextEquipmentPlan = nextEquipmentPlan;
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
	@PreRemove
	private void preRemove(){
		if(preEquipmentPlan!=null&&nextEquipmentPlan!=null){
			preEquipmentPlan.setFreeTime((((Long)(nextEquipmentPlan.getPlanStartTime().getTime()-preEquipmentPlan.getPlanEndTime().getTime())).intValue()/60000));
			preEquipmentPlan.setNextEquipmentPlan(nextEquipmentPlan);
		}
		if(nextEquipmentPlan!=null)
			nextEquipmentPlan.setPreEquipmentPlan(preEquipmentPlan);
	}
	
}
