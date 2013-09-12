package com.mao.jf.beans;

import java.beans.Transient;
import java.util.Date;

public class OperationWork extends BeanMao {
	
	
	private OperationPlan operationPlan;
	private Employee employee;
	private Date finishDate;
	private int getNum;
	private int productNum;
	private int scrapNum;
	private String scrapReason;
	private float workTime;
	private float workCost;
	private Employee checker;
	private Employee prepareEmployee;
	private float prepareTime;
	private float prepareCost;
	private float planCost;
	
	private String note;
	public OperationWork() {
		super();
	}

	public OperationWork(OperationPlan operationPlan) {
		
		setOperationPlan(operationPlan);
	}
	@ChinaAno(str="������",order=1)
	@Transient
	public String getOperationName() {
		return operationPlan.getName();
	}
	@ChinaAno(str="����Ա",order=17)
	public Employee getChecker() {
		return checker;
	}
	@ChinaAno(str="����Ա",order=4)
	public Employee getEmployee() {
		return employee;
	}

	@ChinaAno(str="�깤����",order=18)
	public Date getFinishDate() {
		return finishDate;
	}


	@ChinaAno(str="ʵ����",order=4)
	public int getGetNum() {
		return getNum;
	}
	@ChinaAno(order =99, str = "��ע")
	public String getNote() {
		return note;
	}
	public OperationPlan getOperationPlan() {
		return operationPlan;
	}
	public Plan getPlan() {
		return getOperationPlan().getPlan();
	}
	@ChinaAno(order = 7, str = "�ƻ�����")
	public float getPlanCost() {
		return planCost==0?getOperationPlan().getPlanCost():planCost;
	}
	public void setPlanCost(float planCost) {
		this.planCost = planCost;
	}


	@ChinaAno(str="�ӹ�����",order=11)
	public float getWorkCost() {
		return workCost==0?(employee==null?0: workTime*employee.getWage()):workCost;
	}
	public void setWorkCost(float workCost) {
		this.workCost = workCost;
	}


	@ChinaAno(str="��������",order=14)
	public float getPrepareCost() {
		return prepareCost==0?(prepareEmployee==null?0: prepareTime*prepareEmployee.getWage()):prepareCost;
	}
	@ChinaAno(str="������",order=12)
	public Employee getPrepareEmployee() {
		return prepareEmployee;
	}
	@ChinaAno(str="����ʱ��",order=13)
	public float getPrepareTime() {
		return prepareTime;
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
	public int getUncompletedNum() {
		return getNum-productNum-scrapNum;
	}





	@Transient
	@ChinaAno(order = 15, str = "ʵ�ʷ���")
	public float getCost() {
		return getWorkCost()+getPrepareCost();
	}
	

	@ChinaAno(order = 10, str = "�ӹ���ʱ")
	public float getWorkTime() {
		return workTime;
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

	public void setPrepareCost(float prepareCost) {
		this.prepareCost = prepareCost;
	}

	public void setPrepareEmployee(Employee prepareEmployee) {
		this.prepareEmployee = prepareEmployee;
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

	public void setWorkTime(float workTime) {
		this.workTime = workTime;
	}
	

}
