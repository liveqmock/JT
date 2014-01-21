package com.mao.jf.beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.naming.NamingException;

public class CustomBill extends Custom {

	@Override
	public void save() throws ClassNotFoundException, SQLException,
			NamingException {
		String sql = "";
		if (sysId == 0) {
			sql = "insert into Custom (name,tel ,address,fax ,contact,email) values(?,?,?,?,?,?)";

		} else {
			sql = "update  Custom set name=?,tel=?,address=? ,fax=?,contact =? ,email=? where id="
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

	public static CustomBill Load(String name) {
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement("select * from Custom where name=?")) {
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();
			CustomBill custom = null;
			if (rs.next()) {

				custom = new CustomBill();
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
	public static Vector<CustomBill> LoadAlls() {
		Vector<CustomBill> customs=new Vector<>();
		try (Statement st = SessionData.getConnection().createStatement();
				ResultSet rs = st.executeQuery("select * from Custom ")) {
			while (rs.next()) {
				CustomBill custom;			
				custom = new CustomBill();
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
	public static Vector<String> LoadNames() {
		Vector<String> names = new Vector<>();
		names.add(null);
		try (Statement st = SessionData.getConnection().createStatement();
				ResultSet rs = st
						.executeQuery("select distinct name from custom");) {
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

	public static Vector<String> LoadContacts(String name) {
		Vector<String> contacts = new Vector<>();
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement("select contact from custom where name=?")) {
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				contacts.add(rs.getString(1));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return contacts;

	}

	@Override
	public Vector<Custom> LoadAll() {
		Vector<Custom> customs = new Vector<>();
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement("select * from Custom")) {
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				CustomBill custom = new CustomBill();
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
				.prepareStatement("delete custom where id=?");) {
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
		if (obj instanceof Userman) {
			return this.sysId == ((CustomBill) obj).getSysId();
		} else
			return false;

	}

	@Override
	public Custom clone()  {
		CustomBill custom=new CustomBill();
		custom.setName(name);
		custom.setTel(tel);
		custom.setAddress(address);
		custom.setContact(contact);
		custom.setFax(fax);
		custom.setEmail(tel);
		return custom;
	}
}
