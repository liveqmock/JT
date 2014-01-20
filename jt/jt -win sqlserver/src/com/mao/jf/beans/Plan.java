package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.beans.Transient;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;

import com.mao.jf.beans.annotation.Caption;

public class Plan extends BeanMao {


	
	private int num;
	
	private int sequenceNum;
	private Bill bill;
	private Date produceDate;
	private TreeSet<OperationPlan> operationPlans;
	private boolean completed;
	private Vector<OperationWork> operationWorks;
	private WpCompare wpCompare=new WpCompare();

	public Plan(Bill bill) {
		this.bill=bill;
	}

	public Plan() {
	}



	public Bill getBill() {
		
		return bill;
		
	}
	
	
	
	public HashMap<OperationPlan, Date> getPlanLastDate() {
		HashMap<OperationPlan, Date> lastDateMap = null;
		
		
		return lastDateMap;
	}
	
	
	public boolean getCompleted() {
		return completed;
	}
	@Transient
	public float  getPlanCost() {
		float cost = 0;
		for(OperationPlan operationPlan:operationPlans){
			cost+=operationPlan.getPlanCost();
		}
		return cost;
	}
	public int getNum() {
		if(num==0)return getBill().getNum();
		return num;
	}
	@Transient
	public TreeSet<OperationPlan> getOperationPlans() {
		if(operationPlans==null){
			operationPlans=new TreeSet<OperationPlan>(wpCompare);
			try {
				operationPlans.addAll( OperationPlan.loadAll(OperationPlan.class,"select * from OperationPlan where \"plan\"="+getId()));
					
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException
					| IntrospectionException e) {
				// TODO �Զ����ɵ� catch ��
//				e.printStackTrace();
			}
		}
		return operationPlans;
	}
	@Transient
	public int  getPlanTime() {
		int time = 0;
		for(OperationPlan operationPlan:operationPlans){
			time+=operationPlan.getUseTime();
		}
		return time;
	}
	@Caption(order = 3, value= "ͼ��")
	public String getPic() {
		return bill.getPicid();
	}
	@Caption(order = 2, value= "����ʱ��")
	public Date getProduceDate() {
		return produceDate;
	}
	@Caption(order=1,value="���")
	public int getSequenceNum() {
		if(sequenceNum==0) {
			try(Statement st=SessionData.getConnection().createStatement();
					ResultSet rs=st.executeQuery("select max(sequenceNum) from \"plan\" where bill="+bill.getId());

					){
				if(rs.next()) {
					sequenceNum = rs.getInt(1)+1;
				}
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return sequenceNum;
	}
	@Transient
	public int  getUserTime() {
		int time = 0;
		for(OperationPlan operationPlan:operationPlans){
			time+=operationPlan.getUseTime();
		}
		return time;
	}
	

	
	public boolean isBig() {
		// TODO �Զ����ɵķ������
		return getPlanCost()>bill.getPlanCost();
	}

	@Override
	public void 	save() {
		try {
			if(operationPlans!=null) {
				if(getUserTime()>0||getId()>0)
					super.save();
				for(OperationPlan operationPlan:operationPlans){
					if(operationPlan.getUnitUseTime()> 0) {
						operationPlan.save();
					}else if(operationPlan.getId()>0 ) {
						operationPlan.remove();
					}
				}
			}
			
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | IntrospectionException | SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}


	public void setNum(int num) {
		this.num = num;
	}
	public void setOperationPlans(TreeSet<OperationPlan> operationPlans) {
		this.operationPlans = operationPlans;
	}

	public void setProduceDate(Date produceDate) {
		this.produceDate = produceDate;
	}

	public void setSequenceNum(int sequenceNum) {
		this.sequenceNum = sequenceNum;
	}


	@Transient
	public Vector<OperationWork> getOperationWorks() {
		if(operationWorks==null)
			try {
				operationWorks=OperationWork.loadAll(OperationWork.class, "select a.* from OperationWork a join Operationplan b on a.Operationplan =b.id join \"plan\" c on c.id=b.\"plan\" and c.id= "+getId());
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException
					| IntrospectionException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		return operationWorks;
	}



	private class WpCompare implements Comparator<OperationPlan>{

		@Override
		public int compare(OperationPlan o1, OperationPlan o2) {
			// TODO �Զ����ɵķ������
			return Integer.compare(o1.getSequence(), o2.getSequence());
		}
		
	}

}
