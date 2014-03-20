package com.mao.jf.beans;

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
import javax.persistence.Query;

import com.mao.jf.beans.annotation.Caption;
import static javax.persistence.CascadeType.ALL;

@Entity
public class OperationPlan extends BeanMao {
	private static SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private int id;

	private int sequence;

	private int unitUseTime;

	private int prepareTime;

	private int equipmentNum;

	@OneToMany(mappedBy = "operationPlan", cascade = ALL, orphanRemoval = true)	@OrderBy("planStartTime")

	private Collection<EquipmentPlan> equipmentPlans;

	@OneToMany(mappedBy = "operationPlan")
	private Collection<OperationWork> operationWorks;

	@ManyToOne	@JoinColumn(name = "billplan", referencedColumnName = "id")
	private BillPlan billPlan;

	@OneToOne	@JoinColumn(name = "operation", referencedColumnName = "id")
	private Operation operation;

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

	public OperationPlan(BillPlan plan) {
		this.billPlan=plan;
	}
	@Caption(order =1, value= "流程序号")
	public int getSequenceChange() {
		sequence= getIndex()+1;
		return sequence;
	}


	public EquipmentPlan getEarliestEquipmentPlan() {
		return BeanMao.getBean(EquipmentPlan.class, " equipment.operation=?1 and "
				+ "planEndTime=(select min(planEndTime) from EquipmentPlan a where  a.equipment.operation=?1 and a.nextEquipmentPlan is null) "
				,operation);
	}
	public EquipmentPlan getFreeEquipmentPlan(int userTime) {
		return BeanMao.getBean(EquipmentPlan.class, " equipment.operation=?1 and freeTime>=?2",operation,userTime);
	}

	public void createPlan() throws Exception {

		int eachEquipmentNum = billPlan.getNum()/equipmentNum;
		int ye= billPlan.getNum()%equipmentNum;
		if(equipmentPlans==null)equipmentPlans=new ArrayList<>();		
		for(EquipmentPlan equipmentPlan:equipmentPlans)
			BeanMao.removeBean(equipmentPlan);
		equipmentPlans.clear();

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
				OperationPlan preOperationPlan = billPlan.getpreOperationPlan(this);
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
	public void adjustTime(EquipmentPlan equipmentPlan) {
		Calendar calendarEnd=Calendar.getInstance();
		calendarEnd.setTime(equipmentPlan.getPlanEndTime());
		Calendar calendarStart=Calendar.getInstance();
		calendarStart.setTime(equipmentPlan.getPlanStartTime());
		//如果用时小于等于8小时并且结束时间已经是下班时间，则安排到第二天排产
		if(equipmentPlan.getPlanUseTimes()<9*60&&
				(calendarEnd.get(Calendar.HOUR_OF_DAY)>=17||
				calendarEnd.get(Calendar.HOUR_OF_DAY)<8)){
			calendarStart.add(Calendar.DAY_OF_MONTH, 1);			
			calendarStart.set(Calendar.HOUR_OF_DAY, 8);
			calendarStart.set(Calendar.MINUTE, 0);
			calendarStart.set(Calendar.SECOND, 0);
			equipmentPlan.setPlanStartTime(calendarStart.getTime());
			adjustTime(equipmentPlan);
		}else if(equipmentPlan.getPlanUseTimes()>9*60){
			int workDays = equipmentPlan.getPlanUseTimes()/540;
			calendarEnd.setTimeInMillis(calendarEnd.getTimeInMillis()+workDays*15*3_600_000);
			equipmentPlan.setPlanEndTime(calendarEnd.getTime());
		}


	}
	public int getSequence() {
		return sequence;
	}
	@Caption(order = 2, value= "工序名称")
	public String getName() {
		return name;
	}

	@Caption(order = 6, value= "耗时")
	public long getPlanProcessTime() {		
		return (getEndDate().getTime()-getStartDate().getTime())/60000;
	}


	@Caption(order = 7, value= "单位费用")
	public float getCost() {
		return cost;
	}

	@Caption(order = 8, value= "计划费用")
	public float getPlanCost() {
		return (unitUseTime*billPlan.getNum()+prepareTime)*getCost();
	}

	@Caption(order =99, value= "备注")
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


	public BillPlan getBillPlan() {
		return billPlan;
	}
	public void setBillPlan(BillPlan billPlan) {
		this.billPlan = billPlan;
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


	public Date getEndDate() {
		return Collections.max(equipmentPlans,new DateEquipmentPlanCompare() ).getPlanEndTime();
	}
	public Date getStartDate() {
		return Collections.min(equipmentPlans,new DateEquipmentPlanCompare() ).getPlanStartTime();
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationPlan other = (OperationPlan) obj;
		if (billPlan == null) {
			if (other.billPlan != null)
				return false;
		} else if (!billPlan.equals(other.billPlan))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public int getIndex() {
		return getBillPlan().getOperationPlans().indexOf(this);
	}
	public OperationPlan getPreBean() {
		if(getIndex()>0) 
			return getBillPlan().getOperationPlans().get(getIndex()-1);
		else
			return null;
	}
	public int getEquipmentNum() {
		return equipmentNum;
	}
	public void setEquipmentNum(int num) {
		Query query = BeanMao.beanManager.getEm().createQuery("select count(1) from Equipment where operation=?1");
		query.setParameter(1,operation);
		int equipmentCount=((Long)query.getSingleResult()).intValue();
		this.equipmentNum=num>equipmentCount?equipmentCount:num;
	}
	private class DateEquipmentPlanCompare implements Comparator<EquipmentPlan>{

		@Override
		public int compare(EquipmentPlan o1, EquipmentPlan o2) {
			// TODO 自动生成的方法存根
			return o1.getPlanEndTime().compareTo(o2.getPlanEndTime());
		}
	}
}
