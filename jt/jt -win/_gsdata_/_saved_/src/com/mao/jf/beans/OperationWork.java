package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.beans.Transient;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class OperationWork extends BeanMao {
	public static Vector<OperationWork> loadFinishOperationWorks() {
		try {
			return loadAll(OperationWork.class, "select * from OperationWork where finishDate is  null");
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
	private Plan plan;
	private OperationPlan operationPlan;
	private Employee employee;
	private Date finishDate;
	private int getNum;
	private int productNum;
	private int scrapNum;
	private String scrapReason;
	private float useTime;
	private String note;
	private Employee checker;
	private int weight;
	
	/**
	 * 调机时间
	 */
	private float prepareTime;
	
	@ChinaAno(str="操作员",order=4)
	public Employee getEmployee() {
		return employee;
	}



	@ChinaAno(str="实发数",order=4)
	public int getGetNum() {
		return getNum;
	}


	@ChinaAno(str="工序",order=3)
	public OperationPlan getOperationPlan() {
		return operationPlan;
	}

	@Transient
	@ChinaAno(str="客户",order=0)
	public String getCustom() {
		return plan.getBill().getCustom();
	}
	@Transient
	@ChinaAno(str="订单号",order=1)
	public String getBillid() {
		return plan.getBill().getBillid();
	}
	@Transient
	@ChinaAno(str="图号",order=2)
	public String getPiclid() {
		return plan.getBill().getPicid();
	}
	public Plan getPlan() {
		return plan;
	}


	@ChinaAno(str="成品数",order=6)
	public int getProductNum() {
		return productNum;
	}

	@ChinaAno(str="报废数",order=7)
	public int getScrapNum() {
		return scrapNum;
	}

	@ChinaAno(str="报废原因",order=8)
	public String getScrapReason() {
		return scrapReason;
	}
	@Transient
	@ChinaAno(str="未完工数量",order=9)
	public String getUncompletedNum() {
		return scrapReason;
	}
	@ChinaAno(str="操作用时",order=10)
	public float getUseTime() {
		return useTime;
	}
	@ChinaAno(str="单件操作用时",order=11)
	public float getUnitTime() {
		return useTime/(getProductNum()+getScrapNum());
	}
	@ChinaAno(str="计划单件用时",order=12)
	public float getPlanUnitTime() {
		return plan.getUserTime()/plan.getNum();
	}

	@ChinaAno(str="调机时间",order=13)
	public float getPrepareTime() {
		return prepareTime;
	}
	@ChinaAno(str="调机金额",order=14)
	public float getPrepareCost() {
		return prepareTime*employee.getWage();
	}
	@ChinaAno(str="检验员",order=15)
	public Employee getChecker() {
		return checker;
	}

	@ChinaAno(str="完工日期",order=16)
	public Date getFinishDate() {
		return finishDate;
	}
	@ChinaAno(str="备注",order=17)
	public String getNote() {
		return note;
	}
	public void setChecker(Employee checker) {
		this.checker = checker;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public void setGetNum(int getNum) {
		this.getNum = getNum;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setOperationPlan(OperationPlan operationPlan) {
		this.operationPlan = operationPlan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public void setPrepareTime(float prepareTime) {
		this.prepareTime = prepareTime;
	}
	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}
	public void setScrapNum(int scrapNum) {
		this.scrapNum = scrapNum;
	}
	public void setScrapReason(String scrapReason) {
		this.scrapReason = scrapReason;
	}
	public void setUseTime(float useTime) {
		this.useTime = useTime;
	}
	
	public static void main(String a[]) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException, SQLException {
		new OperationWork().save();
	}
}
