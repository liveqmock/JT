package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class Employee extends BeanMao {
	private static float convert=60*8*26;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	@Caption(order = 1, value= "����")
	private String name;
	
	@OneToOne
	@JoinColumn(name = "operation", referencedColumnName = "id")
	@Caption(order = 2, value= "��������")
	private Operation operation ;

	@Caption(order = 3, value= "�ֹ���")
	private float wage;
	
	@Caption(order = 5, value= "�Ƿ�Ϊ����Ա")
	private boolean operationer;
	@Caption(order = 6, value= "�Ƿ�Ϊ����Ա")
	private boolean checker;
	@Caption(order = 6, value= "�Ƿ�Ϊ����")
	private boolean superintendent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public Operation getOperation() {
		return operation;
	}
	public float getWage() {
		return wage;
	}
	@Caption(order = 4, value= "�¹���")
	public float getMonWage() {
		return (int)(wage*convert);
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
		
			return getBeans(Employee.class, " checker=true");
		
	}
	public static List<Employee> loadOperaters() {
		
				return getBeans(Employee.class, " operationer=true");
		
	}
	
	public static String[] getNames() {
		List<String> list = BeanMao.beanManager.queryList("select name from Employee", String.class);
		String[] names=null;
		if(list!=null){
			list.add(0, "");
			names=new String[list.size()];
			list.toArray(names);
		}
			return names;
		
	}
	public static float getConvert() {
		return convert;
	}
	public static void setConvert(float convert) {
		Employee.convert = convert;
	}
	public boolean isChecker() {
		return checker;
	}
	public void setChecker(boolean checker) {
		this.checker = checker;
	}
	public boolean isSuperintendent() {
		return superintendent;
	}
	public void setSuperintendent(boolean superintendent) {
		this.superintendent = superintendent;
	}
	public boolean isOperationer() {
		return operationer;
	}
	public void setOperationer(boolean operationer) {
		this.operationer = operationer;
	}
	public static Collection<? extends Employee> loadSuperintendents() {

		return getBeans(Employee.class, " superintendent=true");
	}
	public static Collection<? extends Employee> loadAll() {

		return getBeans(Employee.class);
	}
}
