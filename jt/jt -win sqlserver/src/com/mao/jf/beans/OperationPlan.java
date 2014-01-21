package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.beans.Transient;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mao.jf.beans.annotation.Caption;

public class OperationPlan extends BeanMao {
	private static SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private int sequence;
	private Plan plan;
	private String name;  
	private float cost   ;
	private String note  ;
	private float unitUseTime;
	private float prepareTime;
	private Date planDate;

	public OperationPlan(Operation operation) {

		setCost(operation.getCost());
		setName(operation.getName());
		setPlan(plan);
	}

	@Caption(order =1, value= "流程序号")
	public int getSequence() {
		System.err.println(getPlan().getOperationPlans().size());
		return getPlan().getOperationPlans().indexOf(this)+1;
	}

	@Caption(order = 2, value= "工序名称")
	public String getName() {
		return name;
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
		return unitUseTime*plan.getNum()+prepareTime;
	}


	@Caption(order = 7, value= "单位费用")
	public float getCost() {
		return cost;
	}

	@Transient
	@Caption(order = 8, value= "计划费用")
	public float getPlanCost() {
		return cost*(getUnitUseTime()*plan.getNum()+prepareTime);
	}

	@Caption(order =99, value= "备注")
	public String getNote() {
		return note;
	}



	public Plan getPlan() {
		return plan;
	}



	public void setUnitUseTime(float unitUseTime) {
		this.unitUseTime = unitUseTime;
	}
	public OperationPlan() {
		super();
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
	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public void setPrepareTime(float prepareTime) {
		this.prepareTime = prepareTime;
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
			int point = getPlan().getOperationPlans().indexOf(this);
			if(point>0){
				OperationPlan lower = getPlan().getOperationPlans().get(point-1);
				long lowerPlanDate = lower.getPlanDate().getTime()+Math.round( lower.getUseTime()*60000);
				planDatetime=lastDate>lowerPlanDate?lastDate:lowerPlanDate;
			}else{
				planDatetime=lastDate;
			}
			planDate=new Date(lastDate);
			Calendar calendar= Calendar.getInstance();	
			calendar.setTimeInMillis(planDatetime+Math.round( getUseTime()*60000));
			
			if( calendar.get(Calendar.HOUR_OF_DAY)>17||calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
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
			OperationPlan operationPlan=OperationPlan.load(OperationPlan.class, "select top 1 * from OperationPlan where plandate=(select max(plandate) from OperationPlan where name='"+getName()+"')");
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
	@Transient
	public Operation getOperation() {
		try {
			return Operation.load(Operation.class, "select * from operation where name='"+name+"'");
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			return null;
		}
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
		if (plan == null) {
			if (other.plan != null)
				return false;
		} else if (!plan.equals(other.plan))
			return false;
		return true;
	}
	
}
