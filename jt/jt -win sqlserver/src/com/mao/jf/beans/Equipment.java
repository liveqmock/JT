package com.mao.jf.beans;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class Equipment {
	@Id
	@GeneratedValue(strategy = AUTO)
	private int id;
	@ManyToOne
	@JoinColumn(name = "operation", referencedColumnName = "id")
	@Caption("设备类型")
	private Operation operation;
	@Caption("设备名称")
	private String name;
	@Caption("设备编号")
	private int code;
	@Caption("可用性")
	private boolean good=true;

	@Caption("日工作时间")
	private long workTime;
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
		return name==null?(operation==null?"":operation.getName()):name;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public int getCode() {
		return code==0?(operation==null?1:operation.getEquipments().size()):code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public boolean isGood() {
		return good;
	}
	public void setGood(boolean good) {
		this.good = good;
	}
	public long getWorkTime() {
		return workTime;
	}
	public void setWorkTime(long workTime) {
		this.workTime = workTime;
	}
	public void setName(String name) {
		this.name = name;
	}

}
