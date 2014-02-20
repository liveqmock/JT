package com.mao.jf.beans;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Equipment {
	@Id
	@GeneratedValue(strategy = AUTO)
	private int id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "operation", referencedColumnName = "id")
	private Operation operation;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
}
