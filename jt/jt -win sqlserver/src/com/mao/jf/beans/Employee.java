package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.beans.Transient;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class Employee extends BeanMao {
	private static float convert=60*8*26;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	private String name;
	private Operation operation ;
	private float wage;
	private String employeeType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public static List<Employee> loadCheckers() {
		
			return loadAll(Employee.class, " a.employeeType='����Ա'");
		
	}
	public static List<Employee> loadOperaters() {
		
				return loadAll(Employee.class, "a.employeeType='����Ա'");
		
	}
	
	
}
