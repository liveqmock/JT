package com.mao.jf.beans;

public class Operation extends BeanMao {
	private String name;  
	private String unit ; 
	private boolean out  ;
	private double cost   ;
	private String note  ;
	

	@ChinaAno(order = 5, str = "费用")
	public double getCost() {
		return cost;
	}
	
	@ChinaAno(order = 1, str = "工序名称")
	public String getName() {
		return name;
	}
	@ChinaAno(order = 6, str = "备注")
	public String getNote() {
		return note;
	}
	@ChinaAno(order = 2, str = "计量单位")
	public String getUnit() {
		return unit;
	}
	@ChinaAno(order = 3, str = "外发")
	public boolean isOut() {
		return out;
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
	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return name;
	}
	
	
	
	
}
