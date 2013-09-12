package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

public class Employee extends BeanMao {
	private String name;
	private Operation operation ;
	private float wage;
	private String employeeType;
	
	@ChinaAno(order = 1, str = "姓名")
	public String getName() {
		return name;
	}
	@ChinaAno(order = 2, str = "操作工序")
	public Operation getOperation() {
		return operation;
	}
	@ChinaAno(order = 3, str = "工资")
	public float getWage() {
		return wage;
	}
	@ChinaAno(order = 4, str = "类型")
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
		// TODO 自动生成的方法存根
		return name;
	}
	public static Vector<Employee> loadCheckers() {
		// TODO 自动生成的方法存根
		try {
			return loadAll(Employee.class, "select * from employee where employeeType='检验员'");
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
	public static Vector<Employee> loadOperaters() {
		// TODO 自动生成的方法存根
		try {
			return loadAll(Employee.class, "select * from employee where employeeType='操作员'");
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
	
	
}
