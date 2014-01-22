package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.beans.Transient;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.mao.jf.beans.annotation.Caption;

import javax.persistence.JoinTable;

@Entity
public class BillPlan extends BeanMao {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	private int num;

	private int sequenceNum;

	@ManyToOne
	@JoinColumn(name = "bill", referencedColumnName = "id")
	private Bill bill;
	private Date produceDate;

	@OneToMany(mappedBy = "billPlan")
	private Collection<OperationPlan> operationPlans;
	@javax.persistence.Transient
	private ArrayList<OperationPlan> operationPlans2;
	private int completed;
	@javax.persistence.Transient
	private List<OperationWork> operationWorks;

	public BillPlan(Bill bill) {
		this.bill=bill;
	}

	public BillPlan() {
	}



	public Bill getBill() {

		return bill;

	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HashMap<OperationPlan, Date> getPlanLastDate() {
		HashMap<OperationPlan, Date> lastDateMap = null;


		return lastDateMap;
	}


	public List<OperationPlan> getOperationPlans() {
		if(operationPlans!=null&&operationPlans2==null){
			TreeSet<OperationPlan> operationPlanSet = new TreeSet<OperationPlan>(new WpCompare());
			operationPlanSet.addAll(operationPlans);
			operationPlans2=new ArrayList<>(operationPlanSet );
		}
		if(operationPlans2==null) 
			operationPlans2=new ArrayList<OperationPlan>();
		return  operationPlans2;
	}

	public int getCompleted() {
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
	public int  getPlanTime() {
		int time = 0;
		for(OperationPlan operationPlan:operationPlans){
			time+=operationPlan.getUseTime();
		}
		return time;
	}
	@Caption(order = 1, value= "图号")
	public String getPic() {
		return bill.getPicid();
	}
	@Caption(order = 3, value= "创建时间")
	public Date getProduceDate() {
		if(produceDate==null)produceDate=new Date();
		return produceDate;
	}
	@Caption(order=2,value="序号")
	public int getSequenceNum() {
		if(sequenceNum==0) {
			sequenceNum=getBill().getPlans().size();
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


	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed?1:0;
	}


	public void setNum(int num) {
		this.num = num;
	}
	public void setOperationPlans(Collection<OperationPlan> operationPlans) {
		this.operationPlans = operationPlans;
	}

	public void setProduceDate(Date produceDate) {
		this.produceDate = produceDate;
	}

	public void setSequenceNum(int sequenceNum) {
		this.sequenceNum = sequenceNum;
	}


	@Transient
	public List<OperationWork> getOperationWorks() {
		if(operationWorks==null)

			operationWorks=OperationWork.loadAll(OperationWork.class, " a.operationPlan.plan.id= "+getId());

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
