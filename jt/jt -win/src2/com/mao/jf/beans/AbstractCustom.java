package com.mao.jf.beans;

import java.sql.SQLException;
import java.util.Vector;

import javax.naming.NamingException;

public abstract class AbstractCustom {
	protected int sysId;
	protected String name;
	protected String tel;
	protected String address;
	protected String fax;
	protected String contact;
	protected String email;

	public AbstractCustom() {
		super();
		this.name = "";
		this.tel = "";
		this.address = "";
		this.fax = "";
		this.contact = "";
		this.email = "";
	}

	public abstract AbstractCustom clone();

	@ChinaAno(order = 2, str = "��ַ")
	public String getAddress() {
		return address;
	}

	@ChinaAno(order = 3, str = "��ϵ��")
	public String getContact() {
		return contact;
	}

	@ChinaAno(order = 6, str = "Email")
	public String getEmail() {
		return email;
	}

	@ChinaAno(order = 5, str = "����")
	public String getFax() {
		return fax;
	}

	@ChinaAno(order =1, str = "�ͻ�����")
	public String getName() {
		return name;
	}

	public int getSysId() {
		return sysId;
	}

	@ChinaAno(order =4, str = "�绰")
	public String getTel() {
		return tel;
	}

	public abstract Vector<AbstractCustom> LoadAll();

	public abstract void remove();

	public abstract void save() throws ClassNotFoundException, SQLException,
			NamingException;

	public void setAddress(String address) {
		this.address = address;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSysId(int sysId) {
		this.sysId = sysId;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

}
