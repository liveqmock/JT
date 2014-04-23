package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.awt.Color;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.mao.jf.beans.annotation.Caption;
@Entity
public class PicBean  {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "bill", referencedColumnName = "id")
	private BillBean bill;
	  
	@Caption("��Ŀ��")
	private String item;  
	@Caption("ͼ��")
	private String picid;
	@Caption("�ͻ�������")
	private String custBillNo;
	private String oleImgUrl;
	private String imageUrl;  
	private float plancost;
	@Caption("����")
	private int num;
	private String color;

	
	@Caption("���ձ���(δ��˰)")
	private float reportPrice;
	@Caption("���ձ���(��˰)")
	private float reportTaxPrice;
	@Caption("�ܼ�")
	@Transient
	private float reportTaxMoney;
	
	@Caption("������")
	private String gjh;
	@Caption("������")
	private String meterial;
	@Caption("���ϱ��")
	private String meterialCode;
	@Caption("����")
	private String meterialz;
	@Caption("�����ͺ�")
	private String meterialType;
	@Caption("���ϼ�������")
	private String techCondition;
	@Caption("���ϲ�����")
	private String partName;
	  
	@Caption("���ʱ��")
	private Date itemCompleteDate ;  
	@Caption("���")
	private boolean warehoused;   
	@Caption("��Э��λ")
	private String outCustom;
	@Caption("��Э�۸�")
	private float outPrice;
	@Caption("��Э����")
	private long outNum;
	@Caption("��Э��������")
	private Date outGetDate;
	
	@OneToMany(mappedBy = "pic")
	private Collection<PicPlan> plans;

	@OneToMany(mappedBy = "pic")
	private Collection<BackRepair> backRepairs;

	@OneToMany(mappedBy = "pic")
	private Collection<Material> materials;

	@OneToMany(mappedBy = "pic")
	private Collection<ShipingBean> shipingBeans;

	@Caption("���ۼ�¼")
	@OneToMany(mappedBy = "pic")
	@OrderBy("reportDate desc")
	private Collection<ReportPrice> reportPrices;
	
	

	private boolean complete;
	private boolean cancel;
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
		// TODO �Զ����ɵķ������
		return false;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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

	public String getCustBillNo() {
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
		// TODO �Զ����ɵķ������
		return reportPrice*num;
	}
	public float getReportTaxMoney() {
		// TODO �Զ����ɵķ������
		return reportTaxPrice*num;
	}
	public void setReportTaxPrice(float reportPriceS) {
		this.reportTaxPrice = reportPriceS;
		this.reportPrice= Math.round(reportPriceS/0.0117)/100.0f;
	}
	public void setReportPrice(float reportPrice) {
		this.reportPrice = reportPrice;
		this.reportTaxPrice= Math.round(reportPrice*117f)/100.0f;
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


}