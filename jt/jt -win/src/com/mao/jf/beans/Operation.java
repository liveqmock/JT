package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

public class Operation extends BeanMao {
	private String name;  
	private int  num ; 
	private boolean out  ;
	private float cost   ;
	private String note  ;
	


	
	@ChinaAno(order = 1, str = "��������")
	public String getName() {
		return name;
	}
	
	@ChinaAno(order = 3, str = "�豸����")
	public int getNum() {
		return num;
	}
	@ChinaAno(order = 4, str = "�ⷢ")
	public boolean getOut() {
		return out;
	}
	public boolean isOut() {
		return out;
	}
	@ChinaAno(order = 5, str = "����")
	public float getCost() {
		return cost;
	}
	@ChinaAno(order = 6, str = "��ע")
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
		// TODO �Զ����ɵķ������
		return loadAll(Operation.class, "select * from Operation where out ");
	}

	
	
	
	
}
