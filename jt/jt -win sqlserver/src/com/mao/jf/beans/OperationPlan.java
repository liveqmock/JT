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

	@OneToMany(mappedBy = "operationPlan")	@OrderBy("planStartTime")

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
	@Caption(order =1, value= "�������")
	public int getSequenceChange() {
		sequence= getIndex()+1;
		return sequence;
	}


	public EquipmentPlan getEarliestEquipmentPlan() {
		return BeanMao.getBean(EquipmentPlan.class, " equipment.operation=?1 and "
				+ "planEndTime=(select min(planEndTime) from EquipmentPlan where nextEquipmentPlan is null) "
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
			if(equipment!=null){//��û���Ų����豸��ʱ������ڿ�ʼ
				equipmentPlan.setEquipment(equipment);
				equipmentPlan.setPlanStartTime(new Date());

			}else{
				EquipmentPlan theEarliestEquipmentPlan=getFreeEquipmentPlan(equipmentPlan.getPlanUseTimes());//���м����ʱ�����������ɴ��Ų����豸
				if(theEarliestEquipmentPlan==null)
					theEarliestEquipmentPlan=getEarliestEquipmentPlan();//����ռ���豸

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
		//�����ʱС�ڵ���8Сʱ���ҽ���ʱ���Ѿ����°�ʱ�䣬���ŵ��ڶ����Ų�
		if(equipmentPlan.getPlanUseTimes()<9*3_600_000 
				&&calendarStart.get(Calendar.HOUR_OF_DAY)==8&&
				(calendarStart.get(Calendar.HOUR_OF_DAY)>=17||
						calendarEnd.get(Calendar.HOUR_OF_DAY)>=17||
						calendarEnd.get(Calendar.HOUR_OF_DAY)<8||
						calendarStart.get(Calendar.HOUR_OF_DAY)<8)){
			if(calendarStart.get(Calendar.HOUR_OF_DAY)>8){				
				calendarStart.add(Calendar.DAY_OF_MONTH, 1);
			}
			EquipmentPlan preEquipmentPlan = equipmentPlan.getPreEquipmentPlan();
			if(preEquipmentPlan!=null)
				preEquipmentPlan.setFreeTime((((Long)(calendarStart.getTimeInMillis()-preEquipmentPlan.getPlanEndTime().getTime())).intValue())/60000);
			calendarStart.set(Calendar.HOUR_OF_DAY, 8);
			calendarStart.set(Calendar.MINUTE, 0);
			calendarStart.set(Calendar.SECOND, 0);
			equipmentPlan.setPlanStartTime(calendarStart.getTime());
			adjustTime(equipmentPlan);

			return;
		}else if(calendarEnd.get(Calendar.HOUR_OF_DAY)>=17||
				equipmentPlan.getPlanUseTimes()>9*3600000){
			calendarEnd.set(Calendar.HOUR_OF_DAY, 17);
			calendarEnd.set(Calendar.MINUTE, 0);
			calendarEnd.set(Calendar.SECOND, 0);
			equipmentPlan.setPlanEndTime(calendarEnd.getTime());
			int useTime=equipmentPlan.getPlanUseTimes()- ((Long)((equipmentPlan.getPlanEndTime().getTime()-equipmentPlan.getPlanStartTime().getTime())/60000)).intValue();
			equipmentPlan.setPlanUseTimes( ((Long)((equipmentPlan.getPlanEndTime().getTime()-equipmentPlan.getPlanStartTime().getTime())/60000)).intValue());
			equipmentPlan.setFreeTime(0);
			
			
			calendarStart.add(Calendar.DAY_OF_MONTH, 1);
			calendarStart.set(Calendar.HOUR_OF_DAY, 8);
			calendarStart.set(Calendar.MINUTE, 0);
			calendarStart.set(Calendar.SECOND, 0);
			
			EquipmentPlan equipmentPlan2=new EquipmentPlan();			
			equipmentPlan2.setNum(equipmentNum);
			equipmentPlan2.setPlanUseTimes(useTime);
			equipmentPlan2.setOperationPlan(this);
			equipmentPlan2.setEquipment(equipmentPlan.getEquipment());
			equipmentPlan2.setPlanStartTime(calendarStart.getTime());
			equipmentPlan2.setPreEquipmentPlan(equipmentPlan);			
			if(equipmentPlan.getNextEquipmentPlan()!=null) 
				equipmentPlan2.setNextEquipmentPlan(equipmentPlan.getNextEquipmentPlan());
			equipmentPlan.setNextEquipmentPlan(equipmentPlan2);
			equipmentPlans.add(equipmentPlan2);

			adjustTime(equipmentPlan2);

		}


	}
	public int getSequence() {
		return sequence;
	}
	@Caption(order = 2, value= "��������")
	public String getName() {
		return name;
	}

	@Caption(order = 6, value= "��ʱ")
	public long getPlanProcessTime() {		
		return (getEndDate().getTime()-getStartDate().getTime())/60000;
	}


	@Caption(order = 7, value= "��λ����")
	public float getCost() {
		return cost;
	}

	@Caption(order = 8, value= "�ƻ�����")
	public float getPlanCost() {
		return (unitUseTime*billPlan.getNum()+prepareTime)*getCost();
	}

	@Caption(order =99, value= "��ע")
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
		// TODO �Զ����ɵķ������
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
			// TODO �Զ����ɵķ������
			return o1.getPlanEndTime().compareTo(o2.getPlanEndTime());
		}
	}
}
