package com.mao.jf.beans;

public class Employee extends BeanMao {
	private String name;
	private Operation operation ;
	private double wage;
	
	@ChinaAno(order = 1, str = "姓名")
	public String getName() {
		return name;
	}
	@ChinaAno(order = 2, str = "操作工序")
	public Operation getOperation() {
		return operation;
	}
	@ChinaAno(order = 3, str = "工资")
	public double getWage() {
		return wage;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public void setWage(double wage) {
		this.wage = wage;
	}
	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return name;
	}
	
	
}
