package com.mao.jf.beans;

import java.awt.Color;
import java.beans.IntrospectionException;
import java.beans.Transient;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

public class Bill extends BeanMao {


	public static Vector<Bill> loadBySearch(String searchString) {
		Vector<Bill> billItems=null;
		try {
			billItems = loadAll(Bill.class,"select a.*,(select count(1) from backrepair where billid=a.id ) backrepairNum  from bill a where "
					+ searchString
					+ " order by itemCompleteDate ,requestdate");
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		try(Statement st=SessionData.getConnection().createStatement();
				ResultSet rs=st.executeQuery("select sum(outprice*outnum)/casewhen(sum(outnum)>0,sum(outnum),1) outprice,sum(reportprice*num)/casewhen(sum(num)>0,sum(num),1) reportprice,sum(outnum) outnum,sum(num) num  from bill where "
						+ searchString);){
			if(rs.next()){
				Bill billItem = new Bill();
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
	public static Vector<Bill> loadBySearch(String searchField,
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
	private String billgroup;
	private Date itemCompleteDate;

	private Date billedDate;

	private boolean warehoused;

	private int backRepairNum;
	private String status;

	public Bill() {
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

	@ChinaAno(order = -1, str = "客户名称")
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
	@Transient
	@ChinaAno(order=56,str="生产状态")
	public String getStatus() {
		if(status!=null) return status;
		status="";
		Vector<PlanCost> planCosts = PlanCost.loadAllByBill(this);
		if(planCosts==null||planCosts.size()==0) {
			status= "未排产";
		}else {
			Vector<OperCost> operCosts = OperCost.loadAllByBill(this);

			if(operCosts==null||operCosts.size()==0){
				status= "未开始生产";
			}else {
				HashMap<String, Integer> operHashMap=new HashMap<String, Integer>();
				for(OperCost operCost:operCosts){
					Integer a;
					if(operCost.getProductNum()+operCost.getScrapnum()==0) {
						a=operHashMap.get(operCost.getOperationName());
						if(a==null)
							operHashMap.put(operCost.getOperationName(), operCost.getAssignNum());
						else 
							operHashMap.put(operCost.getOperationName(), a+operCost.getAssignNum());
					}

				}
				for(String operName:operHashMap.keySet())
					status+=operHashMap.get(operName)+"件正在进行【"+operName+"】;";
				operCosts=null;
				operHashMap=null;
			}}
		if(status.length()==0)
			status= "完成加工";
		return status;
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

	@Transient
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
	@Transient
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
	@Transient
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
	@Transient
	@ChinaAno(order = 51, str = "入库状态")
	public String getWarehousedStr() {
		return warehoused ? "已入库" : "未入库";
	}
	@Transient
	@ChinaAno(order = 52, str = "开票状态")
	public String getIsBilled() {
		return isBilled() ? "已开票" : "未开票";
	}

	@Transient
	@ChinaAno(order = 53, str = "订单交货状态")
	public String getIsItemComplete() {
		return isItemComplete() ? "已交货" : "未交货";
	}

	@Transient
	@ChinaAno(order = 54, str = "外协交货状态")
	public String getIsOutComplete() {
		if(outNum==0) 
			return "无外协加工";
		else
			return isBilled() ? "已交货" : "未交货";
	}

	@Transient
	@ChinaAno(order = 55, str = "外协开票状态")
	public String getIsOutBilled() {
		if(outNum==0) 
			return "无外协加工";
		else
			return outBillNo!=null ? "已开票" : "未开票";
	}


	@Transient
	public boolean isBilled() {
		return billNo != null&&billNo.trim().length()>0;
	}

	@Transient
	public boolean isItemComplete() {
		return itemCompleteDate != null;
	}

	@Transient
	public boolean isOutBig() {
		return outPrice >= reportPrice&&reportPrice>0;
	}

	@Transient
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
	@ChinaAno(order=0,str="订单组")
	public String getBillgroup() {
		return billgroup;
	}
	public void setBillgroup(String billgroup) {
		this.billgroup = billgroup;
	}

}
