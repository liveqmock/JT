package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.beans.Transient;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import com.mao.jf.beans.annotation.Caption;

public class Employee extends BeanMao {
	private static float convert=60*8*26;
	private String name;
	private Operation operation ;
	private float wage;
	private String employeeType;
	
	@Caption(order = 1, value= "����")
	public String getName() {
		return name;
	}
	@Caption(order = 2, value= "��������")
	public Operation getOperation() {
		return operation;
	}
	@Caption(order = 3, value= "�ֹ���")
	public float getWage() {
		return wage;
	}
	
	@Transient
	@Caption(order = 4, value= "�¹���")
	public float getMonWage() {
		return (int)(wage*convert);
	}
	@Caption(order = 5, value= "����")
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public void setMonWage(float wage) {
		this.wage = wage/convert;
	}
	public void setWage(float wage) {
		this.wage = wage;
	}
	@Override
	public String toString() {
		// TODO �Զ����ɵķ������
		return name;
	}
	public static Vector<Employee> loadCheckers() {
		// TODO �Զ����ɵķ������
		try {
			return loadAll(Employee.class, "select * from employee where employeeType='����Ա'");
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return null;
	}
	public static Vector<Employee> loadOperaters() {
		// TODO �Զ����ɵķ������
		try {
			return loadAll(Employee.class, "select * from employee where employeeType='����Ա'");
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return null;
	}
	
	
}
