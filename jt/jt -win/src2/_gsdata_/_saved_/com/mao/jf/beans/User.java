package com.mao.jf.beans;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.naming.NamingException;

public class User implements Serializable {
	private int sysId;
	private String name;
	private String password;
	private int level;
	public static User loginUser;

	public void save() throws ClassNotFoundException, SQLException,
			NamingException {
		String sql = "";
		if (sysId == 0) {
			sql = "insert into \"user\" (name,password ,level) values(?,?,?)";

		} else {
			sql = "update  \"user\" set name=?,password=?,level=? where id="
					+ sysId;
		}
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			pst.setString(1, name);
			pst.setString(2, password);
			pst.setInt(3, level);
			pst.execute();
			ResultSet rsKey = pst.getGeneratedKeys();
			if (rsKey != null && rsKey.next())
				this.sysId = rsKey.getInt(1);
		}
	}

	public static Vector<User> LoadAll() {
		Vector<User> users = new Vector<>();
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement("select * from \"user\" ")) {
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setLevel(rs.getInt("level"));
				user.setSysId(rs.getInt("id"));
				users.add(user);
			}

			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public boolean load() {
		try (Statement statement = SessionData.getConnection()
				.createStatement();
				ResultSet rs = statement
						.executeQuery("select * from \"user\" where name='"
								+ name + "' and password='" + password + "'")) {

			if (rs.next()) {
				setLevel(rs.getInt("level"));
				setSysId(rs.getInt("id"));
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

	public static User Load(String name) {
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement("select * from \"user\" where name=?")) {
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();

			User user = null;
			if (rs.next()) {
				user = new User();
				user.setName(name);
				user.setPassword(rs.getString("password"));
				user.setLevel(rs.getInt("level"));
				user.setSysId(rs.getInt("id"));

			}

			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getLevels() {
		switch (level) {
		case 0:
			return "管理用户";
		case 1:
			return "车间用户";
		default:
			return null;
		}

	}

	public void setLevels(String levels) {
		switch (levels) {
		case "管理用户":
			this.level = 0;
			break;
		case "车间用户":
			this.level = 1;
			break;
		default:
			break;
		}

	}

	public User(String name, String passwd) {
		super();
		this.name = name;
		this.password = passwd;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSysId() {
		return sysId;
	}

	public void setSysId(int sysId) {
		this.sysId = sysId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void remove() {
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement("delete \"user\" where id=?");) {
			pst.setInt(1, sysId);
			pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		isEqual = super.equals(obj);
		if (isEqual)
			return isEqual;
		if (obj instanceof User) {
			return this.sysId == ((User) obj).getSysId();
		} else
			return false;

	}

}
