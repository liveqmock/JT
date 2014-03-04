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
	@ManyToOne
	@JoinColumn(name = "operation", referencedColumnName = "id")
	private Operation operation;
	private int code;
	public Equipment() {
	}
	public Equipment(Operation operation) {
		this.operation=operation;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return operation.getName()+" #"+code;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

}
