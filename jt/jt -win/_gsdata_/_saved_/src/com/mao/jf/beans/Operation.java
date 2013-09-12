package com.mao.jf.beans;

public class Operation extends BeanMao {
	private String name;  
	private int  num ; 
	private boolean out  ;
	private double cost   ;
	private String note  ;
	


	
	@ChinaAno(order = 1, str = "工序名称")
	public String getName() {
		return name;
	}
	
	@ChinaAno(order = 3, str = "设备数量")
	public int getNum() {
		return num;
	}
	@ChinaAno(order = 4, str = "外发")
	public boolean getOut() {
		return out;
	}
	public boolean isOut() {
		return out;
	}
	@ChinaAno(order = 5, str = "费用")
	public double getCost() {
		return cost;
	}
	@ChinaAno(order = 6, str = "备注")
	public String getNote() {
		return note;
	}
	

	public void setCost(double cost) {
		this.cost = cost;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setOut(boolean out) {
		this.out = out;
	}

	@Override
	public String toString() {
		return name;
	}

	public void setNum(int num) {
		this.num = num;
	}

	
	
	
	
}
