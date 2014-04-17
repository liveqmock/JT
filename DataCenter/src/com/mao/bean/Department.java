package com.mao.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity

@Table(schema = "core", name = "BBFMORGA")
public class Department {
	
	@Id	
	@Column(name = "ORCABRNO")
	private String id;
	
	@Column(name = "ORCAFLNM")
	private String name;
	
	@Column(name = "ORCABRLV")
	private String level;
	

	@ManyToOne
	@JoinColumn(name = "ORCFBRNO", referencedColumnName = "ORCABRNO")
	private Department topDepartment;
	
	@OneToMany(mappedBy = "topDepartment")
	private  Collection<Department> departments;
	
	@OneToMany(mappedBy = "department")
	private Collection<Employee > employees;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Department getTopDepartment() {
		return topDepartment;
	}

	public void setTopDepartment(Department topDepartment) {
		this.topDepartment = topDepartment;
	}

	public Collection<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Collection<Department> departments) {
		this.departments = departments;
	}

	public Collection<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Collection<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return name;
	}

	public Collection<Employee> getAllEmployees() {
		List<Employee> list=new ArrayList<Employee>();
		for(Department department:getDepartments())
			list.addAll(department.getEmployees());
		
		return list;
	}
	
}
