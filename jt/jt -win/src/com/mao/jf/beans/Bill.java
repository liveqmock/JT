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
import java.util.Vector;

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
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		try(Statement st=SessionData.getConnection().createStatement();
				ResultSet rs=st.executeQuery("select sum(outprice*outnum)/casewhen(sum(outnum)>0,sum(outnum),1) outprice,sum(reportprice*num)/casewhen(sum(num)>0,sum(num),1) reportprice,sum(outnum) outnum,sum(num) num,sum(plancost) plancost,sum(opercost) opercost "+ 
					" from (select outprice,outnum,reportprice,num, "+
					"(select sum(unitcost*operationNum) from opercost  where bill=a.id ) opercost,"+
					"plancost plancost"+
					" from bill a where "+searchString+ ")");){
			if(rs.next()){
				Bill billItem = new Bill();
				billItem.setCustom("�ϼ�");
				billItem.setReportPrice(rs.getFloat("reportprice"));
				billItem.setOutPrice(rs.getFloat("outprice"));
				billItem.setNum(rs.getLong("num"));
				billItem.setOutNum(rs.getLong("outnum"));
				billItem.setItemCompleteDate(new Date());
				billItem.setOperCost(rs.getFloat("OperCost"));
				billItem.setPlanCost(rs.getFloat("PlanCost"));
				billItems.add(billItem);
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
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
	private String meterial;

	private boolean warehoused;

	private int backRepairNum;
	private String status;

	private float operCost;
	private float planCost;
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

	@ChinaAno(order = 61, str = "���޼�¼")
	public int getBackRepairNum() {
		return backRepairNum;
	}

	@ChinaAno(order = 5, str = "��������")
	public Date getBillDate() {
		return billDate;
	}

	@ChinaAno(order = 9, str = "��Ʊ����")
	public Date getBilledDate() {
		return billedDate;
	}

	@ChinaAno(order = 1, str = "������")
	public String getBillid() {
		return billid;
	}

	@ChinaAno(order = 8, str = "��Ʊ��")
	public String getBillNo() {
		return billNo;
	}

	public String getColor() {
		return color;
	}

	@ChinaAno(order = -1, str = "�ͻ�����")
	public String getCustom() {
		return custom;
	}

	@ChinaAno(order = 31, str = "��ϵ��")
	public String getCustomMan() {
		return customMan;
	}

	public int getId() {
		return id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	@ChinaAno(order = 2, str = "��Ŀ��")
	public String getItem() {
		return item;
	}

	@ChinaAno(order = 7, str = "������������")
	public Date getItemCompleteDate() {
		return itemCompleteDate;
	}

	@ChinaAno(order = 99, str = "��ע")
	public String getNote() {
		return note;
	}

	@ChinaAno(order = 4, str = "����")
	public long getNum() {
		return num;
	}

	@ChinaAno(order = 25, str = "��Э��������")
	public Date getOutBillDate() {
		return outBillDate;
	}
	@Transient
	//@ChinaAno(order=56,str="����״̬")
	public String getStatus() {
		if(status!=null) return status;
		status="";
		
		return status;
	}

	@ChinaAno(order=59,str="���Ϸ���")
	public String getMeterial() {
		return meterial;
	}
	@Transient
	@ChinaAno(order=57,str="��������")
	public float getOperCost() {

		return Userman.loginUser.isManager()? operCost:0;
	}
	@Transient
	@ChinaAno(order=58,str="�ƻ�����")
	public float getPlanCost() {

		return planCost;
	}



	public void setOperCost(float operCost) {
		this.operCost = operCost;
	}
	public void setPlanCost(float planCost) {
		this.planCost = planCost;
	}
	@ChinaAno(order = 21, str = "��Э������")
	public String getOutBillNo() {
		return outBillNo;
	}

	@ChinaAno(order = 22, str = "��Э�ͻ�����")
	public String getOutCustom() {
		return outCustom;
	}

	@ChinaAno(order = 26, str = "��Э��������")
	public Date getOutGetDate() {
		return outGetDate;
	}

	@Transient
	@ChinaAno(order = 24, str = "��Э�ܼ�")
	public float getOutMoney() {
		// TODO Auto-generated method stub
		
		return Userman.loginUser.isManager()? outPrice * outNum:0;
	}

	@ChinaAno(order = 23, str = "��Э����")
	public long getOutNum() {
		return outNum;
	}

	@ChinaAno(order = 22, str = "��Э����")
	public float getOutPrice() {

		return Userman.loginUser.isManager()? outPrice:0;
		
	}

	@ChinaAno(order = 3, str = "ͼ��")
	public String getPicid() {
		return picid;
	}
	@Transient
	@ChinaAno(order = 12, str = "�����ܼ�")
	public float getReportMoney() {

		return Userman.loginUser.isManager()? reportPrice * num:0;
	}

	@ChinaAno(order = 11, str = "��������")
	public float getReportPrice() {

		return Userman.loginUser.isManager()? reportPrice:0;
	}

	@ChinaAno(order = 6, str = "Ҫ�󽻻�����")
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
	@ChinaAno(order = 51, str = "���״̬")
	public String getWarehousedStr() {
		return warehoused ? "�����" : "δ���";
	}
	@Transient
	@ChinaAno(order = 52, str = "��Ʊ״̬")
	public String getIsBilled() {
		return isBilled() ? "�ѿ�Ʊ" : "δ��Ʊ";
	}

	@Transient
	@ChinaAno(order = 53, str = "��������״̬")
	public String getIsItemComplete() {
		return isItemComplete() ? "�ѽ���" : "δ����";
	}

	@Transient
	@ChinaAno(order = 54, str = "��Э����״̬")
	public String getIsOutComplete() {
		if(outNum==0) 
			return "����Э�ӹ�";
		else
			return isBilled() ? "�ѽ���" : "δ����";
	}

	@Transient
	@ChinaAno(order = 55, str = "��Э��Ʊ״̬")
	public String getIsOutBilled() {
		if(outNum==0) 
			return "����Э�ӹ�";
		else
			return outBillNo!=null ? "�ѿ�Ʊ" : "δ��Ʊ";
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

	// @ChinaAno(order=12,str="�ͻ������ܼ�")
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
				sql = "insert into bill (billdate,billid ,item,picid ,imageUrl ,note,custom ,outCustom,customMan,billno,billeddate,outgetdate,itemCompleteDate,num,requestDate,outNum,color,warehoused,outBillNo,billgroup,outBillDate,meterial,reportPrice ,outPrice,plancost) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			else
				sql = "insert into bill (billdate,billid ,item,picid ,imageUrl ,note,custom ,outCustom,customMan,billno,billeddate,outgetdate,num,requestDate,itemCompleteDate,outNum,color,warehoused,outBillNo,billgroup,outBillDate,meterial) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		} else {

			if (Userman.loginUser.isManager())
				sql = "update  bill set billdate=?,billid=? ,item=?,picid =?,imageUrl =?,note=?,custom =?,outCustom =?,customMan =?,billno=?,billeddate=?,outgetdate=?,itemCompleteDate=?,num=?,requestDate=?,outNum =?,color=?,warehoused=?,outBillNo=?,billgroup=?,outBillDate=?,meterial=?,reportPrice=? ,outPrice =? ,plancost=? where id="
						+ id;
			else

				sql = "update  bill set billdate=?,billid=? ,item=?,picid =?,imageUrl =?,note=?,custom =?,outCustom =?,customMan =?,billno=?,billeddate=?,outgetdate=?,itemCompleteDate=?,num=?,requestDate=?,outNum =?,color=?,warehoused=?,outBillNo=?,billgroup=?,outBillDate=?,meterial=? where id="
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
			pst.setString(20, billgroup);
			pst.setDate(21, outBillDate == null ? null : new java.sql.Date(
					outBillDate.getTime()));
			pst.setString(22, meterial);
			if (Userman.loginUser.isManager()) {
				pst.setFloat(23, reportPrice);
				pst.setFloat(24, outPrice);
				pst.setFloat(25, planCost);
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
	@ChinaAno(order=0,str="������")
	public String getBillgroup() {
		return billgroup;
	}
	public void setBillgroup(String billgroup) {
		this.billgroup = billgroup;
	}
	public Vector<Plan> getPlans() {
		// TODO �Զ����ɵķ������
		try {
			return Plan.loadAll(Plan.class, "select * from plan where bill="+getId());
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return null;
		}
	}
	public void setMeterial(String meterial) {
		this.meterial = meterial;
	}

}
