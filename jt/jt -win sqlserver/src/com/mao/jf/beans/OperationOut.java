package com.mao.jf.beans;

import com.mao.jf.beans.annotation.Caption;


public class OperationOut extends BeanMao {
	private Plan plan;
	private String name;  
	private float materialCost;
	private float materialNum;

	

	public OperationOut() {
		super();
		// TODO �Զ����ɵĹ��캯�����
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

	@Caption(order = 1, value= "��������")
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
		// TODO �Զ����ɵķ������
		return name;
	}
	

}
