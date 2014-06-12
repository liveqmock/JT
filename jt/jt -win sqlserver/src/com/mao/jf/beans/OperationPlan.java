package com.mao.jf.beans;

import static javax.persistence.CascadeType.ALL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.Transient;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.mao.jf.beans.annotation.Caption;

@Entity
public class OperationPlan extends BeanMao implements Comparable<OperationPlan> {
//	private static SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");

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

	@Caption("计划时间")
	private Date planDate;
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
	@Caption( "计划耗时")
	@Transient
	private long planUseTime;
	@Caption( "实际耗时")
	@Transient
	private long workUseTime;

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

	/**
	 * 找到最早空闲的设备
	 */
	public EquipmentPlan getEarliestEquipmentPlan() {
		Object planEndTime= BeanMao.beanManager.queryNativeSingle("select min(planEndTime) from ("
				+ "select max(planEndTime) planEndTime from EquipmentPlan "
				+ "where equipment in (select id from equipment where good=1 and operation=?1) "
				+ "group by equipment)a", operation.getId());

		return BeanMao.getBean(EquipmentPlan.class, " equipment.good=true and equipment.operation=?1 and planEndTime=?2 "
				,operation,planEndTime);
	}
	public List<EquipmentFreeTime> getFreeEquipmentPlan(long userTime, Date preStarDate) {
		return BeanMao.getBeans(EquipmentFreeTime.class, 
				"equipment.good=true and  equipment.operation=?1 and freeTime>?2 and startDate<?3 order by startDate",operation,userTime,preStarDate);
		//		return BeanMao.getBean(EquipmentFreeTime.class, 
		//				" equipment.operation=?1 and startDate<=?3 and dateadd(ms,(((?2)/equipment.workTime)*86400000+((?2)%equipment.workTime)*60000), ?3)<= endDate",operation,userTime,preStarDate);

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

			computePlanDate(equipmentPlan);

			BeanMao.saveBean(equipmentPlan);
			equipmentPlans.add(equipmentPlan);
		}
	}
	private void computePlanDate(EquipmentPlan equipmentPlan){

		OperationPlan preOperationPlan = picPlan.getpreOperationPlan(this);
		//下一天上班时间
		Calendar preStarDate = Calendar.getInstance();
		preStarDate.add(Calendar.DAY_OF_MONTH, 1);
		preStarDate.set(Calendar.HOUR_OF_DAY, 0);
		preStarDate.set(Calendar.MINUTE, 0);
		preStarDate.set(Calendar.SECOND, 0);
        if(picPlan.getPlanDate()!=null&&picPlan.getPlanDate().getTime()>preStarDate.getTimeInMillis()){
			preStarDate.setTime( picPlan.getPlanDate());
		}
        if(planDate!=null&&planDate.getTime()>preStarDate.getTimeInMillis()){
			preStarDate.setTime( planDate);
		}
		if(preOperationPlan!=null&&preOperationPlan.getEndDate().getTime()>preStarDate.getTimeInMillis()){
			preStarDate.setTime( preOperationPlan.getEndDate());
		}
		
		
		
		Equipment equipment = BeanMao.getBean(Equipment.class,
				" operation=?1 and good=true and  a not in (" 
						+ "select equipment from EquipmentPlan)",
						operation);
		if(equipment!=null){

			equipmentPlan.setEquipment(equipment);
			equipmentPlan.setPlanStartTime(preStarDate.getTime());
			EquipmentFreeTime equipmentFreeTime = equipmentPlan.getEquipmentFreeTime();
			equipmentFreeTime.setStartDate(new Date());			
			equipmentFreeTime.setEndDate(preStarDate.getTime());
			BeanMao.saveBean(equipmentFreeTime);

			equipmentPlan.computeEndDate();
		}else{
			//如果没有未排产的设备,则查找最早空间的设备
			List<EquipmentFreeTime> equipmentFreeTimes=getFreeEquipmentPlan(equipmentPlan.getPlanUseTimes(),preStarDate.getTime());//有中间空闲时间可以满足完成此排产的设备
			boolean hasFree=false;
			if(equipmentFreeTimes!=null&&equipmentFreeTimes.size()>0){
				for(EquipmentFreeTime equipmentFreeTime:equipmentFreeTimes){

					equipmentPlan.setEquipment(equipmentFreeTime.getEquipment());
					equipmentPlan.setPlanStartTime(preStarDate.getTime());
					equipmentPlan.computeEndDate();
					if(equipmentPlan.getPlanEndTime().getTime()<equipmentFreeTime.getEndDate().getTime()){

						EquipmentFreeTime equipmentFreeTime2=new EquipmentFreeTime();
						equipmentFreeTime2.setEquipment(equipmentFreeTime.getEquipment());
						equipmentFreeTime2.setStartDate(equipmentFreeTime.getStartDate());
						equipmentFreeTime2.setEndDate(equipmentPlan.getPlanStartTime());

						equipmentFreeTime.setStartDate(equipmentPlan.getPlanEndTime());		


						BeanMao.saveBean(equipmentFreeTime);				
						BeanMao.saveBean(equipmentFreeTime2);
					
						hasFree=true;
						break;
					}else{
						continue;
					}
				}


			}
			if(!hasFree){
				//最早空闲的设备
				EquipmentPlan theEarliestEquipmentPlan = getEarliestEquipmentPlan();
				equipmentPlan.setEquipment(theEarliestEquipmentPlan.getEquipment());
				equipmentPlan.setPlanStartTime(theEarliestEquipmentPlan.getPlanEndTime());
				
				if(preStarDate.getTimeInMillis()>equipmentPlan.getPlanStartTime().getTime()){

					EquipmentFreeTime equipmentFreeTime = equipmentPlan.getEquipmentFreeTime();
					equipmentFreeTime.setStartDate(equipmentPlan.getPlanStartTime());					
					equipmentFreeTime.setEndDate(preStarDate.getTime());
					BeanMao.saveBean(equipmentFreeTime);
					//设置开始时间
					equipmentPlan.setPlanStartTime(preStarDate.getTime());
				}

				equipmentPlan.computeEndDate();
			}

		}
	}

	
	public int getSequence() {
		return sequence;
	}
	public String getName() {
		return name;
	}

	public long getPlanUseTime() {	
		try{
			planUseTime=0;
			for(EquipmentPlan equipmentPlan:equipmentPlans){
				planUseTime+=equipmentPlan.getPlanUseTimes();
			}
			return planUseTime;
		}catch (Exception e){
			return 0;
		}
	}
	public long getWorkUseTime() {	
		try{
			workUseTime=0;
			for(OperationWork operationWork:operationWorks){
				workUseTime+=operationWork.getWorkTime();
			}
			return workUseTime;
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

	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
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
	public static void main(String a[]) {


		//		TreeSet<PicPlan> picPlans=new TreeSet(new Comparator<PicPlan>() {
		//
		//			@Override
		//			public int compare(PicPlan o1, PicPlan o2) {
		//
		//				if(o1.getStartDate()==null){
		//					if(o2.getStartDate()==null)
		//						return ((Integer)o1.getId()).compareTo(o2.getId());
		//					else
		//						return 1;
		//
		//				}
		//				if(o2.getStartDate()==null){
		//					if(o1.getStartDate()==null)
		//						return ((Integer)o1.getId()).compareTo(o2.getId());
		//					else
		//						return -1;
		//
		//				}
		//				return o1.getStartDate().compareTo(o2.getStartDate());
		//			}
		//		});
		//		for(PicPlan picPlan:list){
		//		if((picPlan.getOperationWorks()==null||picPlan.getOperationWorks().isEmpty())&&
		//				(picPlan.getStartDate()==null||	picPlan.getStartDate().getTime()>now)){
		//			picPlans.add(picPlan);
		//
		//		}
		//	}
//		List<PicPlan> list = BeanMao.getBeans(PicPlan.class," operationWorks.size=0 and (startDate>=?1 or startDate is null) and completed=0",new Date());
		List<PicPlan> list = BeanMao.getBeans(PicPlan.class);
//		for(PicPlan picPlan:list){
//			if(picPlan.getPic().isCancel());
//			picPlan.remove();
//			if(picPlan.getPic().isWarehoused());
//			picPlan.remove();
//			if(picPlan.getPic().isComplete());
//			picPlan.remove();
//			
//			
//		}
		
		BeanMao.beanManager.executeUpdate("delete  EquipmentFreeTime");


		System.err.println("delete....");
		for(PicPlan picPlan:list){
			picPlan.removeEquipmentPlans();
		}
		System.err.println("create....");
		for(PicPlan picPlan:list){
			picPlan.createEquipmentPlans();
		}
		
	}

}
