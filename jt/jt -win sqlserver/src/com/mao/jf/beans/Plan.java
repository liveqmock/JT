package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.beans.Transient;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	private ArrayList<OperationPlan> operationPlans;
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
	public ArrayList<OperationPlan> getOperationPlans() {
		if(operationPlans==null){
			operationPlans=new ArrayList<OperationPlan>();
			try {
				operationPlans.addAll( OperationPlan.loadAll(OperationPlan.class,"select * from OperationPlan where \"plan\"="+getId()));
					
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException
					| IntrospectionException e) {
				// TODO 自动生成的 catch 块
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
	@Caption(order = 3, value= "图号")
	public String getPic() {
		return bill.getPicid();
	}
	@Caption(order = 2, value= "创建时间")
	public Date getProduceDate() {
		if(produceDate==null)produceDate=new Date();
		return produceDate;
	}
	@Caption(order=1,value="序号")
	public int getSequenceNum() {
		if(sequenceNum==0) {
			try(Statement st=SessionData.getConnection().createStatement();
					ResultSet rs=st.executeQuery("select max(sequenceNum) from \"plan\" where bill="+bill.getId());

					){
				if(rs.next()) {
					sequenceNum = rs.getInt(1)+1;
				}else sequenceNum=1;
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
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
		// TODO 自动生成的方法存根
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
			// TODO 自动生成的 catch 块
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
	public void setOperationPlans(ArrayList<OperationPlan> operationPlans) {
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
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		return operationWorks;
	}



	private class WpCompare implements Comparator<OperationPlan>{

		@Override
		public int compare(OperationPlan o1, OperationPlan o2) {
			// TODO 自动生成的方法存根
			int a = Integer.compare(o1.getSequence(), o2.getSequence());
			try{return a==0?o1.getName().compareTo(o2.getName()):a;}catch (Exception e){
				return 0;
			}
		}
		
	}

}
