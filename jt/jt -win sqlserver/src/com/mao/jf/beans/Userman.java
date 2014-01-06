package com.mao.jf.beans;

import java.beans.Transient;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mao.jf.beans.annotation.Caption;

public class Userman extends BeanMao {
	public static Userman Load(String name) {
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement("select * from userman where name=?")) {
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();

			Userman user = null;
			if (rs.next()) {
				user = new Userman();
				user.setName(name);
				user.setPassword(rs.getString("password"));
				user.setLevel(rs.getInt("level"));
				user.setId(rs.getInt("id"));

			}

			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	private String name;
	private String password;
	private int level;


	public static Userman loginUser;

	public Userman() {
		// TODO Auto-generated constructor stub
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

	public boolean load() {
		try (Statement statement = SessionData.getConnection()
				.createStatement();
				ResultSet rs = statement
						.executeQuery("select * from userman where name='"
								+ name + "' and password='" + password + "'")) {

			if (rs.next()) {
				setLevel(rs.getInt("level"));
				setId(rs.getInt("id"));
				loginUser = this;
				return true;
			} else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

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
