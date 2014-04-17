package com.mao.bean;

import static javax.persistence.GenerationType.AUTO;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.mao.annotations.Caption;
import com.mao.annotations.Hidden;

import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(schema = "dataCenter")
public class MarketInfo {
	@Id
	@GeneratedValue(strategy = AUTO)
	@Hidden
	private int id;
	@Caption("Ӫ����Ա")
	@OneToOne
	@JoinColumn(name = "marketEmployee", referencedColumnName = "STCASTAF")
	private Employee marketEmployee;
	
	@Caption("����")
	private Date marketDate;
	@Caption("ҵ������")
	@ManyToOne
	@JoinColumn(name = "business", referencedColumnName = "id")
	private BusinessType business;
	@Caption("�˺�")
	private String acctNo;
	@Caption("�ͻ�����")
	private String custName;
	@OneToOne
	@JoinColumn(name = "inputUser", referencedColumnName = "id")
	@Hidden
	private User inputUser;

	@Hidden
	private Date inputDate;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Employee getMarketEmployee() {
		return marketEmployee;
	}
	public void setMarketEmployee(Employee marketEmployee) {
		this.marketEmployee = marketEmployee;
	}
	public User getInputUser() {
		return inputUser;
	}
	public void setInputUser(User inputUser) {
		this.inputUser = inputUser;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public Date getMarketDate() {
		return marketDate;
	}
	public void setMarketDate(Date marketDate) {
		this.marketDate = marketDate;
	}

	public BusinessType getBusiness() {
		return business;
	}
	public void setBusiness(BusinessType business) {
		this.business = business;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	
}
