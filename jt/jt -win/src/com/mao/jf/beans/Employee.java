package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

public class Employee extends BeanMao {
	private String name;
	private Operation operation ;
	private float wage;
	private String employeeType;
	
	@ChinaAno(order = 1, str = "����")
	public String getName() {
		return name;
	}
	@ChinaAno(order = 2, str = "��������")
	public Operation getOperation() {
		return operation;
	}
	@ChinaAno(order = 3, str = "����")
	public float getWage() {
		return wage;
	}
	@ChinaAno(order = 4, str = "����")
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
