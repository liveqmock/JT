package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class Operation extends BeanMao {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	private String name;  
	private int  num ; 
	private float cost   ;
	private String note  ;
	


	
	@Caption(order = 1, value= "工序名称")
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Caption(order = 3, value= "设备数量")
	public int getNum() {
		return num;
	}
	@Caption(order = 5, value= "费用")
	public float getCost() {
		return cost;
	}
	@Caption(order = 6, value= "备注")
	public String getNote() {
		return note;
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
	@Override
	public String toString() {
		return name;
	}

	public void setNum(int num) {
		this.num = num;
	}

	
	
	
	
	
}
