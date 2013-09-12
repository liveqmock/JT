package com.mao.jf.beans;

public class Operation extends BeanMao {
	private String name;  
	private String unit ; 
	private boolean out  ;
	private double cost   ;
	private String note  ;
	

	@ChinaAno(order = 5, str = "����")
	public double getCost() {
		return cost;
	}
	
	@ChinaAno(order = 1, str = "��������")
	public String getName() {
		return name;
	}
	@ChinaAno(order = 6, str = "��ע")
	public String getNote() {
		return note;
	}
	@ChinaAno(order = 2, str = "������λ")
	public String getUnit() {
		return unit;
	}
	@ChinaAno(order = 3, str = "�ⷢ")
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
		// TODO �Զ����ɵķ������
		return name;
	}
	
	
	
	
}
