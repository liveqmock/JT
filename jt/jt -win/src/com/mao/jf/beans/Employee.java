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
	
	@Caption(order = 1, value= "姓名")
	public String getName() {
		return name;
	}
	@Caption(order = 2, value= "操作工序")
	public Operation getOperation() {
		return operation;
	}
	@Caption(order = 3, value= "分工资")
	public float getWage() {
		return wage;
	}
	
	@Transient
	@Caption(order = 4, value= "月工资")
	public float getMonWage() {
		return (int)(wage*convert);
	}
	@Caption(order = 5, value= "类型")
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
