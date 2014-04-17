package com.mao.jf.beans;

import static javax.persistence.GenerationType.AUTO;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ChangeLog {
	@Id
	@GeneratedValue(strategy = AUTO)
	private int id;
	private Userman changeUser;
	private Date changeDate;
	private String changeContent;
	@ManyToOne
	@JoinColumn(name = "bill", referencedColumnName = "id")
	private BillBean bill;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Userman getChangeUser() {
		return changeUser;
	}
	public void setChangeUser(Userman changeUser) {
		this.changeUser = changeUser;
	}
	public Date getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	public String getChangeContent() {
		return changeContent;
	}
	public void setChangeContent(String changeContent) {
		this.changeContent = changeContent;
	}
	
}
