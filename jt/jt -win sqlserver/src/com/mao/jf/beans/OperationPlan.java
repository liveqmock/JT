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

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.mao.jf.beans.annotation.Caption;

@Entity
public class OperationPlan extends BeanMao implements Comparable<OperationPlan> {
	private static SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private int id;

	@Caption("�Ų����")
	private int sequence;

	private int unitUseTime;

	private int prepareTime;

	private int equipmentNum;

	@OneToMany(mappedBy = "operationPlan", cascade = ALL, orphanRemoval = true)	@OrderBy("planStartTime")

	private Collection<EquipmentPlan> equipmentPlans;

	@OneToMany(mappedBy = "operationPlan")
	private Collection<OperationWork> operationWorks;

	@ManyToOne	@JoinColumn(name = "billplan", referencedColumnName = "id")
	private PicPlan picPlan;

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
		equipmentPlans.clear();
		BeanMao.beanManager.executeUpdate("delete EquipmentPlan where operationPlan=?1",this);

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
			if(equipment!=null){//��û���Ų����豸��ʱ������ڿ�ʼ
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
				EquipmentPlan theEarliestEquipmentPlan=getFreeEquipmentPlan(equipmentPlan.getPlanUseTimes());//���м����ʱ�����������ɴ��Ų����豸
				if(theEarliestEquipmentPlan==null)
					theEarliestEquipmentPlan=getEarliestEquipmentPlan();//����ռ���豸

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
	public void adjustTime(EquipmentPlan equipmentPlan) {
		Calendar calendarEnd=Calendar.getInstance();
		calendarEnd.setTime(equipmentPlan.getPlanEndTime());
		Calendar calendarStart=Calendar.getInstance();
		calendarStart.setTime(equipmentPlan.getPlanStartTime());
		//�����ʱС�ڵ���8Сʱ���ҽ���ʱ���Ѿ����°�ʱ�䣬���ŵ��ڶ����Ų�

		//˫�����ӳ�

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

		int weeks = equipmentPlan.getPlanUseTimes()/(9*60*5);
		calendarEnd.add(Calendar.DAY_OF_MONTH, weeks*2);	
		long time = calendarEnd.getTimeInMillis()-calendarStart.getTimeInMillis();
		if(calendarEnd.get(Calendar.DAY_OF_WEEK)<calendarStart.get(Calendar.DAY_OF_WEEK)||
				(calendarEnd.get(Calendar.DAY_OF_WEEK)==calendarStart.get(Calendar.DAY_OF_WEEK)&&
				time>(1000*60*60*24*6+1000*60*60*24*7*weeks)
				&&time<1000*60*60*24*7*(weeks+1)
						))
			calendarEnd.add(Calendar.DAY_OF_MONTH, 2);
		if(calendarEnd.get(Calendar.DAY_OF_WEEK)>=Calendar.SATURDAY)
			calendarEnd.add(Calendar.DAY_OF_MONTH, 2);	

		equipmentPlan.setPlanEndTime(calendarEnd.getTime());
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
		try{
			return (getEndDate().getTime()-getStartDate().getTime())/60000;
		}catch (Exception e){
			return 0;
		}
	}


	@Caption(order = 7, value= "��λ����")
	public float getCost() {
		return cost;
	}

	@Caption(order = 8, value= "�ƻ�����")
	public float getPlanCost() {
		return (unitUseTime*picPlan.getNum()+prepareTime)*getCost();
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
	public PicPlan getPicPlan() {
		return picPlan;
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
		// TODO �Զ����ɵķ������
		return name;
	}


	public Date getEndDate() {
		try{
			return Collections.max(equipmentPlans,new DateEquipmentPlanCompare() ).getPlanEndTime();
		}catch(Exception e){
			return null;
		}
	}
	public Date getStartDate() {try{
		return Collections.max(equipmentPlans,new DateEquipmentPlanCompare() ).getPlanStartTime();
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
			// TODO �Զ����ɵķ������
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
		// TODO �Զ����ɵķ������
		return ((Integer)getSequence()).compareTo(o.getSequence());
	}


}
