package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.mao.jf.beans.annotation.Caption;

@Entity
public  class Custom extends BeanMao {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	@Caption("姓名")
	private String name;
	@Caption("电话")
	private String tel;
	@Caption("地址")
	private String address;
	@Caption("传真")
	private String fax;
	@Caption("联系人")
	private String contact;
	@Caption("Email")
	private String email;
	private int out;
	public Custom() {
		super();

		this.name = "";
		this.tel = "";
		this.address = "";
		this.fax = "";
		this.contact = "";
		this.email = "";
	}

	public Custom(int out) {
		this.out=out;
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

	public int getOut() {
		return out;
	}

	public void setOut(int out) {
		this.out = out;
	}

	public static Set<String> loadNames(int out) {
		HashSet<String> names=new HashSet<>();
		for(Custom custom:loadAll(Custom.class," a.out="+out)){
			names.add(custom.name);
		}
		return names;
	}
	public static Set<String> loadContacts(String name, int out) {
		HashSet<String> names=new HashSet<>();
		for(Custom custom:loadAll(Custom.class," a.out="+out+" a.name='"+name+"'")){
			names.add(custom.contact);
		}
		return names;
	}
}
