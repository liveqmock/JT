package com.mao.jf.beans;

public class OperationTime extends BeanMao {

	private Bill bill;
	private String operationName;

	private int plantime;
	public Bill getBill() {
		return bill;
	}
	public String getOperationName() {
		return operationName;
	}
	public int getPlantime() {
		return plantime;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public void setPlantime(int plantime) {
		this.plantime = plantime;
	}
	

}
