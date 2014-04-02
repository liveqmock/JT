package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.beans.Transient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class Material  extends BeanMao{
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "bill", referencedColumnName = "id")
	private Bill bill; 
	@Caption("���ϣ���������")
	private String name ;
	@Caption("�Ƽ۵�λ")
	private String unitName ;
	@Caption("����")
	private float unitCost ;
	@Caption("����")
	private float num ;
	@Caption("����Ա")
	@OneToOne
	@JoinColumn(name = "enterEmployee", referencedColumnName = "id")
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
	@Caption(value="ͼ��",order=-1)
	public String getBillNo() {
		return bill.getPicid();
	}
}
