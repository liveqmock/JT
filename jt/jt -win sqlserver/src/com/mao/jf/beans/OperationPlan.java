package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.beans.Transient;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mao.jf.beans.annotation.Caption;

import javax.persistence.OneToOne;

@Entity
public class OperationPlan extends BeanMao {
	private static SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	private int sequence;
	
	@ManyToOne
	@JoinColumn(name = "billplan", referencedColumnName = "id")
	private BillPlan billPlan;
	@Column(insertable = false, updatable = false)
	private String name;  
	private float cost   ;
	private String note  ;
	private float unitUseTime;
	private float prepareTime;
	private Date planDate;
	@OneToOne
	@JoinColumn(name = "name", referencedColumnName = "name")
	private Operation operation;
	public OperationPlan() {
		super();
	}
	public OperationPlan(Operation operation) {
		this.operation=operation;
	}

	@Caption(order =1, value= "流程序号")
	public int getSequenceChange() {
		 sequence= getBillPlan().getOperationPlans().indexOf(this)+1;
		 return sequence;
	}
	public int getSequence() {
		return sequence;
	}
	@Caption(order = 2, value= "工序名称")
	public String getName() {
		return name=name==null?operation.getName():name;
	}
	@Transient
	@Caption(order =3, value= "排产时间")
	public String getPlanDateStr() {
		try{
		return df.format(getPlanDate());
		}catch(Exception e){
			return null;
		}
	}
	@Caption(order = 4, value= "单件计划用时")
	public float getUnitUseTime() {
		return unitUseTime;
	}

	@Caption(order = 5, value= "调机时间")	
	public float getPrepareTime() {
		return prepareTime;
	}
	@Transient
	@Caption(order = 6, value= "计划总用时")
	public float getUseTime() {
		return unitUseTime*billPlan.getNum()+prepareTime;
	}


	@Caption(order = 7, value= "单位费用")
	public float getCost() {
		if(cost==0)
			cost=operation.getCost();
		return cost;
	}

	@Transient
	@Caption(order = 8, value= "计划费用")
	public float getPlanCost() {
		return cost*(getUnitUseTime()*billPlan.getNum()+prepareTime);
	}

	@Caption(order =99, value= "备注")
	public String getNote() {
		return note;
	}





	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUnitUseTime(float unitUseTime) {
		this.unitUseTime = unitUseTime;
	}


	public void setCost(float cost) {
		this.cost = cost;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setPrepareTime(float prepareTime) {
		this.prepareTime = prepareTime;
	}

	public BillPlan getBillPlan() {
		return billPlan;
	}
	public void setBillPlan(BillPlan billPlan) {
		this.billPlan = billPlan;
	}
	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return name;
	}

	public Date getPlanDate() {
		if(planDate==null){
			long planDatetime=0;
			long lastDate=getLastPlanDate();
			int point = getBillPlan().getOperationPlans().indexOf(this);
			if(point>0){
				OperationPlan lower = getBillPlan().getOperationPlans().get(point-1);
				long lowerPlanDate = lower.getPlanDate().getTime()+Math.round( lower.getUseTime()*60000);
				planDatetime=lastDate>lowerPlanDate?lastDate:lowerPlanDate;
				
			}else{
				planDatetime=lastDate;
			}
			planDate=new Date(planDatetime);
			Calendar calendar= Calendar.getInstance();	
			calendar.setTimeInMillis(planDatetime+Math.round( getUseTime()*60000));
			if( calendar.get(Calendar.HOUR_OF_DAY)>=17||calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 8);
				calendar.set(Calendar.MINUTE, 0);
				planDatetime=calendar.getTimeInMillis();
				planDate=calendar.getTime();
			}
			

		}
			
		return planDate;
	}
	@Transient
	public long getLastPlanDate() {
		long lastDate=0;
		try {
			OperationPlan operationPlan=OperationPlan.load(OperationPlan.class, "  a.planDate=(select max(planDate) from OperationPlan where name='"+getName()+"' and id<> "+id+")");
			lastDate=operationPlan.getPlanDate()==null?new Date().getTime(): 
								(operationPlan.getPlanDate().getTime())								
						         +Math.round( operationPlan.getUseTime()*60000);;
			
		} catch (Exception e) {
			lastDate=new Date().getTime();
		}

		return lastDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
	public void setOperation(Operation operation) {
		this.name=operation.getName();
		this.cost=operation.getCost();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		OperationPlan other = (OperationPlan) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (billPlan == null) {
			if (other.billPlan != null)
				return false;
		} else if (!billPlan.equals(other.billPlan))
			return false;
		return true;
	}
	
}
