package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.beans.Transient;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class Plan extends BeanMao {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	private int num;
	
	private int sequenceNum;
	
	@ManyToOne
	@JoinColumn(name = "bill", referencedColumnName = "id")
	private Bill bill;
	private Date produceDate;
	
	@OneToMany(mappedBy = "plan")
	private ArrayList<OperationPlan> operationPlans;
	private int completed;
	@javax.persistence.Transient
	private List<OperationWork> operationWorks;

	public Plan(Bill bill) {
		this.bill=bill;
	}

	public Plan() {
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
	

	public ArrayList<OperationPlan> getOperationPlans() {
		return operationPlans;
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


	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed?1:0;
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
	public List<OperationWork> getOperationWorks() {
		if(operationWorks==null)
			
		operationWorks=OperationWork.loadAll(OperationWork.class, " a.operationPlan.plan= "+getId());
			
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
