package com.mao.jf.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import com.mao.jf.beans.annotation.Caption;

import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

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
	@OneToMany(mappedBy = "operationPlan")
	@OrderBy("planStartTime")
	private Collection<EquipmentPlan> equipmentPlans;
	@ManyToOne
	@JoinColumn(name = "billplan", referencedColumnName = "id")
	private BillPlan billPlan;
	@OneToOne
	@JoinColumn(name = "operation", referencedColumnName = "id")
	private Operation operation;
	private String name;  
	private float cost   ;
	private String note  ;
	@javax.persistence.Transient
	private HashMap<Equipment, TreeSet<EquipmentPlan>> equipmentplanMap=new HashMap<>();
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
	public HashMap<Equipment, TreeSet<EquipmentPlan>> getEquipmentplanMap() {
		for(EquipmentPlan equipmentPlan:equipmentPlans){
			TreeSet<EquipmentPlan> equipmentPlanSet=equipmentplanMap.get(equipmentPlan.getEquipment());
			if(equipmentPlanSet==null){
				 equipmentPlanSet=new TreeSet<>(new DateEquipmentPlanCompare());
				
			}
			equipmentPlanSet.add(equipmentPlan);
			
		}
		return equipmentplanMap;
	}
	public EquipmentPlan getNowEarliestEquipmentPlan() {
		EquipmentPlan earliestLastEquipmentPlan=null;
		for(TreeSet<EquipmentPlan> equipmentPlanSet:getEquipmentplanMap().values()){
			if(earliestLastEquipmentPlan==null)
				earliestLastEquipmentPlan=equipmentPlanSet.last();
			else{
				EquipmentPlan theEquipmentPlan = equipmentPlanSet.last();
				if(earliestLastEquipmentPlan.getPlanEndTime().getTime()>theEquipmentPlan.getPlanEndTime().getTime())
					earliestLastEquipmentPlan=theEquipmentPlan;
			}
		}
		return earliestLastEquipmentPlan;
	}
	public EquipmentPlan getEarliestEquipmentPlan() {
		return BeanMao.load(EquipmentPlan.class, " equipment.operation=?1 and plaEndTime=(select max(plaEndTime) from equipmentPlan)",new Object[]{operation});
	}
	public EquipmentPlan getFreeEquipmentPlan() {
		return BeanMao.load(EquipmentPlan.class, " equipment.operation=?1 and freeTime>=?2",new Object[]{operation,(prepareTime+Math.round( unitUseTime*Math.ceil(( billPlan.getNum()*1.0d)/equipmentNum)))});
	}
	public void createPlan() throws Exception {
		HashSet<Integer> equipmentSet=new HashSet<>();
		while(equipmentSet.size()<equipmentNum){
			EquipmentPlan theEarliestEquipmentPlan=getFreeEquipmentPlan();
			if(theEarliestEquipmentPlan==null)
				theEarliestEquipmentPlan=getEarliestEquipmentPlan();
			EquipmentPlan equipmentPlan=theEarliestEquipmentPlan.getFirstNextPlan(unitUseTime, prepareTime);
			equipmentPlan.setEquipment(theEarliestEquipmentPlan.getEquipment());
			equipmentPlan.setOperationPlan(this);
			Date preEndDate = billPlan.getpreOperationPlan(this).getEndDate();
			if(equipmentPlan.getPlanStartTime().getTime()<preEndDate.getTime())
				equipmentPlan.setPlanStartTime(preEndDate);
			BeanMao.saveBean(equipmentPlan);
			equipmentSet.add(equipmentPlan.getEquipment().getId());
			equipmentPlans.add(equipmentPlan);
		}
		if(equipmentNum<billPlan.getNum()){
			for(int i=equipmentNum;i<=billPlan.getNum();i++){
				EquipmentPlan theEarliestEquipmentPlan=getNowEarliestEquipmentPlan();
				EquipmentPlan equipmentPlan=theEarliestEquipmentPlan.getNextPlan(unitUseTime);
				equipmentPlan.setEquipment(theEarliestEquipmentPlan.getEquipment());
				equipmentPlan.setOperationPlan(this);
				equipmentPlans.add(equipmentPlan);
			}
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
			Operation operation=BeanMao.load(Operation.class, " a.name='"+name+"'");
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
		TreeSet<EquipmentPlan> equipmentPlanSet=new TreeSet<>(new DateEquipmentPlanCompare());
		equipmentPlanSet.addAll(equipmentPlans);
		return equipmentPlanSet.last().getPlanEndTime();
	}
	public Date getStartDate() {
		TreeSet<EquipmentPlan> equipmentPlanSet=new TreeSet<>(new DateEquipmentPlanCompare());
		equipmentPlanSet.addAll(equipmentPlans);
		return equipmentPlanSet.first().getPlanEndTime();
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
	public void setEquipmentNum(int equipmentNum) {
		this.equipmentNum = equipmentNum;
	}
	private class DateEquipmentPlanCompare implements Comparator<EquipmentPlan>{

		@Override
		public int compare(EquipmentPlan o1, EquipmentPlan o2) {
			// TODO 自动生成的方法存根
			return o1.getPlanEndTime().compareTo(o2.getPlanEndTime());
		}
	}
	
}
