package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.beans.Transient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class Material  extends BeanMao{
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "bill", referencedColumnName = "id")
	private Bill bill; 
	@Caption("材料（工序）名称")
	private String name ;
	@Caption("计价单位")
	private String unitName ;
	@Caption("单价")
	private float unitCost ;
	@Caption("数量")
	private float num ;
	@Caption("输入员")
	private Userman enterEmployee ;
	
	public Material() {
	}
	public Material(Bill bill) {
		super();
		this.bill = bill;
	}
	public int getId() {
		return id;
	}
	public Bill getBill() {
		return bill;
	}
	public String getName() {
		return name;
	}
	public String getUnitName() {
		return unitName;
	}
	public float getUnitCost() {
		return unitCost;
	}
	public float getNum() {
		return num;
	}
	public Userman getEnterEmployee() {
		return enterEmployee;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}
	public void setNum(float num) {
		this.num = num;
	}
	public void setEnterEmployee(Userman enterEmployee) {
		this.enterEmployee = enterEmployee;
	}
	
	@Transient
	@Caption(value="订单号",order=-1)
	public String getBillNo() {
		return bill.getBillNo();
	}
}
