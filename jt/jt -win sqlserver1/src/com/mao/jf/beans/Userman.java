package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.beans.Transient;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class Userman  implements Serializable{
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	private String name;
	private String password;
	private int level;
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
	@Caption(value="级别",order=2)
	public String getLevelStr() {

		switch (level) {
		case 0:
			return "管理员";
		case 1:
			return "统计员";
		case 2 :
			return "计划派工";
		case 3:
			return "仓库管理员";
		default:
			return "未确定";
		}
		

	}

	@Caption(value="姓名",order=1)
	public String getName() {
		return name;
	}

	public String getPassword() {
		return password==null?"111111":password;
	}

	public boolean isManager() {
		// TODO 自动生成的方法存根
		return level==0;
	}

	


	public void setLevel(int level) {
		this.level = level;
	}

	public void setLevelStr(String levels) {
		switch (levels) {
		case "管理员":
			this.level = 0;
			break;
		case "统计员":
			this.level = 1;
			break;
		case "计划派工":
			this.level = 2;
			break;
		case "仓库管理员":
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

	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return name;
	}

}
