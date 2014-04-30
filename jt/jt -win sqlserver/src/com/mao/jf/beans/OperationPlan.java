package com.mao.jf.beans;

import static javax.persistence.CascadeType.ALL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class OperationPlan extends BeanMao implements Comparable<OperationPlan> {
	private static SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private int id;

	@Caption("排产序号")
	private int sequence;

	@Caption("设备")
	@OneToOne	@JoinColumn(name = "operation", referencedColumnName = "id")
	private Operation operation;
	@Caption("工艺")
	private String technics;
	@Caption("工艺描述")
	private String technicsDes;
	@Caption("使用设备数")
	private int equipmentNum;
	@Caption("单位用时")
	private int unitUseTime;

	@Caption("调机用时")
	private int prepareTime;

	@Caption("开始时间")
	@Transient
	private Date startDate;
	@Caption("结束时间")
	@Transient
	private Date endDate;
	@Caption( "已完成数量")
	@Transient
	private int productedNum;
	@Caption( "剩余数量")
	@Transient
	private int remainNum;
	@Caption( "耗时")
	@Transient
	private long planProcessTime;

	@OneToMany(mappedBy = "operationPlan", cascade = ALL, orphanRemoval = true)	@OrderBy("planStartTime")

	private Collection<EquipmentPlan> equipmentPlans;

	@OneToMany(mappedBy = "operationPlan",cascade = ALL)
	private Collection<OperationWork> operationWorks;

	@ManyToOne	@JoinColumn(name = "billplan", referencedColumnName = "id")
	private PicPlan picPlan;


	private String name;  

	private float cost   ;

	private String note  ;


	public OperationPlan() {
		super();
	}
	public OperationPlan(Operation operation) {
		this.name=operation.getName();
		this.cost=operation.getCost();
	}

	public OperationPlan(PicPlan plan) {
		this.picPlan=plan;
	}


	public EquipmentPlan getEarliestEquipmentPlan() {
		return BeanMao.getBean(EquipmentPlan.class, " equipment.operation=?1 and "
				+ "planEndTime=(select min(planEndTime) from EquipmentPlan a where  a.equipment.operation=?1 and a.nextEquipmentPlan is null) "
				,operation);
	}
	public EquipmentPlan getFreeEquipmentPlan(int userTime) {
		return BeanMao.getBean(EquipmentPlan.class, " equipment.operation=?1 and freeTime>=?2",operation,userTime);
	}

	public void deleteEquipmentPlans() {
		if(equipmentPlans==null) return;

		for(EquipmentPlan equipmentPlan:equipmentPlans){
			BeanMao.removeBean(equipmentPlan);
		}
		System.err.println(equipmentPlans.size());

	}
	public void createPlan() throws Exception {
		deleteEquipmentPlans();
		int eachEquipmentNum = picPlan.getNum()/equipmentNum;
		int ye= picPlan.getNum()%equipmentNum;
		if(equipmentPlans==null)equipmentPlans=new ArrayList<>();	
		while(equipmentPlans.size()<equipmentNum){
			Equipment equipment = BeanMao.getBean(Equipment.class,
					" operation=?1 and a not in (" 
							+ "select equipment from EquipmentPlan where operationPlan.operationWorks.size=0)",
							operation);
			int num=eachEquipmentNum;
			if(ye>0){
				num++;
				ye--;
			}
			EquipmentPlan equipmentPlan=new EquipmentPlan();
			equipmentPlan.setNum(num);
			equipmentPlan.setPlanUseTimes(prepareTime+unitUseTime*num);
			equipmentPlan.setOperationPlan(this);
			
			if(equipment!=null){//有没有排产的设备，时间从现在开始
				equipmentPlan.setEquipment(equipment);
				Calendar calendar=Calendar.getInstance();
				if(picPlan.getPlanDate()!=null&&calendar.getTimeInMillis()<picPlan.getPlanDate().getTime()){
					calendar.setTime(picPlan.getPlanDate());
				}
				if(calendar.get(Calendar.HOUR_OF_DAY)>=17){
					calendar.add(Calendar.DAY_OF_MONTH, 1);			
					calendar.set(Calendar.HOUR_OF_DAY, 8);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
				}
				equipmentPlan.setPlanStartTime(calendar.getTime());

			}else{
				EquipmentPlan theEarliestEquipmentPlan=getFreeEquipmentPlan(equipmentPlan.getPlanUseTimes());//有中间空闲时间可以满足完成此排产的设备
				if(theEarliestEquipmentPlan==null)
					theEarliestEquipmentPlan=getEarliestEquipmentPlan();//最早空间的设备

				theEarliestEquipmentPlan.toNextPlan(equipmentPlan);
				OperationPlan preOperationPlan = picPlan.getpreOperationPlan(this);
				if(preOperationPlan!=null){
					Date preEndDate =preOperationPlan.getEndDate();
					if(equipmentPlan.getPlanStartTime().getTime()<preEndDate.getTime())
						equipmentPlan.setPlanStartTime(preEndDate);
				}
			}

			adjustTime(equipmentPlan);
			BeanMao.saveBean(equipmentPlan);
			equipmentPlans.add(equipmentPlan);
		}
	}
	private void adjustTime(EquipmentPlan equipmentPlan) {
		Calendar calendarEnd=Calendar.getInstance();
		calendarEnd.setTime(equipmentPlan.getPlanEndTime());
		Calendar calendarStart=Calendar.getInstance();
		calendarStart.setTime(equipmentPlan.getPlanStartTime());
		//如果用时小于等于8小时并且结束时间已经是下班时间，则安排到第二天排产

		//双休日延迟

		if(equipmentPlan.getPlanUseTimes()<=9*60&&
				(calendarEnd.get(Calendar.HOUR_OF_DAY)>=17||
				calendarEnd.get(Calendar.HOUR_OF_DAY)<8)){
			calendarStart.add(Calendar.DAY_OF_MONTH, 1);			
			calendarStart.set(Calendar.HOUR_OF_DAY, 8);
			calendarStart.set(Calendar.MINUTE, 0);
			calendarStart.set(Calendar.SECOND, 0);
			equipmentPlan.setPlanStartTime(calendarStart.getTime());
			adjustTime(equipmentPlan);
			return;
		}else if(equipmentPlan.getPlanUseTimes()>540){
			int workDays = equipmentPlan.getPlanUseTimes()/540;
			calendarEnd.setTimeInMillis(calendarEnd.getTimeInMillis()+workDays*15*3_600_000);

		}	
		int weekWorkDays=6;
		int weeks = equipmentPlan.getPlanUseTimes()/(9*60*weekWorkDays);
		calendarEnd.add(Calendar.DAY_OF_MONTH, weeks*(7-weekWorkDays));	
		long time = calendarEnd.getTimeInMillis()-calendarStart.getTimeInMillis();
		if(calendarEnd.get(Calendar.DAY_OF_WEEK)<calendarStart.get(Calendar.DAY_OF_WEEK)||
				(calendarEnd.get(Calendar.DAY_OF_WEEK)==calendarStart.get(Calendar.DAY_OF_WEEK)&&
				time>(1000*60*60*24*(weekWorkDays+7*weeks))
				&&time<1000*60*60*24*7*(weeks+1)
						))
			calendarEnd.add(Calendar.DAY_OF_MONTH, 1);
		if(calendarEnd.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
			calendarEnd.add(Calendar.DAY_OF_MONTH, 1);	

		equipmentPlan.setPlanEndTime(calendarEnd.getTime());
	}
	public int getSequence() {
		return sequence;
	}
	public String getName() {
		return name;
	}

	public long getPlanProcessTime() {	
		try{
			return (getEndDate().getTime()-getStartDate().getTime())/60000;
		}catch (Exception e){
			return 0;
		}
	}	
	public int getProductedNum() {	
		
			productedNum=0;
			if(operationWorks!=null)
			for(OperationWork operationWork:operationWorks){
				productedNum+=operationWork.getProductNum();
			}
			return productedNum;
	}

	public int getRemainNum() {
		return picPlan.getNum()- getProductedNum() ;
	}

	public float getCost() {
		return cost;
	}

	public float getPlanCost() {
		return (unitUseTime*picPlan.getNum()+prepareTime)*getCost();
	}

	public String getNote() {
		return note;
	}





	public int getId() {
		return id;
	}

	public Collection<OperationWork> getOperationWorks() {
		return operationWorks;
	}
	public void setOperationWorks(Collection<OperationWork> operationWorks) {
		this.operationWorks = operationWorks;
	}
	public int getUnitUseTime() {
		return unitUseTime;
	}
	public void setUnitUseTime(int unitUseTime) {
		this.unitUseTime = unitUseTime;
	}
	public int getPrepareTime() {
		return prepareTime;
	}
	public void setPrepareTime(int prepareTime) {
		this.prepareTime = prepareTime;
	}
	public Operation getOperation() {
		return operation;
	}
	public PicPlan getPicPlan() {
		return picPlan;
	}
	public String getTechnics() {
		return technics;
	}
	public void setTechnics(String technics) {
		this.technics = technics;
	}
	public String getTechnicsDes() {
		return technicsDes;
	}
	public void setTechnicsDes(String technicsDes) {
		this.technicsDes = technicsDes;
	}
	public void setPicPlan(PicPlan picPlan) {
		this.picPlan = picPlan;
	}
	public void setId(int id) {
		this.id = id;
	}



	public void setCost(float cost) {
		this.cost = cost;
	}
	public void setName(String name) {
		this.name = name;
		if(name!=null){
			Operation operation=BeanMao.getBean(Operation.class, " a.name='"+name+"'");
			this.cost=operation.getCost();
		}
	}

	public void setNote(String note) {
		this.note = note;
	}


	public Collection<EquipmentPlan> getEquipmentPlans() {
		return equipmentPlans;
	}
	public void setEquipmentPlans(Collection<EquipmentPlan> equipmentPlans) {
		this.equipmentPlans = equipmentPlans;
	}
	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return name;
	}

	public Date getStartDate() {
		try{
			return Collections.max(equipmentPlans,new DateEquipmentPlanCompare() ).getPlanStartTime();
		}catch(Exception e){
			return null;
		}
	}
	public Date getEndDate() {
		try{
			return Collections.max(equipmentPlans,new DateEquipmentPlanCompare() ).getPlanEndTime();
		}catch(Exception e){
			return null;
		}
	}



	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public void setOperation(Operation operation) {
		this.operation=operation;
		try{
			this.name=operation.getName();
			this.cost=operation.getCost();
		}catch(Exception e){

		}
	}
	public int getEquipmentNum() {
		return equipmentNum;
	}
	public void setEquipmentNum(int num) {
		num=num>picPlan.getNum()?picPlan.getNum():num;
		int equipmentCount=((Long) BeanMao.beanManager.querySingle("select count(1) from Equipment where operation=?1", Long.class, operation)).intValue();
		this.equipmentNum=num>equipmentCount?equipmentCount:num;
	}
	private class DateEquipmentPlanCompare implements Comparator<EquipmentPlan>{

		@Override
		public int compare(EquipmentPlan o1, EquipmentPlan o2) {
			// TODO 自动生成的方法存根
			return o1.getPlanEndTime().compareTo(o2.getPlanEndTime());
		}
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationPlan other = (OperationPlan) obj;
		if (picPlan == null) {
			if (other.picPlan != null)
				return false;
		} else if (!picPlan.equals(other.picPlan))
			return false;
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		return true;
	}
	@Override
	public int compareTo(OperationPlan o) {
		// TODO 自动生成的方法存根
		return ((Integer)getSequence()).compareTo(o.getSequence());
	}


}
