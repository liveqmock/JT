package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.mao.jf.beans.annotation.Caption;

import javax.persistence.OneToOne;
import javax.persistence.Basic;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
@Entity
public class PicBean  {
	@Transient
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Caption("系统ID")
	private int id;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "bill", referencedColumnName = "id")
	private BillBean bill;

	@Caption("项目号")
	private String item;  
	@Caption("图号")
	private String picid;
	@Caption("客户订单号")
	private String custBillNo;
	private String oleImgUrl;
	private String imageUrl; 
	@Caption("额定单件成本")
	private float plancost;
	@Caption("数量")
	private int num;
	private String color;


	@Caption("最终报价(未含税)")
	private float reportPrice;
	@Caption("最终报价(含税)")
	private float reportTaxPrice;
	@Caption("检验人")
	@OneToOne
	@JoinColumn(name = "checker", referencedColumnName = "id")
	@Basic(fetch = LAZY)
	private Employee checker;
	@Caption("良品数")
	private int nondefective;
	@Caption("不良数")
	private int defective;


	@Caption("构件号")
	@Basic(fetch = LAZY)
	private String gjh;
	@Caption("材料名")
	private String meterial;
	@Caption("材料编号")
	private String meterialCode;
	@Caption("材质")
	private String meterialz;
	@Caption("材料型号")
	private String meterialType;
	@Caption("材料技术条件")
	private String techCondition;
	@Caption("材料部件名")
	private String partName;

	@Caption("完成时间")
	private Date itemCompleteDate ;  
	@Caption("入库")
	private boolean warehoused;   
	@Caption("外协单位")
	private String outCustom;
	@Caption("外协价格")
	private float outPrice;
	@Caption("外协数量")
	private long outNum;
	@Caption("外协交货日期")
	private Date outGetDate;
	@Caption("外协内容")
	private String outContent;
	@Caption("特殊采用")
	private boolean special;
	@Caption("特采客户确认人")
	private String specialMan;
	@Caption("特采确认人")
	private String specialUser;
	@Caption("已完结")
	private boolean complete;
	private boolean cancel;

	@OneToMany(mappedBy = "pic")	
	private Collection<PicPlan> plans;

	@OneToMany(mappedBy = "pic")
	private Collection<BackRepair> backRepairs;

	@OneToMany(mappedBy = "pic")
	private Collection<Material> materials;

	@OneToMany(mappedBy = "pic")
	private Collection<ShipingBean> shipingBeans;
	@OneToMany(mappedBy = "pic")
	private Collection<ShipingOutBean> shipingOutBeans;

	@OneToMany(mappedBy = "pic")
	@OrderBy("reportDate desc")
	private Collection<ReportPrice> reportPrices;

	@OneToMany(mappedBy = "pic", cascade = ALL)
	@OrderBy("createDate desc")
	private Collection<OutFpBean> fpOutBeans;
	@Transient   
	private int outFpMoney;
	@Transient
	private Date OutFpDate;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getPicid() {
		return picid;
	}

	public void setPicid(String picid) {
		this.picid = picid;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	private boolean isOutBig() {
		// TODO 自动生成的方法存根
		return false;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Collection<OutFpBean> getFpOutBeans() {
		return fpOutBeans;
	}

	public void setFpOutBeans(Collection<OutFpBean> fpOutBeans) {
		this.fpOutBeans = fpOutBeans;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getGjh() {
		return gjh;
	}

	public void setGjh(String gjh) {
		this.gjh = gjh;
	}

	public Collection<PicPlan> getPlans() {
		return plans;
	}

	public Employee getChecker() {
		return checker;
	}

	public void setChecker(Employee checker) {
		this.checker = checker;
	}

	public int getNondefective() {
		return nondefective;
	}

	public void setNondefective(int nondefective) {
		this.nondefective = nondefective;
	}

	public int getDefective() {
		return defective;
	}

	public void setDefective(int defective) {
		this.defective = defective;
	}

	public String getCustBillNo() {
		if(custBillNo==null) custBillNo=bill.getBillid();
		return custBillNo;
	}

	public void setCustBillNo(String custBillNo) {
		this.custBillNo = custBillNo;
	}

	public void setPlans(Collection<PicPlan> plans) {
		this.plans = plans;
	}

	public Collection<BackRepair> getBackRepairs() {
		return backRepairs;
	}

	public void setBackRepairs(Collection<BackRepair> backRepairs) {
		this.backRepairs = backRepairs;
	}

	public Collection<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(Collection<Material> materials) {
		this.materials = materials;
	}



	public Collection<ShipingBean> getShipingBeans() {
		return shipingBeans;
	}

	public void setShipingBeans(Collection<ShipingBean> shipingBeans) {
		this.shipingBeans = shipingBeans;
	}


	public String getOleImgUrl() {
		return oleImgUrl;
	}


	public String getOutContent() {
		return outContent;
	}

	public void setOutContent(String outContent) {
		this.outContent = outContent;
	}

	public boolean isSpecial() {
		return special;
	}

	public void setSpecial(boolean special) {
		this.special = special;
	}

	public String getSpecialMan() {
		return specialMan;
	}

	public void setSpecialMan(String specialMan) {
		this.specialMan = specialMan;
	}

	public String getSpecialUser() {
		return specialUser;
	}

	public void setSpecialUser(String specialUser) {
		this.specialUser = specialUser;
	}

	public Date getItemCompleteDate() {
		return itemCompleteDate;
	}

	public void setItemCompleteDate(Date itemCompleteDate) {
		this.itemCompleteDate = itemCompleteDate;
	}

	public boolean isWarehoused() {
		return warehoused;
	}

	public void setWarehoused(boolean warehoused) {
		this.warehoused = warehoused;
	}

	public String getOutCustom() {
		return outCustom;
	}

	public void setOutCustom(String outCustom) {
		this.outCustom = outCustom;
	}

	public float getOutPrice() {
		return outPrice;
	}

	public void setOutPrice(float outPrice) {
		this.outPrice = outPrice;
	}

	public long getOutNum() {
		return outNum;
	}

	public void setOutNum(long outNum) {
		this.outNum = outNum;
	}

	public Date getOutGetDate() {
		return outGetDate;
	}

	public void setOutGetDate(Date outGetDate) {
		this.outGetDate = outGetDate;
	}

	public Collection<ReportPrice> getReportPrices() {
		return reportPrices;
	}

	public void setReportPrices(Collection<ReportPrice> reportPrices) {
		this.reportPrices = reportPrices;
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

	public void setOleImgUrl(String oleImgUrl) {
		this.oleImgUrl = oleImgUrl;
	}
	public String getMeterial() {
		return meterial;
	}
	public void setMeterial(String meterial) {
		this.meterial = meterial;
	}
	public float getPlancost() {
		return plancost;
	}

	public void setPlancost(float plancost) {
		this.plancost = plancost;
	}

	public String getMeterialz() {
		return meterialz;
	}
	public void setMeterialz(String meterialz) {
		this.meterialz = meterialz;
	}
	public String getMeterialType() {
		return meterialType;
	}
	public void setMeterialType(String meterialType) {
		this.meterialType = meterialType;
	}
	public String getTechCondition() {
		return techCondition;
	}
	public void setTechCondition(String techCondition) {
		this.techCondition = techCondition;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getMeterialCode() {
		return meterialCode;
	}
	public void setMeterialCode(String meterialCode) {
		this.meterialCode = meterialCode;
	}

	public BillBean getBill() {
		return bill;
	}

	public float getReportPrice() {

		return reportPrice;
	}

	public float getReportTaxPrice() {
		return reportTaxPrice;
	}
	public float getReportMoney() {
		// TODO 自动生成的方法存根
		return reportPrice*num;
	}
	@Caption("总价")
	public float getReportTaxMoney() {
		// TODO 自动生成的方法存根
		return reportTaxPrice*num;
	}
	public void setReportTaxPrice(float reportPriceS) {
		this.reportTaxPrice = reportPriceS;
		float oldValue = getReportPrice();
		this.reportPrice= Math.round(reportPriceS/0.0117)/100.0f;
		this.pcs.firePropertyChange("reportPrice", oldValue, this.reportPrice);


	}
	public void setReportPrice(float reportPrice) {
		this.reportPrice = reportPrice;
		float oldValue = reportTaxPrice;
		this.reportTaxPrice=Math.round(reportPrice*117f)/100.0f;
		this.pcs.firePropertyChange("reportTaxPrice", oldValue, this.reportTaxPrice);
	}
	public void setBill(BillBean bill) {
		this.bill = bill;
	}
	public int getRemainNotShipingNum() {
		if(getShipingBeans()==null)return num;

		int remainNotShipingNum=num;
		for( ShipingBean shipingBean:getShipingBeans()){
			remainNotShipingNum-=shipingBean.getNum();
		}
		return remainNotShipingNum;
	}

	public PicBean getSamePicBill() {
		if(picid!=null)
			return BeanMao.beanManager.getBean(PicBean.class, "bill=?1 and picid=?2",getBill(),picid);
		else
			return null;//org.hibernate.connection.C3P0ConnectionProvide

	}
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	@Caption("客户")
	public String getCustom() {
		return bill.getCustom();
	}
	@Caption("订单号")
	public String getBillId() {
		return bill.getBillid();
	}
	@Caption("订单时间")
	public Date getBillDate() {
		return bill.getBillDate();
	}
	@Caption("客户要求交货时间")
	public Date getRequestDate() {
		return bill.getRequestDate();
	}
	@Caption("订单交货时间")
	public Date getBillGetDate() {
		return bill.getBillGetDate();
	}

	public Collection<ShipingOutBean> getShipingOutBeans() {
		return shipingOutBeans;
	}

	public void setShipingOutBeans(Collection<ShipingOutBean> shipingOutBeans) {
		this.shipingOutBeans = shipingOutBeans;
	}

	@Caption("发货时间")
	public Date getPicShipingDate() {
		if(getShipingBeans()!=null&&getShipingBeans().iterator().hasNext())
			return getShipingBeans().iterator().next().getShipingDate();
		else return null;
	}
	@Caption("发货数量")
	public int getPicShipingNum() {
		if(getShipingBeans()!=null)
		{	
			int total=0;
			Iterator<ShipingBean> it = getShipingBeans().iterator();
			while(it.hasNext())
				total+=it.next().getNum();
			return total;
		}
		else return 0;
	}
	@Caption("外协发货时间")
	public Date getPicShipingOutDate() {
		if(getShipingOutBeans()!=null&&getShipingOutBeans().iterator().hasNext())
			return getShipingOutBeans().iterator().next().getShipingDate();
		else return null;
	}
	@Caption("外协发货数量")
	public int getPicShipingOutNum() {
		if(getShipingOutBeans()!=null)
		{	
			int total=0;
			Iterator<ShipingOutBean> it = getShipingOutBeans().iterator();
			while(it.hasNext())
				total+=it.next().getNum();
			return total;
		}
		else return 0;
	}

	public Number getRemainOutNotFbMoney() {
		if(getFpOutBeans()==null)return getOutNum()*getOutPrice();

		float remainNotFbMoney=getReportTaxMoney();
		for( OutFpBean fp:getFpOutBeans()){
			remainNotFbMoney-=fp.getMoney();
		}
		return remainNotFbMoney;

	}

	@Caption("外协发票数量")
	public int getFpOutNum() {
		if(getFpOutBeans()!=null)
			return getFpOutBeans().size();
		else return 0;
	}
	@Caption("外协发票时间")
	public Date getFpOutDate() {
		return OutFpDate;
	}
	@Caption("外协发票金额")
	public float getOutFpMoney() {
		return outFpMoney;
	}

	@PostLoad
	public void getOutFpInfo() {

		outFpMoney=0;
		if(getFpOutBeans()!=null)
		{	
			Iterator<OutFpBean> it = getFpOutBeans().iterator();
			while(it.hasNext())
				outFpMoney+=it.next().getMoney();
		}
		if(getFpOutBeans()!=null&&getFpOutBeans().iterator().hasNext())
			OutFpDate= getFpOutBeans().iterator().next().getCreateDate();
	}
	@Caption("最后开票日期")
	public Date getFpDate() {
		return bill.getFpDate();
	}
	@Caption("开票金额")
	public float getFpMoney() {
		return bill.getFpMoney();
	}
	@Caption("发票张数")
	public int getFpNum() {
		return bill.getFpNum();
	}
}
