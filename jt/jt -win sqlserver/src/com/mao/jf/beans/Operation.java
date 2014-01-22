package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.mao.jf.beans.annotation.Caption;

import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	
	
	
	
}
