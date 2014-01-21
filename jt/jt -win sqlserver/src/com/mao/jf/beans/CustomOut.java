package com.mao.jf.beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.naming.NamingException;

public class CustomOut extends Custom {

	@Override
	public void save() throws ClassNotFoundException, SQLException,
			NamingException {
		String sql = "";
		if (sysId == 0) {
			sql = "insert into outCustom (name,tel ,address,fax ,contact,email) values(?,?,?,?,?,?)";

		} else {
			sql = "update  outCustom set name=?,tel=?,address=? ,fax=?,contact =? ,email=? where id="
					+ sysId;
		}
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			pst.setString(1, name);
			pst.setString(2, tel);
			pst.setString(3, address);
			pst.setString(4, fax);
			pst.setString(5, contact);
			pst.setString(6, email);
			pst.execute();
			ResultSet rsKey = pst.getGeneratedKeys();
			if (rsKey != null && rsKey.next())
				this.sysId = rsKey.getInt(1);
		}
	}

	public static CustomOut Load(String name) {
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement("select * from outCustom where name=?")) {
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();
			CustomOut custom = null;
			if (rs.next()) {
				custom = new CustomOut();
				custom.setName(name);
				custom.setSysId(rs.getInt("id"));
				custom.setTel(rs.getString("Tel"));
				custom.setAddress(rs.getString("Address"));
				custom.setContact(rs.getString("Contact"));
				custom.setFax(rs.getString("Fax"));
				custom.setEmail(rs.getString("email"));

			}

			return custom;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static Vector<String> LoadNames() {
		Vector<String> names = new Vector<>();
		names.add(null);
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement("select name from outcustom")) {
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				names.add(rs.getString(1));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return names;

	}

	@Override
	public Vector<Custom> LoadAll() {
		Vector<Custom> customs = new Vector<>();
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement("select * from outCustom")) {
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				CustomOut custom = new CustomOut();
				custom.setName(rs.getString("name"));
				custom.setSysId(rs.getInt("id"));
				custom.setTel(rs.getString("Tel"));
				custom.setAddress(rs.getString("Address"));
				custom.setContact(rs.getString("Contact"));
				custom.setFax(rs.getString("Fax"));
				custom.setEmail(rs.getString("email"));
				customs.add(custom);

			}

			return customs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void remove() {
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement("delete outcustom where id=?");) {
			pst.setInt(1, sysId);
			pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Custom clone()  {
		CustomOut custom=new CustomOut();
		custom.setName(name);
		custom.setTel(tel);
		custom.setAddress(address);
		custom.setContact(contact);
		custom.setFax(fax);
		custom.setEmail(tel);
		return custom;
	}
}
