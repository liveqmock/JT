package com.mao.jf.beans;

import java.sql.SQLException;
import java.util.Vector;

import javax.naming.NamingException;

import com.mao.jf.beans.annotation.Caption;

public abstract class AbstractCustom {
	protected int sysId;
	@Caption("����")
	protected String name;
	@Caption("�绰")
	protected String tel;
	@Caption("��ַ")
	protected String address;
	@Caption("����")
	protected String fax;
	@Caption("��ϵ��")
	protected String contact;
	@Caption("Email")
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

	public String getAddress() {
		return address;
	}

	public String getContact() {
		return contact;
	}

	public String getEmail() {
		return email;
	}
	public String getFax() {
		return fax;
	}

	public String getName() {
		return name;
	}

	public int getSysId() {
		return sysId;
	}

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
