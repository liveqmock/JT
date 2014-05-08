package com.mao.jf.beans;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class Userman  implements Serializable{
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	private String name;
	private String password="111111";
	private int level;
	@ElementCollection(fetch = EAGER)
	@CollectionTable(joinColumns = @JoinColumn(name = "Userman", referencedColumnName = "id"))
	@Column(name = "menu")
	private Collection<String> menus;
	public static Userman loginUser;

	public Userman() {
		super();
	}

	public Userman(String name, String passwd) {
		super();
		this.name = name;
		this.password = passwd;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		isEqual = super.equals(obj);
		if (isEqual)
			return isEqual;
		if (obj instanceof Userman) {
			return this.getId() == ((Userman) obj).getId();
		} else
			return false;

	}

	public int getLevel() {
		return level;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Transient
	@Caption(value="����",order=2)
	public String getLevelStr() {

		switch (level) {
		case 0:
			return "����Ա";
		case 1:
			return "ͳ��Ա";
		case 2 :
			return "�ƻ��ɹ�";
		case 3:
			return "�ֿ����Ա";
		default:
			return "δȷ��";
		}
		

	}

	@Caption(value="����",order=1)
	public String getName() {
		return name;
	}

	public String getPassword() {
		return password==null?"111111":password;
	}

	public boolean isManager() {
		// TODO �Զ����ɵķ������
		return level==0;
	}

	


	public void setLevel(int level) {
		this.level = level;
	}

	public void setLevelStr(String levels) {
		switch (levels) {
		case "����Ա":
			this.level = 0;
			break;
		case "ͳ��Ա":
			this.level = 1;
			break;
		case "�ƻ��ɹ�":
			this.level = 2;
			break;
		case "�ֿ����Ա":
			this.level = 3;
			break;
		default:
			break;
		}

	}

	

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<String> getMenus() {
		if(menus==null)menus=new ArrayList<>();
		return menus;
	}

	public void setMenus(Collection<String> menus) {
		this.menus = menus;
	}

	@Override
	public String toString() {
		// TODO �Զ����ɵķ������
		return name;
	}

}
