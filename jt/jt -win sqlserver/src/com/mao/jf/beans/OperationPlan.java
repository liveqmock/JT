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

	@Caption("�Ų����")
	private int sequence;

	@Caption("�豸")
	@OneToOne	@JoinColumn(name = "operation", referencedColumnName = "id")
	private Operation operation;
	@Caption("����")
	private String technics;
	@Caption("��������")
	private String technicsDes;
	@Caption("ʹ���豸��")
	private int equipmentNum;
	@Caption("��λ��ʱ")
	private int unitUseTime;

	@Caption("������ʱ")
	private int prepareTime;

	@Caption("��ʼʱ��")
	@Transient
	private Date startDate;
	@Caption("����ʱ��")
	@Transient
	private Date endDate;
	@Caption( "���������")
	@Transient
	private int productedNum;
	@Caption( "ʣ������")
	@Transient
	private int remainNum;
	@Caption( "��ʱ")
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
		Object planEndTime= BeanMao.beanManager.queryNativeSingle("select min(planEndTime) from ("
				+ "select max(planEndTime) planEndTime from EquipmentPlan a join operationPlan b "
				+ "on a.operationPlan=b.id join operation c on b.operation=c.id and c.id=?1 )a"
				, operation.getId());

		return BeanMao.getBean(EquipmentPlan.class, " equipment.operation=?1 and planEndTime=?2 "
				,operation,planEndTime);
	}
	public EquipmentPlan getFreeEquipmentPlan(int userTime) {
		return BeanMao.getBean(EquipmentPlan.class, " equipment.operation=?1 and freeTime>=?2",operation,userTime);
	}

	public void deleteEquipmentPlans() {
		if(equipmentPlans==null) return;

		for(EquipmentPlan equipmentPlan:equipmentPlans){
			BeanMao.removeBean(equipmentPlan);
		}
		equipmentPlans.clear();

	}
	public void createPlan() throws Exception {
		int eachEquipmentNum = picPlan.getNum()/equipmentNum;
		int ye= picPlan.getNum()%equipmentNum;
		if(equipmentPlans==null)equipmentPlans=new ArrayList<>();	
		while(equipmentPlans.size()<equipmentNum){

			int num=eachEquipmentNum;
			if(ye>0){
				num++;
				ye--;
			}
			EquipmentPlan equipmentPlan=new EquipmentPlan();
			equipmentPlan.setNum(num);
			equipmentPlan.setPlanUseTimes(prepareTime+unitUseTime*num);
			equipmentPlan.setOperationPlan(this);

			computeStartDate(equipmentPlan);
			computeEndDate(equipmentPlan);
			BeanMao.saveBean(equipmentPlan);
			equipmentPlans.add(equipmentPlan);
		}
	}
	private void computeStartDate(EquipmentPlan equipmentPlan){
		Equipment equipment = BeanMao.getBean(Equipment.class,
				" operation=?1 and a not in (" 
						+ "select equipment from EquipmentPlan )",
						operation);
		if(equipment!=null){//��û���Ų����豸��ʱ������ڿ�ʼ
			equipmentPlan.setEquipment(equipment);
			Calendar calendar=Calendar.getInstance();
			if(picPlan.getPlanDate()!=null&&calendar.getTimeInMillis()<picPlan.getPlanDate().getTime()){
				calendar.setTime(picPlan.getPlanDate());
			}
			equipmentPlan.setPlanStartTime(calendar.getTime());

		}else{
			EquipmentPlan theEarliestEquipmentPlan=getFreeEquipmentPlan(equipmentPlan.getPlanUseTimes());//���м����ʱ�����������ɴ��Ų����豸
			if(theEarliestEquipmentPlan==null)
				theEarliestEquipmentPlan=getEarliestEquipmentPlan();//����ռ���豸

			equipmentPlan.setEquipment(theEarliestEquipmentPlan.getEquipment());
			equipmentPlan.setPlanStartTime(theEarliestEquipmentPlan.getPlanEndTime());


		}
		OperationPlan preOperationPlan = picPlan.getpreOperationPlan(this);
		if(preOperationPlan!=null){
			Date preEndDate =preOperationPlan.getEndDate();
			if(equipmentPlan.getPlanStartTime().getTime()<preEndDate.getTime())
				equipmentPlan.setPlanStartTime(preEndDate);
		}Calendar calendar=Calendar.getInstance();
		calendar.setTime(equipmentPlan.getPlanStartTime());
		if(calendar.get(Calendar.HOUR_OF_DAY)>=23){
			calendar.add(Calendar.DAY_OF_MONTH, 1);			
			calendar.set(Calendar.HOUR_OF_DAY, 8);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
		}
		equipmentPlan.setPlanStartTime(calendar.getTime());

	}
	private void computeEndDate(EquipmentPlan equipmentPlan) {
		Calendar calendarEnd=Calendar.getInstance();
		calendarEnd.setTimeInMillis(equipmentPlan.getPlanStartTime().getTime()+equipmentPlan.getPlanUseTimes()*60000);
		Calendar calendarStart=Calendar.getInstance();
		calendarStart.setTime(equipmentPlan.getPlanStartTime());
		int dayWorkTime=15*60;
		if(equipmentPlan.getPlanUseTimes()<=dayWorkTime&&
				(calendarEnd.get(Calendar.HOUR_OF_DAY)>=23||
				calendarEnd.get(Calendar.HOUR_OF_DAY)<8)){
			calendarStart.add(Calendar.DAY_OF_MONTH, 1);			
			calendarStart.set(Calendar.HOUR_OF_DAY, 8);
			calendarStart.set(Calendar.MINUTE, 0);
			calendarStart.set(Calendar.SECOND, 0);
			equipmentPlan.setPlanStartTime(calendarStart.getTime());
			computeEndDate(equipmentPlan);
			return;
		}else if(equipmentPlan.getPlanUseTimes()>dayWorkTime){
			int workDays = equipmentPlan.getPlanUseTimes()/dayWorkTime;
			calendarEnd.setTimeInMillis(calendarEnd.getTimeInMillis()+workDays*dayWorkTime*60000);

		}	
		int weekWorkDays=6;
		int weeks = equipmentPlan.getPlanUseTimes()/(dayWorkTime*weekWorkDays);
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
		// TODO �Զ����ɵķ������
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
