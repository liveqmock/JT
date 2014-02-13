package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class OperationWork extends BeanMao {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "operationPlan", referencedColumnName = "id")
	private OperationPlan operationPlan;	
	@OneToOne
	@JoinColumn(name = "employee", referencedColumnName = "id")
	private Employee employee;
	private Date finishDate;
	private int getNum;
	private int productNum;
	private int scrapNum;
	private String scrapReason;
	private float workTime;
	private float workCost;	
	@OneToOne
	@JoinColumn(name = "checker", referencedColumnName = "id")
	private Employee checker;
	@OneToOne
	@JoinColumn(name = "prepareEmployee", referencedColumnName = "id")
	private Employee prepareEmployee;
	private float prepareTime;
	private float prepareCost;
	private float planCost;
	@ManyToOne
	@JoinColumn(name = "billplan", referencedColumnName = "id")
	private BillPlan plan;
	private String note;
	public OperationWork() {
		super();
	}

	public OperationWork(OperationPlan operationPlan) {
		
		setOperationPlan(operationPlan);
		
	}
	public OperationWork(BillPlan plan) {
		this.plan=plan;
		
	}

	@Caption(value="������",order=1)
	@Transient
	public String getOperationName() {
		return operationPlan.getName();
	}
	@Caption(value="����Ա",order=17)
	public Employee getChecker() {
		return checker;
	}
	@Caption(value="����Ա",order=4)
	public Employee getEmployee() {
		return employee;
	}

	@Caption(value="�깤����",order=18)
	public Date getFinishDate() {
		return finishDate;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Caption(value="ʵ����",order=4)
	public int getGetNum() {
		return getNum;
	}
	@Caption(order =99, value= "��ע")
	public String getNote() {
		return note;
	}
	public OperationPlan getOperationPlan() {
		return operationPlan;
	}
	public BillPlan getPlan() {
		return plan;
	}
	@Caption(order = 7, value= "�ƻ�����")
	public float getPlanCost() {
		return planCost==0?(getOperationPlan()==null?0: getOperationPlan().getPlanCost()):planCost;
	}
	public void setPlanCost(float planCost) {
		this.planCost = planCost;
	}


	@Caption(value="�ӹ�����",order=11)
	public float getWorkCost() {
		return workCost==0?(employee==null?0: workTime*employee.getWage()):workCost;
	}
	public void setWorkCost(float workCost) {
		this.workCost = workCost;
	}


	@Caption(value="��������",order=14)
	public float getPrepareCost() {
		return prepareCost==0?(prepareEmployee==null?0: prepareTime*prepareEmployee.getWage()):prepareCost;
	}
	@Caption(value="������",order=12)
	public Employee getPrepareEmployee() {
		return prepareEmployee;
	}
	@Caption(value="����ʱ��",order=13)
	public float getPrepareTime() {
		return prepareTime;
	}


	@Caption(value="��Ʒ��",order=6)
	public int getProductNum() {
		return productNum;
	}
	@Caption(value="������",order=7)
	public int getScrapNum() {
		return scrapNum;
	}

	@Caption(value="����ԭ��",order=8)
	public String getScrapReason() {
		return scrapReason;
	}
	
	@Transient
	@Caption(value="δ�깤����",order=9)
	public int getUncompletedNum() {
		return getNum-productNum-scrapNum;
	}





	@Transient
	@Caption(order = 15, value= "ʵ�ʷ���")
	public float getCost() {
		return getWorkCost()+getPrepareCost();
	}
	

	@Caption(order = 10, value= "�ӹ���ʱ")
	public float getWorkTime() {
		return workTime;
	}
	public void setChecker(Employee checker) {
		this.checker = checker;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public void setGetNum(int getNum) {
		this.getNum = getNum;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setOperationPlan(OperationPlan operationPlan) {
		this.operationPlan = operationPlan;
		if(operationPlan!=null)
			this.plan=operationPlan.getBillPlan();
	}

	public void setPrepareCost(float prepareCost) {
		this.prepareCost = prepareCost;
	}

	public void setPrepareEmployee(Employee prepareEmployee) {
		this.prepareEmployee = prepareEmployee;
	}

	public void setPrepareTime(float prepareTime) {
		this.prepareTime = prepareTime;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	public void setScrapNum(int scrapNum) {
		this.scrapNum = scrapNum;
	}

	public void setScrapReason(String scrapReason) {
		this.scrapReason = scrapReason;
	}

	public void setWorkTime(float workTime) {
		this.workTime = workTime;
	}

	public void setPlan(BillPlan plan) {
		this.plan = plan;
	}
	

}
