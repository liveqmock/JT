package com.mao.jf.beans;

import static javax.persistence.GenerationType.AUTO;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OutFpBean {
	@Id
	@GeneratedValue(strategy = AUTO)
	private int id;
	@ManyToOne
	@JoinColumn(name = "bill", referencedColumnName = "id")
	private BillBean bill;
	private String FpNo;
	private float money;
	private Date createDate;
	private Userman inputUser;
	public BillBean getBill() {
		return bill;
	}
	public void setBill(BillBean bill) {
		this.bill = bill;
	}
	public String getFpNo() {
		return FpNo;
	}
	public void setFpNo(String fpNo) {
		FpNo = fpNo;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Userman getInputUser() {
		return inputUser;
	}
	public void setInputUser(Userman inputUser) {
		this.inputUser = inputUser;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
