package com.mao.jf.beans;

import java.beans.Transient;

public class OperationPlan extends BeanMao {
	private Plan plan;
	private String name;  
	private int  useTime ; 
	private int  num ; 
	private boolean out  ;
	private double cost   ;
	private float materialCost;
	private float materialNum;
	private String note  ;

	
	private int getNum ;
	private int scrapNum;
	private int productNum;
	private float workTime;
	private float prepareCost;
	
	public OperationPlan() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public OperationPlan(Operation operation) {
		// TODO 自动生成的构造函数存根
		setCost(operation.getCost());
		setName(operation.getName());
		setNum(operation.getNum());
		setOut(operation .getOut());
		setPlan(plan);
	}
	@ChinaAno(order = 5, str = "费用")
	public double getCost() {
		return cost;
	}
	@Transient
	public double  getCostTotal() {
		return cost*useTime;
	}
	@Transient
	public double  getWorkCostTotal() {
		return cost*workTime;
	}
	
	public int getGetNum() {
		return getNum;
	}


	public float getMaterialCost() {
		return materialCost;
	}
	public float getMaterialNum() {
		return materialNum;
	}

	@ChinaAno(order = 1, str = "工序名称")
	public String getName() {
		return name;
	}
	@ChinaAno(order =99, str = "备注")
	public String getNote() {
		return note;
	}
	@ChinaAno(order = 3, str = "设备数量")
	public int getNum() {
		return num;
	}


	@ChinaAno(order = 4, str = "外发")
	public boolean getOut() {
		return out;
	}
	public Plan getPlan() {
		return plan;
	}
	public float getPrepareCost() {
		return prepareCost;
	}
	public int getProductNum() {
		return productNum;
	}
	public int getScrapNum() {
		return scrapNum;
	}

	@Transient
	public int  getTimeTotal() {
		return useTime/num;
	}

	@ChinaAno(order = 2, str = "用时")
	public int getUseTime() {
		return useTime;
	}

	public float getWorkTime() {
		return workTime;
	}


	public void setCost(double cost) {
		this.cost = cost;
	}
	public void setGetNum(int getNum) {
		this.getNum = getNum;
	}
	public void setMaterialCost(float materialCost) {
		this.materialCost = materialCost;
	}
	public void setMaterialNum(float materialNum) {
		this.materialNum = materialNum;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void setOut(boolean out) {
		this.out = out;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public void setPrepareCost(float prepareCost) {
		this.prepareCost = prepareCost;
	}
	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}
	public void setScrapNum(int scrapNum) {
		this.scrapNum = scrapNum;
	}
	public void setUseTime(int useTime) {
		this.useTime = useTime;
	}
	public void setWorkTime(float workTime) {
		this.workTime = workTime;
	}
	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return name;
	}

}
