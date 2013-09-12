package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.beans.Transient;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.TreeMap;
import java.util.Vector;

public class Plan extends BeanMao {

	public static Vector<Plan> loadallNew() {
		Vector<Plan> plans=new Vector<>();
		try {
			Vector<Bill> bills=Bill.loadAll(Bill.class, "select * from bill where  warehoused=false and id not in (select bill from plan)");
			Vector<Operation> operations;
			operations = Operation.loadAll(Operation.class);
			for(Bill bill:bills){
				Plan plan=new Plan();
				plan.setBill(bill);
				TreeMap<String,OperationPlan> operationPlans=new TreeMap<>();;
				for(Operation oper:operations){
					OperationPlan operationPlan=new OperationPlan(oper);
					operationPlan.setPlan(plan);
					operationPlans.put(oper.getName(),operationPlan);
				}
				plan.setOperationPlans(operationPlans);
				plans.add(plan );
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return plans;
	}
	public static Vector<Plan> loadAllUnCompleted() {       
		Vector<Plan> plans=loadUnCompleted();
		Vector<Plan> newPlans = loadallNew();
		if(plans!=null)
			plans.addAll(newPlans);
		else 
			plans=newPlans;
		
		return plans;
	}
	public static Vector<Plan> loadUnCompleted() {
       
        
		Vector<Plan> plans=null;
		try {
			plans= Plan.loadAll(Plan.class,"Select * from plan where not completed");
			for(Plan plan:plans){
				plan.initOperations();
			}

		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		return plans;
	}
	private int sequenceNum;
	private Bill bill;
	private Date produceDate;
	private int num;

	private TreeMap<String,OperationPlan> operationPlans;

	private boolean completed;
	public Bill getBill() {
		return bill;
	}
	public boolean getCompleted() {
		return completed;
	}
	@Transient
	public double  getCost() {
		double cost = 0;
		for(OperationPlan operationPlan:operationPlans.values()){
			cost+=operationPlan.getCostTotal();
		}
		return cost;
	}
	public int getNum() {
		return num;
	}
	@Transient
	public TreeMap<String,OperationPlan> getOperationPlans() {
		return operationPlans;
	}

	@Transient
	public int  getPlanTime() {
		int time = 0;
		for(OperationPlan operationPlan:operationPlans.values()){
			time+=operationPlan.getTimeTotal();
		}
		return time;
	}

	public Date getProduceDate() {
		return produceDate;
	}
	public int getSequenceNum() {
		if(sequenceNum==0) {
			try(Statement st=SessionData.getConnection().createStatement();
					ResultSet rs=st.executeQuery("select max(sequenceNum) from plan where bill="+bill.getId());

					){
				if(rs.next()) {
					sequenceNum = rs.getInt(1)+1;
				}
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
		for(OperationPlan operationPlan:operationPlans.values()){
			time+=operationPlan.getUseTime();
		}
		return time;
	}


	public void initOperations(){
		TreeMap<String,OperationPlan> operationPlans=new TreeMap<>();;

		try {
			for(OperationPlan operationPlan:OperationPlan.loadAll(OperationPlan.class,"select * from OperationPlan where plan="+getId())){

				operationPlans.put(operationPlan.getName(), operationPlan);
			}
			for(Operation operation :Operation.loadAll(Operation.class)){
				if(!operationPlans.containsKey(operation .getName()))
					operationPlans.put(operation.getName(), new OperationPlan(operation));
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		setOperationPlans(operationPlans);
	}

	public boolean isOut() {
		// TODO 自动生成的方法存根
		return getCost()/num>=bill.getPlanCost();
	}

	@Override
	public void 	save() {
		try {
			if(getUserTime()>0||getId()>0) {
				super.save();
				for(OperationPlan operationPlan:operationPlans.values()){
					if(operationPlan.getUseTime()> 0) {
						operationPlan.save();
					}else if(operationPlan.getUseTime()==0 &&operationPlan.getId()>0) {
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
	public void setOperationPlans(TreeMap<String,OperationPlan> operationPlans) {
		this.operationPlans = operationPlans;
	}

	public void setProduceDate(Date produceDate) {
		this.produceDate = produceDate;
	}

	public void setSequenceNum(int sequenceNum) {
		this.sequenceNum = sequenceNum;
	}


}
