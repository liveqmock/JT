package com.mao.jf.beans;

import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class BillPlan extends BeanMao {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int num;

	private int sequenceNum;

	@ManyToOne @JoinColumn(name = "bill", referencedColumnName = "id")
	private Bill bill;
	
	@Transient
	private Date startDate;
	@Transient
	private Date endDate;

	@OneToMany(mappedBy = "billPlan") @OrderBy("sequence")
	private Collection<OperationPlan> operationPlans;
	
	private int completed;
	
	@OneToMany(mappedBy = "plan")
	private Collection<OperationWork> operationWorks;
	
	@OneToOne(fetch = LAZY)	@JoinColumn(name = "prePlan", referencedColumnName = "id")	
	private BillPlan prePlan;
	
	@OneToOne(fetch = LAZY)	@JoinColumn(name = "nextPlan", referencedColumnName = "id")	
	private BillPlan nextPlan;

	public void deleteOperationPlans() {
		if(operationPlans!=null){
			for(OperationPlan operationPlan:operationPlans)
				operationPlan.deleteEquipmentPlans();
		}
	}
	public void initOperationPlans() {
		if(operationPlans!=null){
			for(OperationPlan operationPlan:operationPlans)
				try {
					operationPlan.createPlan();
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
		}
	}
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

	public ArrayList<OperationPlan> getOperationPlans() {
		if(!(operationPlans instanceof ArrayList<?>) && operationPlans!=null){
			operationPlans=new ArrayList<>(operationPlans );
		}
		if(operationPlans==null)operationPlans=new ArrayList<>();
		return  (ArrayList<OperationPlan>) operationPlans;
	}
    public  OperationPlan getNextOperationPlan(OperationPlan operationPlan) {
    	if(getOperationPlans().indexOf(operationPlan)<getOperationPlans().size()-1)
		return getOperationPlans().get(getOperationPlans().indexOf(operationPlan)+1);
    	else return null;
	}

    public  OperationPlan getpreOperationPlan(OperationPlan operationPlan) {
    	if(getOperationPlans().indexOf(operationPlan)>0)
		return getOperationPlans().get(getOperationPlans().indexOf(operationPlan)-1);
    	else return null;
	}
	public int getCompleted() {
		return completed;
	}
	public boolean isCompleted() {
		return completed==1?true:false;
	}
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

	public int  getPlanTime() {
		int time = 0;
		for(OperationPlan operationPlan:operationPlans){
			time+=operationPlan.getPlanProcessTime();
		}
		return time;
	}
	@Caption(order = 1, value= "客户")
	public String getCustom() {
		return bill.getCustom();
	}
	@Caption(order = 2, value= "图号")
	public String getPic() {
		return bill.getPicid();
	}
	@Caption(order = 3, value= "开始时间")
	public Date getStartDate() {
		if(startDate==null && getOperationPlans()!=null&&getOperationPlans().size()>0)startDate=getOperationPlans().iterator().next().getStartDate();
		return startDate;
	}
	@Caption(order=0,value="序号")
	public int getSequenceNum() {
		if(sequenceNum==0) {
			try{
				sequenceNum=getBill().getPlans().size();
			}catch(Exception e){
				
			}
		}
		return sequenceNum;
	}

	@Caption(order = 5, value= "耗时")
	public int  getPlanProcessTime() {
		int time = 0;
		for(OperationPlan operationPlan:operationPlans){
			time+=operationPlan.getPlanProcessTime();
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
		if(!(operationPlans instanceof ArrayList<?>)&&operationPlans!=null){
			operationPlans=new ArrayList<>(operationPlans);
		}
		if(operationPlans==null)			operationPlans=new ArrayList<>();
		this.operationPlans = operationPlans;
	}

	public void setOperationWorks(Collection<OperationWork> operationWorks) {
		this.operationWorks = operationWorks;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setSequenceNum(int sequenceNum) {
		this.sequenceNum = sequenceNum;
	}


	public Collection<OperationWork> getOperationWorks() {
		if(operationWorks==null)operationPlans=new ArrayList<>();
		return operationWorks;
	}

	public static List<BillPlan> getUnstartPlan() {
		return BeanMao.getBeans(BillPlan.class, " a.id not in (select plan from OperationWork) order by produceDate");
		
	}

	@Caption(order = 4, value= "结束时间")
	public Date getEndDate() {
			if(endDate==null && getOperationPlans()!=null&&getOperationPlans().size()>0){
				endDate=getOperationPlans().get(getOperationPlans().size()-1).getEndDate();
			}
		
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BillPlan getPrePlan() {
		return prePlan;
	}

	public void setPrePlan(BillPlan prePlan) {
		this.prePlan = prePlan;
	}

	public BillPlan getNextPlan() {
		return nextPlan;
	}

	public void setNextPlan(BillPlan nextPlan) {
		this.nextPlan = nextPlan;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillPlan other = (BillPlan) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

}
