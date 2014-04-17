package com.mao.jf.beans;

import static javax.persistence.GenerationType.AUTO;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class FpBean {
	@Id
	@GeneratedValue(strategy = AUTO)
	private int id;
	@ManyToOne
	@JoinColumn(name = "bill", referencedColumnName = "id")
	private BillBean bill;
	@Caption("发票号码")
	private String fpNo;
	@Caption("开票金额")
	private float money;
	@Caption("开票时间")
	private Date createDate;
	
	@OneToOne
	@JoinColumn(name = "inputUser", referencedColumnName = "id")
	private Userman inputUser;
	
	
	public BillBean getBill() {
		return bill;
	}
	public void setBill(BillBean bill) {
		this.bill = bill;
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
	public String getFpNo() {
		return fpNo;
	}
	public void setFpNo(String fpNo) {
		this.fpNo = fpNo;
	}
	
	@PostPersist
	public void refreshBill(){
		BeanMao.beanManager.refresh(bill);
	}
}
