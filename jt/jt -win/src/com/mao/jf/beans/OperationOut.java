package com.mao.jf.beans;


public class OperationOut extends BeanMao {
	private Plan plan;
	private String name;  
	private float materialCost;
	private float materialNum;

	

	public OperationOut() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public OperationOut(Operation operation) {
		setName(operation.getName());
		setPlan(plan);
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
	public Plan getPlan() {
		return plan;
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
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	
	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return name;
	}
	

}
