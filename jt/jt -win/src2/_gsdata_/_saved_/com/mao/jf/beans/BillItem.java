package com.mao.jf.beans;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

public class BillItem {
	public static BillItem createBillItem(ResultSet rs) throws SQLException {
		BillItem billItem = new BillItem();
		billItem.setBillDate(rs.getDate("BillDate"));
		billItem.setBillid(rs.getString("Billid"));
		billItem.setCustom(rs.getString("Custom"));
		billItem.setId(rs.getInt("id"));
		billItem.setImageUrl(rs.getString("ImageUrl"));
		billItem.setItem(rs.getString("Item"));
		billItem.setNote(rs.getString("Note"));
		billItem.setPicid(rs.getString("Picid"));
		billItem.setOutCustom(rs.getString("OutCustom"));
		billItem.setCustomMan(rs.getString("CustomMan"));
		billItem.setBillNo(rs.getString("billno"));
		billItem.setBilledDate(rs.getDate("BilledDate"));
		billItem.setOutGetDate(rs.getDate("OutGetDate"));
		billItem.setItemCompleteDate(rs.getDate("ItemCompleteDate"));
		billItem.setNum(rs.getLong("Num"));
		billItem.setRequestDate(rs.getDate("requestDate"));
		billItem.setOutNum(rs.getLong("outNum"));
		billItem.setColor(rs.getString("color"));
		billItem.setWarehoused(rs.getBoolean("Warehoused"));
		billItem.setOutBillNo(rs.getString("outBillNo"));
		billItem.setOutBillDate(rs.getDate("outBillDate"));
		billItem.setBackRepairNum(rs.getInt("BackRepairNum"));
		billItem.setOutPrice(User.loginUser.getLevel() == 0 ? rs
				.getDouble("OutPrice") : 0);
		billItem.setReportPrice(User.loginUser.getLevel() == 0 ? rs
				.getDouble("ReportPrice") : 0);
		return billItem;
	}
	public static BillItem Load(int id) {
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement("select a.*,(select count(1) from backrepair where billid=a.id ) backrepairNum from bill a where id=?")) {
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			BillItem billItem = null;
			if (rs.next()) {
				billItem = createBillItem(rs);
			}

			return billItem;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	public static Vector<BillItem> loadBySearch(String searchString) {
		Vector<BillItem> billItems = new Vector<>();
		try (Statement statement = SessionData.getConnection()
				.createStatement();
				ResultSet rs = statement
						.executeQuery("select a.*,(select count(1) from backrepair where billid=a.id ) backrepairNum  from bill a where "
								+ searchString
								+ " order by billeddate ,requestdate");) {
			while (rs.next()) {
				BillItem billItem = createBillItem(rs);
				billItems.add(billItem);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			try(Statement st=SessionData.getConnection().createStatement();
					ResultSet rs=st.executeQuery("select sum(outprice*outnum)/casewhen(sum(outnum)>0,sum(outnum),1) outprice,sum(reportprice*num)/casewhen(sum(num)>0,sum(num),1) reportprice,sum(outnum) outnum,sum(num) num  from bill where "
								+ searchString);){
				if(rs.next()){
			BillItem billItem = new BillItem();
			billItem.setCustom("合计");
			billItem.setReportPrice(rs.getDouble("reportprice"));
			billItem.setOutPrice(rs.getDouble("outprice"));
			billItem.setNum(rs.getLong("num"));
			billItem.setOutNum(rs.getLong("outnum"));
			billItem.setItemCompleteDate(new Date());
			billItems.add(billItem);
				}
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		return billItems;

	}
	public static Vector<BillItem> loadBySearch(String searchField,
			String searchString) {
		return loadBySearch(searchField + " like '%" + searchString + "%'");

	}
	private int id;
	private String custom;
	private String billid;
	private String item;
	private String picid;
	private double reportPrice;
	private String imageUrl;
	private String note;
	private String outCustom;
	private double outPrice;
	private long outNum;
	private String billNo;
	private long num;
	private String customMan;
	private String color;
	private String outBillNo;
	private Date requestDate;
	private Date billDate;
	private Date outGetDate;
	private Date outBillDate;

	private Date itemCompleteDate;

	private Date billedDate;

	private boolean warehoused;

	private int backRepairNum;

	public BillItem() {
		custom = "";
		billid = "";
		item = "";
		picid = "";
		imageUrl = "";
		note = "";
		outCustom = "";
		customMan = "";
		billNo = "";
	}

	@ChinaAno(order = 61, str = "返修记录")
	public int getBackRepairNum() {
		return backRepairNum;
	}

	@ChinaAno(order = 5, str = "订单日期")
	public Date getBillDate() {
		return billDate;
	}

	@ChinaAno(order = 9, str = "开票日期")
	public Date getBilledDate() {
		return billedDate;
	}

	@ChinaAno(order = 1, str = "订单号")
	public String getBillid() {
		return billid;
	}

	@ChinaAno(order = 8, str = "发票号")
	public String getBillNo() {
		return billNo;
	}

	public String getColor() {
		return color;
	}

	@ChinaAno(order = 0, str = "客户名称")
	public String getCustom() {
		return custom;
	}

	@ChinaAno(order = 31, str = "联系人")
	public String getCustomMan() {
		return customMan;
	}

	public int getId() {
		return id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	@ChinaAno(order = 2, str = "项目号")
	public String getItem() {
		return item;
	}

	@ChinaAno(order = 7, str = "订单交货日期")
	public Date getItemCompleteDate() {
		return itemCompleteDate;
	}

	@ChinaAno(order = 99, str = "备注")
	public String getNote() {
		return note;
	}

	@ChinaAno(order = 4, str = "数量")
	public long getNum() {
		return num;
	}

	@ChinaAno(order = 25, str = "外协订单日期")
	public Date getOutBillDate() {
		return outBillDate;
	}

	//
	//
	//
	// @ChinaAno(order=11,str="客户请求价")
	// public double getRequestPrice() {
	// return requestPrice;
	// }
	//
	//
	//
	// public void setRequestPrice(double requestPrice) {
	// this.requestPrice = requestPrice;
	// }
	//

	@ChinaAno(order = 21, str = "外协订单号")
	public String getOutBillNo() {
		return outBillNo;
	}

	@ChinaAno(order = 22, str = "外协客户名称")
	public String getOutCustom() {
		return outCustom;
	}

	@ChinaAno(order = 26, str = "外协交货日期")
	public Date getOutGetDate() {
		return outGetDate;
	}

	@ChinaAno(order = 24, str = "外协总价")
	public double getOutMoney() {
		// TODO Auto-generated method stub
		return outPrice * outNum;
	}

	@ChinaAno(order = 23, str = "外协数量")
	public long getOutNum() {
		return outNum;
	}

	@ChinaAno(order = 22, str = "外协单价")
	public double getOutPrice() {
		return outPrice;
	}

	@ChinaAno(order = 3, str = "图号")
	public String getPicid() {
		return picid;
	}

	@ChinaAno(order = 12, str = "订单总价")
	public Double getReportMoney() {
		return reportPrice * num;
	}

	@ChinaAno(order = 11, str = "订单单价")
	public double getReportPrice() {
		return  reportPrice;
	}

	@ChinaAno(order = 6, str = "要求交货日期")
	public Date getRequestDate() {
		return requestDate;
	}

	public Color getTableColor() {
		Color tableColor = null;
		if (isOutBig())
			tableColor = Color.red;
		if (isItemComplete())
			tableColor = new Color(220, 220, 220);
		if (color != null)
			switch (color) {
			case "hi-orange":
				tableColor = Color.orange;
				break;
			case "hi-green":
				tableColor = Color.green;
				break;

			case "hi-blue":
				tableColor = Color.blue;
				break;

			default:
				break;
			}
		return tableColor;
	}

	@ChinaAno(order = 51, str = "入库状态")
	public String getWarehousedStr() {
		return warehoused ? "已入库" : "未入库";
	}

	@ChinaAno(order = 52, str = "开票状态")
	public String getIsBilled() {
		return isBilled() ? "已开票" : "未开票";
	}

	@ChinaAno(order = 53, str = "订单交货状态")
	public String getIsItemComplete() {
		return isItemComplete() ? "已交货" : "未交货";
	}

	@ChinaAno(order = 54, str = "外协交货状态")
	public String getIsOutComplete() {
		if(outNum==0) 
			return "无外协加工";
		else
			return isBilled() ? "已交货" : "未交货";
	}

	@ChinaAno(order = 54, str = "外协开票状态")
	public String getIsOutBilled() {
		if(outNum==0) 
			return "无外协加工";
		else
			return outBillNo!=null ? "已开票" : "未开票";
	}


	public boolean isBilled() {
		return billNo != null&&billNo.trim().length()>0;
	}

	public boolean isItemComplete() {
		return itemCompleteDate != null;
	}

	public boolean isOutBig() {
		return outPrice >= reportPrice&&reportPrice>0;
	}

	public boolean isOutComplete() {
		return outGetDate != null;
	}

	// @ChinaAno(order=12,str="客户请求总价")
	// public Double getRequestMoney() {
	// return requestPrice*num;
	// }


	public boolean isWarehoused() {
		return warehoused;
	}

	public void remove() {
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement("delete bill where id=?");) {
			pst.setInt(1, id);
			pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void save() {
		String sql = "";
		if (id == 0) {
			if (User.loginUser.getLevel() == 0)
				sql = "insert into bill (billdate,billid ,item,picid ,imageUrl ,note,custom ,outCustom,customMan,billno,billeddate,outgetdate,itemCompleteDate,num,requestDate,outNum,color,warehoused,outBillNo,outBillDate,reportPrice ,outPrice) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			else
				sql = "billdate,billid ,item,picid ,imageUrl ,note,custom ,outCustom,customMan,billno,billeddate,outgetdate,num,requestDate,itemCompleteDate,outNum,color,warehoused,outBillNo,outBillDate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		} else {

			if (User.loginUser.getLevel() == 0)
				sql = "update  bill set billdate=?,billid=? ,item=?,picid =?,imageUrl =?,note=?,custom =?,outCustom =?,customMan =?,billno=?,billeddate=?,outgetdate=?,itemCompleteDate=?,num=?,requestDate=?,outNum =?,color=?,warehoused=?,outBillNo=?,outBillDate=?,reportPrice=? ,outPrice =? where id="
						+ id;
			else

				sql = "update  bill set billdate=?,billid=? ,item=?,picid =?,imageUrl =?,note=?,custom =?,outCustom =?,customMan =?,billno=?,billeddate=?,outgetdate=?,itemCompleteDate=?,num=?,requestDate=?,outNum =?,color=?,warehoused=?,outBillNo=?,outBillDate=? where id="
						+ id;
		}
		try (PreparedStatement pst = SessionData.getConnection()
				.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			pst.setDate(
					1,
					billDate == null ? null : new java.sql.Date(billDate
							.getTime()));
			pst.setString(2, billid);
			pst.setString(3, item);
			pst.setString(4, picid);
			pst.setString(5, imageUrl);
			pst.setString(6, note);
			pst.setString(7, custom);
			pst.setString(8, outCustom);
			pst.setString(9, customMan);
			pst.setString(10, billNo);
			pst.setDate(11, billedDate == null ? null : new java.sql.Date(
					billedDate.getTime()));
			pst.setDate(12, outGetDate == null ? null : new java.sql.Date(
					outGetDate.getTime()));
			pst.setDate(13, itemCompleteDate == null ? null
					: new java.sql.Date(itemCompleteDate.getTime()));
			pst.setLong(14, num);
			pst.setDate(15, requestDate == null ? null : new java.sql.Date(
					requestDate.getTime()));
			pst.setLong(16, outNum);
			pst.setString(17, color);
			pst.setBoolean(18, warehoused);
			pst.setString(19, outBillNo);
			pst.setDate(20, outBillDate == null ? null : new java.sql.Date(
					outBillDate.getTime()));
			if (User.loginUser.getLevel() == 0) {
				pst.setDouble(21, reportPrice);
				pst.setDouble(22, outPrice);
			}
			pst.execute();
			ResultSet rsKey = pst.getGeneratedKeys();
			if (rsKey != null && rsKey.next())
				this.id = rsKey.getInt(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setBackRepairNum(int backRepairNum) {
		this.backRepairNum = backRepairNum;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public void setBilledDate(Date billedDate) {
		this.billedDate = billedDate;
	}

	public void setBillid(String billid) {
		this.billid = billid;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public void setCustomMan(String customMan) {
		this.customMan = customMan;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setItemCompleteDate(Date itemCompleteDate) {
		this.itemCompleteDate = itemCompleteDate;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public void setOutBillDate(Date outBillDate) {
		this.outBillDate = outBillDate;
	}

	public void setOutBillNo(String outBillNo) {
		this.outBillNo = outBillNo;
	}

	public void setOutCustom(String outCustom) {
		this.outCustom = outCustom;
	}

	public void setOutGetDate(Date outGetDate) {
		this.outGetDate = outGetDate;
	}

	public void setOutNum(long outNum) {
		this.outNum = outNum;
	}

	public void setOutPrice(double outPrice) {
		this.outPrice = outPrice;
	}

	public void setPicid(String picid) {
		this.picid = picid;
	}

	public void setReportPrice(double reportPrice) {
		this.reportPrice = reportPrice;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public void setWarehoused(boolean warehoused) {
		this.warehoused = warehoused;
	}

}
