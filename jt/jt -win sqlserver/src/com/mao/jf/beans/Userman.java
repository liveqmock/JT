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

	@Override
	public String toString() {
		// TODO �Զ����ɵķ������
		return name;
	}

}
