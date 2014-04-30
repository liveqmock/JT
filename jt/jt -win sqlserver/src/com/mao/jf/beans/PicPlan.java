package com.mao.jf.beans;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class PicPlan extends BeanMao {

	@Caption("系统ID")
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	


	@Caption(order=0,value="序号")
	private int sequenceNum;

	@Caption(order=1,value="数量")
	private int num;
	@ManyToOne @JoinColumn(name = "pic", referencedColumnName = "id")
	private PicBean pic;
	private Date planDate;
	@Transient
	private Date startDate;
	@Transient
	private Date endDate;
	@OneToMany(mappedBy = "picPlan", cascade = ALL) 
	private Collection<OperationPlan> operationPlans;

	private int completed;
	
	@OneToMany(mappedBy = "plan",cascade = ALL)
	private Collection<OperationWork> operationWorks;
	
	@OneToOne(fetch = LAZY)	@JoinColumn(name = "prePlan", referencedColumnName = "id")	
	private PicPlan prePlan;
	
	@OneToOne(fetch = LAZY)	@JoinColumn(name = "nextPlan", referencedColumnName = "id")	
	private PicPlan nextPlan;
	@Transient
	@Caption(order=4,value="用时")
	private int useTime;
	public void initOperationPlans() {
		if(operationPlans!=null){
			for(OperationPlan operationPlan:getOperationPlanSet())
				try {
					operationPlan.createPlan();
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
		}
	}

	public PicPlan() {
	}
	public PicPlan(PicBean picBean) {
		this.pic=picBean;
	}
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TreeSet<OperationPlan> getOperationPlanSet() {
		if(operationPlans==null)return new TreeSet<>();
		return new TreeSet<>(operationPlans);
	}
	public Collection<OperationPlan> getOperationPlans() {
		return   operationPlans;
	}
    public  OperationPlan getNextOperationPlan(OperationPlan operationPlan) {
    	return getOperationPlanSet().higher(operationPlan);
	}

    public  OperationPlan getpreOperationPlan(OperationPlan operationPlan) {
    	return getOperationPlanSet().lower(operationPlan);
	}
	public int getCompleted() {
		return completed;
	}
	public boolean isCompleted() {
		return completed==1?true:false;
	}
	public float  getPlanCost() {
		float cost = 0;
		for(OperationPlan operationPlan:getOperationPlans()){
			cost+=operationPlan.getPlanCost();
		}
		return cost;
	}
	public int getNum() {
		if(num==0)return pic.getNum();
		return num;
	}

	public int  getUseTime() {
		int time = 0;
		for(OperationPlan operationPlan:getOperationPlans()){
			time+=operationPlan.getPlanProcessTime();
		}
		return time;
	}
	@Caption(order = 2, value= "开始时间")
	public Date getStartDate() {
		if(getOperationPlans().size()>0) startDate=getOperationPlanSet().first().getStartDate();
		return startDate;
	}
	public int getSequenceNum() {
		if(sequenceNum==0) {
			try{
				sequenceNum=pic.getPlans().size();
			}catch(Exception e){
				
			}
		}
		return sequenceNum;
	}


	public PicBean getPic() {
		return pic;
	}
	public void setPic(PicBean pic) {
		this.pic = pic;
	}
	public boolean isBig() {
		// TODO 自动生成的方法存根
		return getPlanCost()>pic.getPlancost();
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
	public void setCompleted(int completed) {
		this.completed = completed;
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
		if(operationWorks==null)operationWorks=new ArrayList<>();
		return operationWorks;
	}

	public static List<PicPlan> getUnstartPlan() {
		return BeanMao.getBeans(PicPlan.class, " a.id not in (select plan from OperationWork) order by produceDate");
		
	}

	@Caption(order = 3, value= "计划结束时间")
	public Date getEndDate() {
		if(getOperationPlans().size()>0)endDate=getOperationPlanSet().last().getEndDate();
		
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public PicPlan getPrePlan() {
		return prePlan;
	}

	public void setPrePlan(PicPlan prePlan) {
		this.prePlan = prePlan;
	}

	public PicPlan getNextPlan() {
		return nextPlan;
	}

	public void setNextPlan(PicPlan nextPlan) {
		this.nextPlan = nextPlan;
	}

	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		planDate.setTime((planDate.getTime()/86400000)*86400000);
		this.planDate = planDate;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PicPlan other = (PicPlan) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
