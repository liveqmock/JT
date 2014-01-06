package com.mao.jf.beans;

import java.beans.Transient;
import java.util.Date;

import com.mao.jf.beans.annotation.Caption;

public class OperationPlan extends BeanMao {
	private int sequence;
	private Plan plan;
	private String name;  
	private float cost   ;
	private String note  ;
	private float unitUseTime;
	private float prepareTime;
	private Date planDate;
	
	public OperationPlan(Operation operation) {
		
		setCost(operation.getCost());
		setName(operation.getName());
		setPlan(plan);
	}

	@Transient
	@Caption(value="�ͻ�",order=1)
	public String getCustom() {
		return plan.getBill().getCustom();
	}
	@Transient
	@Caption(value="������",order=2)
	public String getBillgroup() {
		return plan.getBill().getBillgroup();
	}
	@Transient
	@Caption(value="������",order=3)
	public String getBillid() {
		return plan.getBill().getBillid();
	}
	@Transient
	@Caption(value="ͼ��",order=4)
	public String getPiclid() {
		return plan.getBill().getPicid();
	}
	@Caption(order = 5, value= "��������")
	public String getName() {
		return name;
	}
	@Caption(order = 4, value= "�����ƻ���ʱ")
	public float getUnitUseTime() {
		return unitUseTime;
	}
	@Transient
	@Caption(order = 5, value= "�ƻ�����ʱ")
	public float getUseTime() {
		return unitUseTime*plan.getNum()+prepareTime;
	}
	

	@Caption(order = 6, value= "��λ����")
	public float getCost() {
		return cost;
	}
	
	@Caption(order = 7, value= "����ʱ��")	
	public float getPrepareTime() {
		return prepareTime;
	}
	
	@Transient
	@Caption(order = 8, value= "�ƻ�����")
	public float getPlanCost() {
		return cost*(getUnitUseTime()*plan.getNum()+prepareTime);
	}

	@Caption(order =99, value= "��ע")
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

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	

}
