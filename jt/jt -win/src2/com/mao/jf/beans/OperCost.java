package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.beans.Transient;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Vector;


public class OperCost extends BeanMao {
	
	public static Vector<OperCost> loadAllByBill(Bill bill) {
		String sql="select * from opercost where bill="+bill.getId();
		try {
			return loadAll(OperCost.class, sql);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return null;
	}
	private Date createDate ;
	private Bill bill;
	private PlanCost planCost;
	private Employee operEmployee,checkEmployee;
	private int assignNum,productNum,scrapnum;	
	private float operationNum;
	private String scrapReason,note;
	private String operationName;
	private String operationUnit;
	private float employeePrice;
	private double unitCost;
	
	public OperCost() {
		super();
	}
	public OperCost(Bill bill) {
		super();
		this.bill = bill;
	}
	@ChinaAno(order=8,str="��������")
	public int getAssignNum() {
		return assignNum;
	}

	public Bill getBill() {
		return bill;
	}
	@Transient
	@ChinaAno(order=1,str="������")
	public String getBillId() {
		return bill.getBillid();
	}
	@ChinaAno(order=15,str="����Ա")
	public Employee getCheckEmployee() {
		return checkEmployee;
	}
	@ChinaAno(order=3,str="����")
	public Date getCreateDate() {
		return createDate;
	}

	@Transient
	@ChinaAno(order=2,str="��Ŀ��")
	public String getItemId() {
		return bill.getItem();
	}

	@Transient
	@ChinaAno(order=14,str="δ�깤��")
	public int getLeaveNum() {
		return getAssignNum()-getProductNum()-getScrapnum();
	}


	@ChinaAno(order=99,str="��ע")
	public String getNote() {
		return note;
	}

	@ChinaAno(order=4,str="����")
	public String getOperationName() {
		return planCost==null? operationName:planCost.getOperationName() ;
	}

	@ChinaAno(order=6,str="��������")
	public float getOperationNum() {
		return operationNum;
	}
	@ChinaAno(order=5,str="������λ")
	public String getOperationUnit() {
		return planCost==null? operationUnit:planCost.getOperationUnit() ;
	}

	@ChinaAno(order=9,str="����Ա")
	public Employee getOperEmployee() {
		return operEmployee;
	}

	@ChinaAno(order=10,str="����Ա����")
	public float getEmployeePrice() {
		return operEmployee==null?employeePrice:operEmployee.getWage();
	}
	public PlanCost getPlanCost() {
		return planCost;
	}
	@ChinaAno(order=11,str="��Ʒ��")
	public int getProductNum() {
		return productNum;
	}
	@ChinaAno(order=12,str="��������")
	public int getScrapnum() {
		return scrapnum;
	}
	@ChinaAno(order=13,str="����ԭ��")
	public String getScrapReason() {
		return scrapReason;
	}
	
	@ChinaAno(order=7,str="��λ�ɱ�")
	public double getUnitCost() {
		return planCost==null? unitCost:planCost.getUnitCost();
	}

	
	public void setAssignNum(int assignNum) {
		this.assignNum = assignNum;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	public void setCheckEmployee(Employee checkEmployee) {
		this.checkEmployee = checkEmployee;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setNote(String note) {
		this.note = note;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public void setOperationNum(float operationNum) {
		this.operationNum = operationNum;
	}
	public void setOperationUnit(String operationUnit) {
		this.operationUnit = operationUnit;
	}
	public void setOperEmployee(Employee operEmployee) {
		this.operEmployee = operEmployee;
	}

	public void setPlanCost(PlanCost planCost) {
		this.planCost = planCost;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	public void setScrapnum(int scrapnum) {
		this.scrapnum = scrapnum;
	}
	
	public void setScrapReason(String scrapReason) {
		this.scrapReason = scrapReason;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}

	@Override
	public String toString() {
		return getOperationName();
	}
	public boolean isComplete() {
		// TODO �Զ����ɵķ������
		return getProductNum()+getScrapnum()>0;
	}
	public void setEmployeePrice(float employeePrice) {
		this.employeePrice = employeePrice;
	}
	
	
}
