package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.beanutils.PropertyUtils;

import com.mao.jf.beans.annotation.Caption;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class BackRepair {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	private Bill billItem ;
	@Caption("返修数据")
	private long backNum ;
	@Caption("返修日期")
	private Date backDate;
	@Caption("返修交货日期")
	private Date getDate ;
	@Caption("返修原因")
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
	public Date getBackDate() {
		return backDate;
	}
	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}
	public long getBackNum() {
		return backNum;
	}
	@Caption(order=-2,value="订单号")
	public String getBillNo() {
		return billItem.getBillNo();
	}
	@Caption(order=-1,value="订单客户")
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

	public Date getGetDate() {
		return getDate;
	}
	public void setGetDate(Date getDate) {
		this.getDate = getDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public static void	main(String[] a) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
		BackRepair backRepair=new BackRepair();
		backRepair.setBillItem(Bill.load(Bill.class,100));
		backRepair.setNote("dd");
		Object v = PropertyUtils.getSimpleProperty(backRepair,"custom");
		System.out.println(v);
	}
}
