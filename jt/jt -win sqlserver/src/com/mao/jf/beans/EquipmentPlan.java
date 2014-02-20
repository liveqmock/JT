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

@Entity
public class EquipmentPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@OneToOne
	@JoinColumn(name = "equipment", referencedColumnName = "id")
	private Equipment equipment;
	@ManyToOne
	private OperationPlan operationPlan;
	private Date planStartTime;
	private Date planEndTime;
	private int planUseTimes;
	private long freeTime;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "preEquipmentPlan", referencedColumnName = "id")
	private EquipmentPlan	preEquipmentPlan;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nextEquipmentPlan", referencedColumnName = "id")
	private EquipmentPlan	nextEquipmentPlan;
	public EquipmentPlan	getFirstNextPlan(int useTime,int prepareTime) throws Exception {
		EquipmentPlan equipmentPlan= new EquipmentPlan();
		equipmentPlan.setPlanUseTimes(useTime);		
		setNextEquipmentPlan(equipmentPlan);
		equipmentPlan.setPreEquipmentPlan(this);
		if(freeTime>0){
			equipmentPlan.setFreeTime( freeTime-useTime-prepareTime);
			if(equipmentPlan.getFreeTime()<0)
				throw new Exception("�����ռ�ʱ�䷶Χ");
		}
		if(nextEquipmentPlan!=null){
			nextEquipmentPlan.setPreEquipmentPlan(equipmentPlan);
			equipmentPlan.setNextEquipmentPlan(nextEquipmentPlan);
		}
		equipmentPlan.setPlanStartTime(getPlanEndTime(),prepareTime);
		return equipmentPlan;
		
		
	}
	public EquipmentPlan	getNextPlan(int useTime) throws Exception {
		return getFirstNextPlan(useTime, 0);		
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
		setPlanStartTime(planStartTime,0);
	}
	public void setPlanStartTime(Date planStartTime,int prepareTime) {
		this.planStartTime = planStartTime;
		setPlanEndTime(new Date(getPlanStartTime().getTime()+(prepareTime+ planUseTimes)*60000));
		if(getPreEquipmentPlan()!=null){
			long c =( planStartTime.getTime()-getPreEquipmentPlan().getPlanEndTime().getTime())/60000;
			if(c>0){
				getPreEquipmentPlan().setFreeTime(c);
			}
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
	public long getFreeTime() {
		return freeTime;
	}
	public void setFreeTime(long freeTime) {
		this.freeTime = freeTime;
	}
	
	
}
