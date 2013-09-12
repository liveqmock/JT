package com.mao.jf.beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

public class BackRepair {
	private int id;
	private Bill billItem ;
	private long backNum ;
	private Date backDate;
	private Date getDate ;
	private String note ;
	public void remove() {
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement("delete BackRepair where id=?");) {
			pst.setInt(1, id);
			pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void save() throws SQLException{
		String sql = "";
		if (id == 0) {
			sql = "insert into BackRepair (billid ,backDate,getDate,backNum,note) values(?,?,?,?,?)";

		} else {
			sql = "update  BackRepair set billid =?,backDate=?,getDate=?,backNum=?,note=? where id="
					+ id;
		}
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			pst.setInt(1, billItem.getId());
			pst.setDate(2, backDate==null?null:new  java.sql.Date(backDate.getTime()));
			pst.setDate(3, getDate==null?null:new  java.sql.Date(getDate.getTime()));
			pst.setLong(4, backNum);
			pst.setString(5, note);
			pst.execute();
			ResultSet rsKey = pst.getGeneratedKeys();
			if (rsKey != null && rsKey.next())
				this.id = rsKey.getInt(1);
		}
	}

	public static Vector<BackRepair> Load(Bill billItem) {
		
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement("select * from BackRepair where billid=?")) {
			Vector<BackRepair> backRepairs=new Vector<>();
			pst.setInt(1, billItem.getId());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {

				BackRepair backRepair = new BackRepair();
				backRepair.setBillItem(billItem);
				backRepair.setId(rs.getInt("id"));
				backRepair.setBackDate(rs.getDate("BackDate"));
				backRepair.setBackNum(rs.getLong("BackNum"));
				backRepair.setGetDate(rs.getDate("getdate"));
				backRepair.setNote(rs.getString("note"));
				backRepairs.add(backRepair);

			}

			return backRepairs;
		} catch (SQLException e) {
			return null;
		}

	}

	
	public Bill getBillItem() {
		return billItem;
	}
	public void setBillItem(Bill billItem) {
		this.billItem = billItem;
	}
	@ChinaAno(order = 5, str = "返修日期")
	public Date getBackDate() {
		return backDate;
	}
	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}
	@ChinaAno(order = 4, str = "返修数据")
	public long getBackNum() {
		return backNum;
	}
	@ChinaAno(order = 2, str = "订单号")
	public String getBillNo() {
		return billItem.getBillNo();
	}
	@ChinaAno(order = 1, str = "订单客户")
	public String getCustom() {
		return billItem.getCustom();
	}
	public void setBackNum(long backNum) {
		this.backNum = backNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@ChinaAno(order =6, str = "返修交货日期")
	public Date getGetDate() {
		return getDate;
	}
	public void setGetDate(Date getDate) {
		this.getDate = getDate;
	}
	@ChinaAno(order =3, str = "返修原因")
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}


}
