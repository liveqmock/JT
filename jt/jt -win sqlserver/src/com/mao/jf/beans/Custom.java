package com.mao.jf.beans;

import java.sql.SQLException;
import java.util.Vector;

import javax.naming.NamingException;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.mao.jf.beans.annotation.Caption;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public  class Custom {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	protected int id;
	@Caption("姓名")
	protected String name;
	@Caption("电话")
	protected String tel;
	@Caption("地址")
	protected String address;
	@Caption("传真")
	protected String fax;
	@Caption("联系人")
	protected String contact;
	@Caption("Email")
	protected String email;

	public Custom() {
		super();

		this.name = "";
		this.tel = "";
		this.address = "";
		this.fax = "";
		this.contact = "";
		this.email = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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


	public String getTel() {
		return tel;
	}

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

	public void setTel(String tel) {
		this.tel = tel;
	}

}
