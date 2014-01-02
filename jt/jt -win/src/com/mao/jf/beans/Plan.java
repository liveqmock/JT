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

import com.mao.jf.beans.annotation.Caption;

public class Plan extends BeanMao {


	private int sequenceNum;
	private Bill bill;
	private Date produceDate;
	private int num;
	private TreeMap<String,OperationPlan> operationPlans;
	private TreeMap<String,OperationOut> operationOuts;
	private boolean completed;
	private float MaterialNum;
	private float MaterialPrice;
	private Vector<OperationWork> operationWorks;


	public Bill getBill() {
		return bill;
	}


	public static Vector<Plan> loadallNew() {
		Vector<Plan> plans=new Vector<>();
		try {
			Vector<Bill> bills=Bill.loadAll(Bill.class, "select * from bill where  warehoused=false and id not in (select bill from plan)   order by  custom");
			Vector<Operation> operations;
			operations = Operation.loadAll(Operation.class,"select * from operation where not out");
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
	public static Vector<Plan> loadAllUnCompletedByNotOut(String search) {       
		Vector<Plan> plans=loadUnCompletedByNotOut(search);
		Vector<Plan> newPlans = loadallNew();
		if(plans!=null)
			plans.addAll(newPlans);
		else 
			plans=newPlans;

		return plans;
	}
	public static Vector<Plan> loadUnCompleted() {
		return loadUnCompletedBySearch(null);

	}

	public static Vector<Plan> loadUnCompleted(String search) {
		return loadUnCompletedBySearch(search);

	}
	public static Vector<Plan> loadUnCompletedByNotOut(String search) {


		Vector<Plan> plans=null;
		plans= loadUnCompletedBySearch(search);
		for(Plan plan:plans){
			plan.initOperationsByNotOut();
		}

		return plans;
	}
	public static Vector<Plan> loadUnCompletedByOut(String search) {


		Vector<Plan> plans=null;
		plans= loadUnCompletedBySearch(search);
		for(Plan plan:plans){
			plan.initOperationOuts();
		}



		return plans;
	}

	public boolean getCompleted() {
		return completed;
	}
	@Transient
	public float  getPlanCost() {
		float cost = 0;
		for(OperationPlan operationPlan:operationPlans.values()){
			cost+=operationPlan.getPlanCost();
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
	public TreeMap<String,OperationOut> getOperationOuts() {
		return operationOuts;
	}
	@Transient
	public int  getPlanTime() {
		int time = 0;
		for(OperationPlan operationPlan:operationPlans.values()){
			time+=operationPlan.getUseTime();
		}
		return time;
	}

	public Date getProduceDate() {
		return produceDate;
	}
	@Caption(order=2,value="序号")
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
			for(Operation operation :Operation.loadAll(Operation.class,"select * from operation")){
				if(!operationPlans.containsKey(operation .getName())) {
					OperationPlan operationPlan=new OperationPlan(operation);
					operationPlan.setPlan(this);
					operationPlans.put(operation.getName(),operationPlan );
				}
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

	public void initOperationsByNotOut(){
		TreeMap<String,OperationPlan> operationPlans=new TreeMap<>();;

		try {
			for(OperationPlan operationPlan:OperationPlan.loadAll(OperationPlan.class,"select * from OperationPlan where plan="+getId())){

				operationPlans.put(operationPlan.getName(), operationPlan);
			}
			for(Operation operation :Operation.loadAll(Operation.class,"select * from operation where not out")){

				if(!operationPlans.containsKey(operation .getName())) {
					OperationPlan operationPlan=new OperationPlan(operation);
					operationPlan.setPlan(this);
					operationPlans.put(operation.getName(),operationPlan );
				}
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
	public void initOperationOuts(){
		operationOuts=new TreeMap<>();;

		try {
			for(OperationOut operationOut:OperationOut.loadAll(OperationOut.class,"select * from OperationOut where plan="+getId())){

				operationOuts.put(operationOut.getName(), operationOut);
			}
			for(Operation operation :Operation.loadAll(Operation.class,"select * from operation where out")){

				if(!operationOuts.containsKey(operation .getName())) {
					OperationOut operationOut=new OperationOut(operation);
					operationOut.setPlan(this);
					operationOuts.put(operation.getName(),operationOut );
				}
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| IntrospectionException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
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
				for(OperationPlan operationPlan:operationPlans.values()){
					if(operationPlan.getUnitUseTime()> 0) {
						operationPlan.save();
					}else if(operationPlan.getId()>0 ) {
						operationPlan.remove();
					}
				}
			}
			if(operationOuts!=null) {
				super.save();
				for(OperationOut operationOut:operationOuts.values()){
					if(operationOut.getMaterialNum()> 0) {
						operationOut.save();
					}else if(operationOut.getId()>0 ) {
						operationOut.remove();
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



	public float getMaterialNum() {
		return MaterialNum;
	}


	public void setMaterialNum(float materialNum) {
		MaterialNum = materialNum;
	}


	public float getMaterialPrice() {
		return MaterialPrice;
	}


	public void setMaterialPrice(float materialPrice) {
		MaterialPrice = materialPrice;
	}





	public void setOperationOuts(TreeMap<String,OperationOut> operationOuts) {
		this.operationOuts = operationOuts;
	}


	@Transient
	public Vector<OperationWork> getOperationWorks() {
		if(operationWorks==null)
			try {
				operationWorks=OperationWork.loadAll(OperationWork.class, "select a.* from OperationWork a join Operationplan b on a.Operationplan =b.id join plan c on c.id=b.plan and c.id= "+getId());
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException
					| IntrospectionException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		return operationWorks;
	}

	public static Vector<Plan> loadUnCompletedByPicId(String search) {
		
		if(search!=null&&!search.equals(""))
			return loadUnCompletedBySearch(" and picid like '%"+search+"%'");
		else
			return loadUnCompletedBySearch(null);
	}

	public static Vector<Plan> loadUnCompletedBySearch(String search) {
		if(search==null)
			search="";
		Vector<Plan> plans=null;
		try {
			plans= Plan.loadAll(Plan.class,"Select distinct a.*,b.custom from plan a join bill b on a.bill=b.id and b.itemCompleteDate is null "+search+" order by b.custom");
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





}
