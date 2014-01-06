package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import com.mao.jf.beans.annotation.Caption;

public class Operation extends BeanMao {
	private String name;  
	private int  num ; 
	private boolean out  ;
	private float cost   ;
	private String note  ;
	


	
	@Caption(order = 1, value= "工序名称")
	public String getName() {
		return name;
	}
	
	@Caption(order = 3, value= "设备数量")
	public int getNum() {
		return num;
	}
	@Caption(order = 4, value= "外发")
	public boolean getOut() {
		return out;
	}
	public boolean isOut() {
		return out;
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

	public static Vector<Operation> loadOutAll() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
		// TODO 自动生成的方法存根
		return loadAll(Operation.class, "select * from Operation where out ");
	}

	
	
	
	
}
