package com.jxjtjm.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.jxjtjm.beanPanels.Caption;
import com.jxjtjm.beanPanels.Hidden;

import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Contact {
	@Id
	@Hidden
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	@Caption("手机号")
	private String telNo;
	@Caption("姓名")
	private String name;
	@Caption("部门")
	private String part;
	
	public void sendSms(String smsString) {
		Sms sms= new Sms();
		sms.setTelNo(telNo);
		sms.setReceiveName(name);
		sms.setContent(smsString);
		sms.setSendDate(new Date());
		sms.send();
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return name;
	}
	
}
