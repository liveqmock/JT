package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.beans.Transient;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;

import com.mao.jf.beans.annotation.Caption;

public class OperationPlan extends BeanMao {
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

	@Transient
	@Caption(value="客户",order=1)
	public String getCustom() {
		return plan.getBill().getCustom();
	}
	@Transient
	@Caption(value="订单组",order=2)
	public String getBillgroup() {
		return plan.getBill().getBillgroup();
	}
	@Transient
	@Caption(value="订单号",order=3)
	public String getBillid() {
		return plan.getBill().getBillid();
	}
	@Transient
	@Caption(value="图号",order=4)
	public String getPiclid() {
		return plan.getBill().getPicid();
	}
	@Caption(order = 5, value= "工序名称")
	public String getName() {
		return name;
	}
	@Caption(order = 4, value= "单件计划用时")
	public float getUnitUseTime() {
		return unitUseTime;
	}
	@Transient
	@Caption(order = 5, value= "计划总用时")
	public float getUseTime() {
		return unitUseTime*plan.getNum()+prepareTime;
	}


	@Caption(order = 6, value= "单位费用")
	public float getCost() {
		return cost;
	}

	@Caption(order = 7, value= "调机时间")	
	public float getPrepareTime() {
		return prepareTime;
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
			OperationPlan lower = getPlan().getOperationPlans().lower(this);
			if(lower==null) {
				planDatetime=lastDate;
			}else{
				long lowerPlanDate = lower.getPlanDate().getTime()+Math.round( lower.getUseTime()*60000);
				planDatetime=lastDate>lowerPlanDate?lastDate:lowerPlanDate;
			}
			Calendar calendar= Calendar.getInstance();	
			calendar.setTimeInMillis(planDatetime+Math.round( getUseTime()*60000));
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			if(hour>17){
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 8);
				calendar.set(Calendar.MINUTE, 0);
			}
			if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 8);
				calendar.set(Calendar.MINUTE, 0);
			}

		}
		return planDate;
	}
	public long getLastPlanDate() {
		long lastDate=0;
		try {
			OperationPlan operationPlan=OperationPlan.load(OperationPlan.class, "select * from OperationPlan where plandate=(select max(plandate) from OperationPlan where name='"+getName()+"')");
			lastDate=operationPlan.getPlanDate()==null?new Date().getTime(): 
								(operationPlan.getPlanDate().getTime())								
						         +Math.round( operationPlan.getUseTime()*60000);;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			lastDate=new Date().getTime();
		}

		return lastDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

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
}
