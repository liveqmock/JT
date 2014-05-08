package com.mao.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "core", name = "BBFMSTLR")
public class Employee {
	
	@Id
	@Column(name = "STCASTAF")
	private String id;

	@ManyToOne
	@JoinColumn(name = "STCABRNO", referencedColumnName = "ORCABRNO")
	private Department department;
	@Column(name = "STCANM20")
	private String name;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
	
}
