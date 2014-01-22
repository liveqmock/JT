package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.awt.Color;
import java.beans.Transient;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.mao.jf.beans.annotation.Caption;
@Entity
public class Bill extends BeanMao {	
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
	private int warehoused;
	private int backRepairNum;
	private String status;
	private float operCost;
	private float planCost;
	private String gjh;
	private String meterialz;
	private String meterialType;
	private String techCondition;
	private String partName;
	@OneToMany(mappedBy = "bill")
	private List<Material> materials;
	
	@OneToMany(mappedBy = "bill")
	private List<Plan> plans;
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
	
	public static List<Bill> loadBySearch(String searchString,boolean isShowCompelete) {

		List<Bill> billItems=null;
		if(!Userman.loginUser.isManager()|| !isShowCompelete){
			searchString=(searchString==null?"":searchString+" and ")+" a.itemCompleteDate is null  order by a.itemCompleteDate ,a.requestDate";
		}else{

			searchString=(searchString==null?"":searchString+" ")+" order by a.itemCompleteDate ,a.requestDate";
		}
		
		
		billItems=loadAll(Bill.class,searchString);
		
		return billItems;

	}
	public static List<Bill> loadByGrp(String billGrp) {

		return loadBySearch(" a.billgroup='"+billGrp+"'",true);
	}
	public static List<Bill> loadNotComplete() {
		return loadBySearch(null,true);

	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Material> getMaterials() {
		return materials;
	}
	public void setMaterials(Vector<Material> materials) {
		this.materials = materials;
	}
	@Caption(order = 61, value= "���޼�¼")
	public int getBackRepairNum() {
		return backRepairNum;
	}

	@Caption(order = 5, value= "��������")
	public Date getBillDate() {
		return billDate;
	}

	@Caption(order = 9, value= "��Ʊ����")
	public Date getBilledDate() {
		return billedDate;
	}

	@Caption(order = 1, value= "������")
	public String getBillid() {
		return billid;
	}

	@Caption(order = 8, value= "��Ʊ��")
	public String getBillNo() {
		return billNo;
	}

	public String getColor() {
		return color;
	}

	@Caption(order = -1, value= "�ͻ�����")
	public String getCustom() {
		return custom;
	}

	@Caption(order = 31, value= "��ϵ��")
	public String getCustomMan() {
		return customMan;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	@Caption(order = 2, value= "��Ŀ��")
	public String getItem() {
		return item;
	}

	@Caption(order = 7, value= "������������")
	public Date getItemCompleteDate() {
		return itemCompleteDate;
	}

	@Caption(order = 99, value= "��ע")
	public String getNote() {
		return note;
	}

	@Caption(order = 4, value= "����")
	public int getNum() {
		return num;
	}

	@Caption(order = 25, value= "��Э��������")
	public Date getOutBillDate() {
		return outBillDate;
	}
	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	@Transient
	//@Caption(order=56,value="����״̬")
	public String getStatus() {
		if(status!=null) return status;
		status="";

		return status;
	}


	@Transient
	@Caption(order=57,value="��������")
	public float getOperCost() {

		return Userman.loginUser.isManager()? operCost:0;
	}
	@Transient
	@Caption(order=58,value="�ƻ�����")
	public float getPlanCost() {

		return planCost;
	}
	@Caption(order=59,value="��������")
	public String getMeterial() {
		return meterial;
	}
	@Caption(order=70,value="����")
	public String getMeterialz() {
		return meterialz;
	}
	@Caption(order=71,value="���Ϲ��")
	public String getMeterialType() {
		return meterialType;
	}
	@Caption(order=72,value="��������")
	public String getTechCondition() {
		return techCondition;
	}
	@Caption(order=73,value="�������")
	public String getPartName() {
		return partName;
	}
	@Caption(order=74,value="������")
	public String getGjh() {
		return gjh;
	}


	public void setOperCost(float operCost) {
		this.operCost = operCost;
	}
	public void setPlanCost(float planCost) {
		this.planCost = planCost;
	}
	@Caption(order = 21, value= "��Э������")
	public String getOutBillNo() {
		return outBillNo;
	}

	@Caption(order = 22, value= "��Э�ͻ�����")
	public String getOutCustom() {
		return outCustom;
	}

	@Caption(order = 26, value= "��Э��������")
	public Date getOutGetDate() {
		return outGetDate;
	}

	@Transient
	@Caption(order = 24, value= "��Э�ܼ�")
	public float getOutMoney() {
		// TODO Auto-generated method stub

		return Userman.loginUser.isManager()? outPrice * outNum:0;
	}

	@Caption(order = 23, value= "��Э����")
	public long getOutNum() {
		return outNum;
	}

	@Caption(order = 22, value= "��Э����")
	public float getOutPrice() {

		return Userman.loginUser.isManager()? outPrice:0;

	}

	@Caption(order = 3, value= "ͼ��")
	public String getPicid() {
		return picid;
	}
	@Transient
	@Caption(order = 12, value= "�����ܼ�")
	public float getReportMoney() {

		return Userman.loginUser.isManager()? reportPrice * num:0;
	}

	@Caption(order = 11, value= "��������")
	public float getReportPrice() {

		return Userman.loginUser.isManager()? reportPrice:0;
	}

	@Caption(order = 6, value= "Ҫ�󽻻�����")
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
	@Caption(order = 51, value= "���״̬")
	public String getWarehousedStr() {
		return warehoused==1 ? "�����" : "δ���";
	}
	@Transient
	@Caption(order = 52, value= "��Ʊ״̬")
	public String getIsBilled() {
		return isBilled() ? "�ѿ�Ʊ" : "δ��Ʊ";
	}

	@Transient
	@Caption(order = 53, value= "��������״̬")
	public String getIsItemComplete() {
		return isItemComplete() ? "�ѽ���" : "δ����";
	}

	@Transient
	@Caption(order = 54, value= "��Э����״̬")
	public String getIsOutComplete() {
		if(outNum==0) 
			return "����Э�ӹ�";
		else
			return isBilled() ? "�ѽ���" : "δ����";
	}

	@Transient
	@Caption(order = 55, value= "��Э��Ʊ״̬")
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

	public void setWarehoused(int warehoused) {
		this.warehoused = warehoused;
	}
	@Caption(order=0,value="������")
	public String getBillgroup() {
		return billgroup;
	}
	public void setBillgroup(String billgroup) {
		this.billgroup = billgroup;
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
	
	
	public List<Plan> getPlans() {
		return plans;
	}

	public void setPlans(List<Plan> plans) {
		this.plans = plans;
	}

	@Override
	public void remove() {
		// TODO �Զ����ɵķ������
		super.remove();
		if(this.imageUrl!=null) new File(this.imageUrl).delete();
	}

}
