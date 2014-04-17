package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.mao.jf.beans.annotation.Caption;
import com.sun.org.apache.xpath.internal.operations.Bool;
@Entity
public class BillBean extends BeanMao {	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	@Caption("客户")
	private String custom;
	@Caption("联系人")
	private String customMan;
	@Caption("订单号")
	private String billid;
	@Caption("最终报价(未含税)")
	@Transient
	private float reportMoney;
	@Caption("最终报价(含税)")
	@Transient
	private float reportTaxMoney;
	@Caption("订单交货日期")
	private Date billGetDate;
	@Caption("要求交货时间")
	private Date requestDate;	
	@Column(name = "billDate")
	@Caption("建单日期")
	private Date createDate;//billDate	
	
	private boolean complete;
	private boolean cancel;
	
	

	@OneToMany(mappedBy = "bill")
	private Collection<PicBean> picBeans;
	@OneToMany(mappedBy = "bill")
	@OrderBy("changeDate")
	private Collection<ChangeLog> changeLogs;
	
	@OneToMany(mappedBy = "bill")
	@OrderBy("createDate")
	private Collection<FpBean> fpBeans;

	@OneToMany(mappedBy = "bill")
	@OrderBy("createDate")
	private Collection<FpBean> outFpBeans;



	private String color;
	@Caption("备注")
	private String note;
	
	
//	public static List<BillBean> loadBySearch(String searchString) {
//
//		return loadBySearch(searchString, true);
//
//	}
//	public static List<BillBean> loadBySearch(String searchString,boolean isShowCompelete) {
//
//		List<BillBean> billItems=null;
//		if(Userman.loginUser.isManager()|| isShowCompelete){
//			searchString=(searchString==null?"":searchString+" ")+" order by a.itemCompleteDate ,a.requestDate";
//
//		}else{
//			searchString=(searchString==null?"":searchString+" and ")+" a.itemCompleteDate is null  order by a.itemCompleteDate ,a.requestDate";
//
//		}
//		billItems=getBeans(BillBean.class,searchString);
//
//		return billItems;
//
//	}
//	public static List<BillBean> loadByGrp(String billGrp) {
//
//		return loadBySearch(" a.billgroup='"+billGrp+"'",true);
//	}
	
	
	
	
	public static List<PicBean> SearchPics(String searchString) {

		return SearchPics(searchString, true);

	}
	public static List<PicBean> SearchPics(String searchString,boolean isShowCompelete) {

		List<PicBean> picBeans=null;
		if(Userman.loginUser.isManager()|| isShowCompelete){
			searchString=(searchString==null?"":searchString+" ")+" order by  itemCompleteDate ,bill.requestDate";

		}else{
			searchString=(searchString==null?"":searchString+" and ")+"  itemCompleteDate is null  order by  itemCompleteDate ,bill.requestDate";

		}
		picBeans=getBeans(PicBean.class,searchString);

		return picBeans;

	}
	public static List<BillBean> SearchBeans(String searchString) {
		return SearchBeans (searchString,false);
	}
	public static List<BillBean> SearchBeans(String searchString,boolean isShowCompelete) {
		List<BillBean> billBeans=null;
		if(Userman.loginUser.isManager()|| isShowCompelete){
			searchString=(searchString==null?"":searchString+" ")+" order by  createDate ,requestDate";

		}else{
			searchString=(searchString==null?"":searchString+" and ")+"  billGetDate is null  order by  createDate ,requestDate";

		}
		billBeans=getBeans(BillBean.class,searchString);

		return billBeans;
	}
	public Color getTableColor() {
		Color tableColor = null;
		if (isOutBig())
			tableColor = Color.red;

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
	public boolean isOutBig() {
		return false;// outPrice >= reportPrice&&reportPrice>0;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCustom() {
		return custom;
	}
	public void setCustom(String custom) {
		this.custom = custom;
	}
	public String getCustomMan() {
		return customMan;
	}
	public void setCustomMan(String customMan) {
		this.customMan = customMan;
	}
	public String getBillid() {
		return billid;
	}
	public void setBillid(String billid) {
		this.billid = billid;
	}
	
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	public boolean isCancel() {
		return cancel;
	}
	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public Collection<ChangeLog> getChangeLogs() {
		return changeLogs;
	}
	public void setChangeLogs(Collection<ChangeLog> changeLogs) {
		this.changeLogs = changeLogs;
	}
	public Collection<FpBean> getFpBeans() {
		return fpBeans;
	}
	public Collection<PicBean> getPicBeans() {
		if(picBeans==null)picBeans=new ArrayList<PicBean>();
		return picBeans;
	}
	public void setPicBeans(Collection<PicBean> picBeans) {
		this.picBeans = picBeans;
	}
	public Collection<FpBean> getOutFpBeans() {
		return outFpBeans;
	}
	public void setOutFpBeans(Collection<FpBean> outFpBeans) {
		this.outFpBeans = outFpBeans;
	}
	public void setReportMoney(float reportMoney) {
		this.reportMoney = reportMoney;
	}
	public void setReportTaxMoney(float reportTaxMoney) {
		this.reportTaxMoney = reportTaxMoney;
	}
	public void setFpBeans(Collection<FpBean> fpBeans) {
		this.fpBeans = fpBeans;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	

	public float getReportMoney() {
		reportMoney=0;
		 for(PicBean picBean:picBeans){
			 reportMoney+=picBean.getReportMoney();
		 }
		 return reportMoney;
	}
	
	public float getReportTaxMoney() {
		reportTaxMoney=0;
		 for(PicBean picBean:picBeans){
			 reportTaxMoney+=picBean.getReportTaxMoney();
		 }
		 return reportTaxMoney;
	}
	public float getRemainNotFbMoney() {
		if(getFpBeans()==null)return getReportTaxMoney();
		
		float remainNotFbMoney=getReportTaxMoney();
		for( FpBean fp:getFpBeans()){
			remainNotFbMoney-=fp.getMoney();
		}
		return remainNotFbMoney;
	}
	public Date getBillGetDate() {
		return billGetDate;
	}
	public void setBillGetDate(Date billGetDate) {
		this.billGetDate = billGetDate;
	}

}
