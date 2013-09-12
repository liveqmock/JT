package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.beans.Transient;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

import javax.swing.ComboBoxModel;

public class PlanCost extends BeanMao {
	private int sequence;
	private Date planDate;
	private Bill bill ;
	private Operation operation;	
	private String operationName;	
	private String operationUnit;	
	private double unitCost;	
	private float operationNum ;
	private boolean isOut ;
	private String note ;
	public PlanCost() {
	}

	public PlanCost(Bill bean) {
		this.bill=bean;
	}

	@Transient
	public Vector<OperCost> getBeforOders() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
		if(sequence==1) return null;

		String sqlString="select * from Opercost a join plancost b on a.plancost=b.id where productNum>0 and a.bill="+bill.getId()+" and sequence="+(sequence-1);
		return loadAll(OperCost.class, sqlString);

	}
	@Transient
	public Vector<OperCost> getNowOders() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
		
			String sqlString="select * from opercost where plancost="+getId();
			return loadAll(OperCost.class, sqlString);
	}
	@Transient
	public int getMaxAsignNum() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
		int before=0,now=0;
		Vector<OperCost> a = getBeforOders();
		if(a==null)
			return Integer.MAX_VALUE;
		else
			for(OperCost cost: a)
				before+=cost.getProductNum();
		a=getNowOders();
		if(a==null)
			now=0;
		else
			for(OperCost cost:a )
				now+=cost.getAssignNum();
		return before-now;
	}
	public static Vector<PlanCost> loadAllByBill(Bill bill) {
		String sql="select * from plancost where bill="+bill.getId();
		try {
			return loadAll(PlanCost.class, sql);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return null;
	}

	@ChinaAno(order=-1,str="����˳��")
	public int getSequence() {
		return sequence;
	}
	@ChinaAno(order=7,str="��������")
	public float getOperationNum() {
		return operationNum;
	}


	@Transient
	@ChinaAno(order=1,str="������")
	public String getBillId() {
		return bill.getBillid();
	}
	@Transient
	@ChinaAno(order=2,str="��Ŀ��")
	public String getItemId() {
		return bill.getItem();
	}
	public Bill getBill() {
		return bill;
	}

	@ChinaAno(order=99,str="��ע")
	public String getNote() {
		return note;
	}

	@Transient
	public Operation getOperation() {
		return operation;
	}

	@ChinaAno(order=4,str="����")
	public String getOperationName() {
		return operation==null? operationName:operation.getName() ;
	}

	@ChinaAno(order=5,str="������λ")
	public String getOperationUnit() {
		return operation==null? operationUnit:operation.getUnit() ;
	}
	@ChinaAno(order=3,str="ʱ��")
	public Date getPlanDate() {
		return planDate;
	}

	@ChinaAno(order=9,str="��λ�ɱ�")
	public double getUnitCost() {
		System.out.println(unitCost);
		return unitCost==0?(operation==null?0:operation.getCost()):unitCost;
	}	

	@ChinaAno(order=10,str="�Ƿ���Э�ӹ�")
	public boolean getIsOut() {
		return operation==null? isOut:operation.getOut() ;
	}
	public void setOperationNum(float operationNum) {
		this.operationNum = operationNum;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public void setOperationUnit(String operationUnit) {
		this.operationUnit = operationUnit;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}


	public void setSequence(int order) {
		this.sequence = order;
	}

	@Override
	public String toString() {
		return getOperationName();
	}

	
	@Transient
	public Vector<OperCost> getOpers() {
		try {
			return OperCost.loadAll(OperCost.class, "select * from opercost where plancost="+getId());
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO �Զ����ɵ� catch ��
			return null;
		}
		
	}

	public boolean hasOper() {
		Vector<OperCost> opers = getOpers();
		return opers!=null&opers.size()>0;
	}

	public static Vector<PlanCost> loadAllByBillHasAssing(Bill bill) {
		Vector<PlanCost> aa = loadAllByBill(bill);
		Vector<PlanCost> bb=new Vector<PlanCost>();
		for(PlanCost planCost:aa) {
			try {
				if(planCost.getMaxAsignNum()>0)bb.add(planCost);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException
					| IntrospectionException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return bb;
	}
	
    public boolean isLastPlan() {
	   try(Statement st=SessionData.getConnection().createStatement()){
		   ResultSet rs = st.executeQuery("select count(1) from plancost where bill="+bill.getId()+" and sequence="+(sequence+1));
		   if(rs.next()) return rs.getInt(1)>0;
		   return false;
				   
	   } catch (SQLException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace(); return false;
	}
}



	public void setIsOut(boolean isOut) {
		this.isOut = isOut;
	}

}
