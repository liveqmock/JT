package com.mao.jf.beans;

import java.beans.Transient;

public class OperationPlan extends BeanMao {
	private Plan plan;
	private String name;  
	private float cost   ;
	private String note  ;
	private float unitUseTime;
	private float prepareTime;
	
	
	public OperationPlan(Operation operation) {
		
		setCost(operation.getCost());
		setName(operation.getName());
		setPlan(plan);
	}

	@Transient
	@ChinaAno(str="�ͻ�",order=1)
	public String getCustom() {
		return plan.getBill().getCustom();
	}
	@Transient
	@ChinaAno(str="������",order=2)
	public String getBillgroup() {
		return plan.getBill().getBillgroup();
	}
	@Transient
	@ChinaAno(str="������",order=3)
	public String getBillid() {
		return plan.getBill().getBillid();
	}
	@Transient
	@ChinaAno(str="ͼ��",order=4)
	public String getPiclid() {
		return plan.getBill().getPicid();
	}
	@ChinaAno(order = 5, str = "��������")
	public String getName() {
		return name;
	}
	@ChinaAno(order = 4, str = "�����ƻ���ʱ")
	public float getUnitUseTime() {
		return unitUseTime;
	}
	@Transient
	@ChinaAno(order = 5, str = "�ƻ�����ʱ")
	public float getUseTime() {
		return unitUseTime*plan.getNum()+prepareTime;
	}
	

	@ChinaAno(order = 6, str = "��λ����")
	public float getCost() {
		return cost;
	}
	
	@ChinaAno(order = 7, str = "����ʱ��")	
	public float getPrepareTime() {
		return prepareTime;
	}
	
	@Transient
	@ChinaAno(order = 8, str = "�ƻ�����")
	public float getPlanCost() {
		return cost*(getUnitUseTime()*plan.getNum()+prepareTime);
	}

	@ChinaAno(order =99, str = "��ע")
	public String getNote() {
		return note;
	}



	public Plan getPlan() {
		return plan;
	}
	


	public void setUnitUseTime(float unitUseTime) {
		this.unitUseTime = unitUseTime;
	}
	public OperationPlan() {
		super();
	}
	

	public void setCost(float cost) {
		this.cost = cost;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public void setPrepareTime(float prepareTime) {
		this.prepareTime = prepareTime;
	}

	@Override
	public String toString() {
		// TODO �Զ����ɵķ������
		return name;
	}
	

}
