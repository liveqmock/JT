package com.mao.jf.beans;

import java.awt.Color;
import java.beans.IntrospectionException;
import java.beans.Transient;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.mao.jf.beans.annotation.Caption;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
@Entity
public class Bill extends BeanMao {

	public static Vector<Bill> loadByGrp(String billGrp) {

		return loadBySearch(" billgroup='"+billGrp+"'",true);
	}
	public static Vector<Bill> loadBySearch(String searchString,boolean isShowCompelete) {

		Vector<Bill> billItems=null;
		if(!Userman.loginUser.isManager()&& !isShowCompelete) searchString+=" and itemCompleteDate is null ";
		try {
			billItems = loadAll(Bill.class,"select a.*, (select count(1) from backrepair where billid=a.id ) backrepairNum,(select sum(unitcost*operationNum) from opercost  where bill=a.id ) opercost, plancost from bill a where "
					+ searchString
					+ " order by itemCompleteDate ,requestdate");
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}

		String sql="select sum(outprice*outnum)/(case when sum(outnum)>0 then sum(outnum) else 1 end) outprice,"
				+ " sum(reportprice*num)/(case when sum(num)>0 then sum(num) else 1 end) reportprice,sum(outnum) outnum,"
				+ " sum(num) num,sum(plancost) plancost,sum( opercost ) opercost"
				+ " from (select outprice,outnum,reportprice,num, "
				+ " (select sum(workcost) from operationwork  b join \"plan\"  c on b.\"plan\"=c.id and c.bill=a.id  ) opercost,"
				+ " (select sum(cost*(unitusetime*b.num+preparetime)) from operationplan b join \"plan\"  c on b.\"plan\"=c.id and c.bill=a.id ) plancost"
				+ " from bill a where "+searchString+ ") as a ";

		try(Statement st=SessionData.getConnection().createStatement();

				ResultSet rs=st.executeQuery(sql);){
			if(rs.next()){
				Bill billItem = new Bill();
				billItem.setCustom("合计");
				billItem.setReportPrice(rs.getFloat("reportprice"));
				billItem.setOutPrice(rs.getFloat("outprice"));
				billItem.setNum(rs.getInt("num"));
				billItem.setOutNum(rs.getLong("outnum"));
				billItem.setItemCompleteDate(new Date());
				billItem.setOperCost(rs.getFloat("OperCost"));
				billItem.setPlanCost(rs.getFloat("PlanCost"));
				billItems.add(billItem);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			System.err.println(sql);
			e.printStackTrace();
		}
		return billItems;

	}
	public static Vector<Bill> loadBySearch(String searchField,
			String searchString) {
		return loadBySearch(searchField + " like '%" + searchString + "%'",true);

	}
	public static Vector<Bill> loadNotComplete() {
		return loadBySearch("true",true);

	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	private String custom;
	private String billid;
	private String item;
	private String picid;
	private float reportPrice;
	private String imageUrl;
	private String note;
	private String outCustom;
	private float outPrice;
	private long outNum;
	private String billNo;
	private int num;
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
	private String meterial;

	private boolean warehoused;

	private int backRepairNum;
	private String status;

	private float operCost;
	private float planCost;
	private String gjh;
	private String meterialz;
	private String meterialType;
	private String techCondition;
	private String partName;
	private Vector<Material> materials;

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

	@Caption(order = 61, value= "返修记录")
	public int getBackRepairNum() {
		return backRepairNum;
	}

	@Caption(order = 5, value= "订单日期")
	public Date getBillDate() {
		return billDate;
	}

	@Caption(order = 9, value= "开票日期")
	public Date getBilledDate() {
		return billedDate;
	}

	@Caption(order = 1, value= "订单号")
	public String getBillid() {
		return billid;
	}

	@Caption(order = 8, value= "发票号")
	public String getBillNo() {
		return billNo;
	}

	public String getColor() {
		return color;
	}

	@Caption(order = -1, value= "客户名称")
	public String getCustom() {
		return custom;
	}

	@Caption(order = 31, value= "联系人")
	public String getCustomMan() {
		return customMan;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	@Caption(order = 2, value= "项目号")
	public String getItem() {
		return item;
	}

	@Caption(order = 7, value= "订单交货日期")
	public Date getItemCompleteDate() {
		return itemCompleteDate;
	}

	@Caption(order = 99, value= "备注")
	public String getNote() {
		return note;
	}

	@Caption(order = 4, value= "数量")
	public int getNum() {
		return num;
	}

	@Caption(order = 25, value= "外协订单日期")
	public Date getOutBillDate() {
		return outBillDate;
	}
	@Transient
	//@Caption(order=56,value="生产状态")
	public String getStatus() {
		if(status!=null) return status;
		status="";

		return status;
	}


	@Transient
	@Caption(order=57,value="生产费用")
	public float getOperCost() {

		return Userman.loginUser.isManager()? operCost:0;
	}
	@Transient
	@Caption(order=58,value="计划费用")
	public float getPlanCost() {

		return planCost;
	}
	@Caption(order=59,value="材料类型")
	public String getMeterial() {
		return meterial;
	}
	@Caption(order=70,value="材质")
	public String getMeterialz() {
		return meterialz;
	}
	@Caption(order=71,value="材料规格")
	public String getMeterialType() {
		return meterialType;
	}
	@Caption(order=72,value="技术条件")
	public String getTechCondition() {
		return techCondition;
	}
	@Caption(order=73,value="零件名称")
	public String getPartName() {
		return partName;
	}
	@Caption(order=74,value="工件号")
	public String getGjh() {
		return gjh;
	}


	public void setOperCost(float operCost) {
		this.operCost = operCost;
	}
	public void setPlanCost(float planCost) {
		this.planCost = planCost;
	}
	@Caption(order = 21, value= "外协订单号")
	public String getOutBillNo() {
		return outBillNo;
	}

	@Caption(order = 22, value= "外协客户名称")
	public String getOutCustom() {
		return outCustom;
	}

	@Caption(order = 26, value= "外协交货日期")
	public Date getOutGetDate() {
		return outGetDate;
	}

	@Transient
	@Caption(order = 24, value= "外协总价")
	public float getOutMoney() {
		// TODO Auto-generated method stub

		return Userman.loginUser.isManager()? outPrice * outNum:0;
	}

	@Caption(order = 23, value= "外协数量")
	public long getOutNum() {
		return outNum;
	}

	@Caption(order = 22, value= "外协单价")
	public float getOutPrice() {

		return Userman.loginUser.isManager()? outPrice:0;

	}

	@Caption(order = 3, value= "图号")
	public String getPicid() {
		return picid;
	}
	@Transient
	@Caption(order = 12, value= "订单总价")
	public float getReportMoney() {

		return Userman.loginUser.isManager()? reportPrice * num:0;
	}

	@Caption(order = 11, value= "订单单价")
	public float getReportPrice() {

		return Userman.loginUser.isManager()? reportPrice:0;
	}

	@Caption(order = 6, value= "要求交货日期")
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
	@Caption(order = 51, value= "入库状态")
	public String getWarehousedStr() {
		return warehoused ? "已入库" : "未入库";
	}
	@Transient
	@Caption(order = 52, value= "开票状态")
	public String getIsBilled() {
		return isBilled() ? "已开票" : "未开票";
	}

	@Transient
	@Caption(order = 53, value= "订单交货状态")
	public String getIsItemComplete() {
		return isItemComplete() ? "已交货" : "未交货";
	}

	@Transient
	@Caption(order = 54, value= "外协交货状态")
	public String getIsOutComplete() {
		if(outNum==0) 
			return "无外协加工";
		else
			return isBilled() ? "已交货" : "未交货";
	}

	@Transient
	@Caption(order = 55, value= "外协开票状态")
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

	// @Caption(order=12,value="客户请求总价")
	// public float getRequestMoney() {
	// return requestPrice*num;
	// }


	public boolean isWarehoused() {
		return warehoused;
	}


	public void save() {
		String sql = "";
		if (id == 0) {
			if (Userman.loginUser.isManager())
				sql = "insert into bill (billdate,billid ,item,picid ,imageUrl ,note,custom ," +
						"outCustom,customMan,billno,billeddate,outgetdate,itemCompleteDate,num," +
						"requestDate,outNum,color,warehoused,outBillNo,billgroup,outBillDate,meterial," +
						"meterialz,meterialType,techCondition,partName,gjh,reportPrice ,outPrice,plancost)" +
						" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			else
				sql = "insert into bill (billdate,billid ,item,picid ,imageUrl ,note,custom ,outCustom," +
						"customMan,billno,billeddate,outgetdate,num,requestDate,itemCompleteDate,outNum," +
						"color,warehoused,outBillNo,billgroup,outBillDate,meterial,meterialz,meterialType," +
						"techCondition,partName,gjh) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		} else {

			if (Userman.loginUser.isManager())
				sql = "update  bill set billdate=?,billid=? ,item=?,picid =?,imageUrl =?,note=?,custom =?,outCustom =?," +
						"customMan =?,billno=?,billeddate=?,outgetdate=?,itemCompleteDate=?,num=?,requestDate=?,outNum =?," +
						"color=?,warehoused=?,outBillNo=?,billgroup=?,outBillDate=?,meterial=?,meterialz=? ,meterialType=? ," +
						"techCondition=? ,partName=? ,gjh=? ,reportPrice=? ,outPrice =? ,plancost=? where id="+ id;
			else

				sql = "update  bill set billdate=?,billid=? ,item=?,picid =?,imageUrl =?,note=?,custom =?,outCustom =?," +
						"customMan =?,billno=?,billeddate=?,outgetdate=?,itemCompleteDate=?,num=?,requestDate=?,outNum =?," +
						"color=?,warehoused=?,outBillNo=?,billgroup=?,outBillDate=?,meterial=? ,meterialz=? ,meterialType=? ," +
						"techCondition=? ,partName=? ,gjh=? where id="+ id;
		}
		System.err.println(getId()+"\t"+ sql);
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
			pst.setInt(14, num);
			pst.setDate(15, requestDate == null ? null : new java.sql.Date(
					requestDate.getTime()));
			pst.setLong(16, outNum);
			pst.setString(17, color);
			pst.setInt(18, warehoused?1:0);
			pst.setString(19, outBillNo);
			pst.setString(20, billgroup);
			pst.setDate(21, outBillDate == null ? null : new java.sql.Date(
					outBillDate.getTime()));
			pst.setString(22, meterial);
			pst.setString(23, meterialz);
			pst.setString(24, meterialType);
			pst.setString(25, techCondition);
			pst.setString(26, partName);
			pst.setString(27, gjh);
			if (Userman.loginUser.isManager()) {
				pst.setFloat(28, reportPrice);
				pst.setFloat(29, outPrice);
				pst.setFloat(30, planCost);
			}

			pst.execute();
			if(getId()==0){
				ResultSet rsKey = pst.getGeneratedKeys();
				if (rsKey != null && rsKey.next())
					this.id = rsKey.getInt(1);
			}

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

	public void setNum(int num) {
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

	public void setOutPrice(float outPrice) {
		this.outPrice = outPrice;
	}

	public void setPicid(String picid) {
		this.picid = picid;
	}

	public void setReportPrice(float reportPrice) {
		this.reportPrice = reportPrice;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public void setWarehoused(boolean warehoused) {
		this.warehoused = warehoused;
	}
	@Caption(order=0,value="订单组")
	public String getBillgroup() {
		return billgroup;
	}
	public void setBillgroup(String billgroup) {
		this.billgroup = billgroup;
	}
	public Vector<Plan> getPlans() {
		// TODO 自动生成的方法存根
		try {
			return Plan.loadAll(Plan.class, "select * from \"plan\" where bill="+getId());
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}
	public void setMeterial(String meterial) {
		this.meterial = meterial;
	}

	public void setGjh(String gjh) {
		this.gjh = gjh;
	}
	public void setMeterialz(String meterialz) {
		this.meterialz = meterialz;
	}
	public void setMeterialType(String meterialType) {
		this.meterialType = meterialType;
	}
	public void setTechCondition(String techCondition) {
		this.techCondition = techCondition;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public Vector<Material> getmaterials() {
		if(materials==null)
			try {	
				materials=new Vector<Material>();

				materials=Material.loadAll(Material.class,"select * from material where bill="+this.id);
				for(Material material:materials)material.setBill(this);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException
					| IntrospectionException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		return materials;
	}
	public Material getFirstMaterial() {
		try{
			return getmaterials().firstElement();
		}catch(NoSuchElementException e){
			return new Material(this);
		}
	}
	@Override
	public void remove() throws SQLException {
		// TODO 自动生成的方法存根
		super.remove();
		if(this.imageUrl!=null) new File(this.imageUrl).delete();
	}

}
