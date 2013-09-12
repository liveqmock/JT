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
			// TODO �Զ����ɵ� catch ��
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
	 * ����ʱ��
	 */
	private float prepareTime;
	
	@ChinaAno(str="����Ա",order=4)
	public Employee getEmployee() {
		return employee;
	}



	@ChinaAno(str="ʵ����",order=4)
	public int getGetNum() {
		return getNum;
	}


	@ChinaAno(str="����",order=3)
	public OperationPlan getOperationPlan() {
		return operationPlan;
	}

	@Transient
	@ChinaAno(str="�ͻ�",order=0)
	public String getCustom() {
		return plan.getBill().getCustom();
	}
	@Transient
	@ChinaAno(str="������",order=1)
	public String getBillid() {
		return plan.getBill().getBillid();
	}
	@Transient
	@ChinaAno(str="ͼ��",order=2)
	public String getPiclid() {
		return plan.getBill().getPicid();
	}
	public Plan getPlan() {
		return plan;
	}


	@ChinaAno(str="��Ʒ��",order=6)
	public int getProductNum() {
		return productNum;
	}

	@ChinaAno(str="������",order=7)
	public int getScrapNum() {
		return scrapNum;
	}

	@ChinaAno(str="����ԭ��",order=8)
	public String getScrapReason() {
		return scrapReason;
	}
	@Transient
	@ChinaAno(str="δ�깤����",order=9)
	public String getUncompletedNum() {
		return scrapReason;
	}
	@ChinaAno(str="������ʱ",order=10)
	public float getUseTime() {
		return useTime;
	}
	@ChinaAno(str="����������ʱ",order=11)
	public float getUnitTime() {
		return useTime/(getProductNum()+getScrapNum());
	}
	@ChinaAno(str="�ƻ�������ʱ",order=12)
	public float getPlanUnitTime() {
		return plan.getUserTime()/plan.getNum();
	}

	@ChinaAno(str="����ʱ��",order=13)
	public float getPrepareTime() {
		return prepareTime;
	}
	@ChinaAno(str="�������",order=14)
	public float getPrepareCost() {
		return prepareTime*employee.getWage();
	}
	@ChinaAno(str="����Ա",order=15)
	public Employee getChecker() {
		return checker;
	}

	@ChinaAno(str="�깤����",order=16)
	public Date getFinishDate() {
		return finishDate;
	}
	@ChinaAno(str="��ע",order=17)
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
