package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class OperationWork extends BeanMao {

	@ManyToOne
	@JoinColumn(name = "billplan", referencedColumnName = "id")
	private PicPlan plan;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
     
	@Transient
	@Caption("图号")
	private String picid;
	
	@ManyToOne
	@JoinColumn(name = "operationPlan", referencedColumnName = "id")
	@Caption(value="工序名")
	@NotFound(action=NotFoundAction.IGNORE)
    private OperationPlan operationPlan;

	@OneToOne
	@JoinColumn(name = "employee", referencedColumnName = "id")

	@Caption(value="操作员")
	@NotFound(action=NotFoundAction.IGNORE)
	
	private Employee employee;

	@Caption(value="操作日期")
	private Date finishDate;

	@Caption(value="投入数")
	private int getNum;

	@Caption(value="合格数")
	private int productNum;

	@Caption(value="不良数")
	private int scrapNum;

	@Caption(value="不良描述")
	private String scrapReason;

	@Caption( value= "加工用时")
	private float workTime;

	@OneToOne
	@JoinColumn(name = "superintendent", referencedColumnName = "id")
	@Caption( value= "主管")
	private Employee superintendent;

	@OneToOne
	@JoinColumn(name = "firstChecker", referencedColumnName = "id")
	@Caption( value= "首检人")
	private Employee firstChecker;

	@OneToOne
	@JoinColumn(name = "checker", referencedColumnName = "id")
	@Caption(value="检验员")
	private Employee checker;

	@OneToOne
	@JoinColumn(name = "prepareEmployee", referencedColumnName = "id")
	@Caption(value="调机人")
	private Employee prepareEmployee;

	@Caption(value="调机时间")
	private float prepareTime;
	@Caption(value="单件程序时间")
	private long programTime;
	

	@Caption( value= "首检数据")
	private String	checkData;

	@Caption( value= "备注")
	private String note;
	public OperationWork() {
		super();
	}

	public OperationWork(OperationPlan operationPlan) {

		setOperationPlan(operationPlan);

	}
	public OperationWork(PicPlan plan) {
		this.plan=plan;

	}
	public int getOperationId() {
		return getOperationPlan().getId();
	}
	public Employee getChecker() {
		return checker;
	}
	public Employee getEmployee() {
		return employee;
	}

	public Date getFinishDate() {
		return finishDate;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getGetNum() {
		return getNum;
	}
	public String getNote() {
		return note;
	}
	public OperationPlan getOperationPlan() {
		return operationPlan;
	}
	public PicPlan getPlan() {
		return plan;
	}



	public Employee getPrepareEmployee() {
		return prepareEmployee;
	}
	public float getPrepareTime() {
		return prepareTime;
	}


	public int getProductNum() {
		return productNum;
	}
	public int getScrapNum() {
		return scrapNum;
	}

	public String getScrapReason() {
		return scrapReason;
	}

	public Employee getSuperintendent() {
		return superintendent;
	}

	public void setSuperintendent(Employee superintendent) {
		this.superintendent = superintendent;
	}

	public Employee getFirstChecker() {
		return firstChecker;
	}

	public void setFirstChecker(Employee firstChecker) {
		this.firstChecker = firstChecker;
	}

	@Caption(value="未完工数量")
	public int getUncompletedNum() {
		return getNum-productNum-scrapNum;
	}





	public float getProgramTime() {
		return programTime/60000f;
	}


	public float getWorkTime() {
		return workTime;
	}
	
	public Date getProgramTimeDate() {
		return new Date(programTime-8*3600000);
	}

	
	public void setProgramTimeDate(Date programTime) {
		this.programTime = programTime.getTime()+8*3600000;
	}

	public void setChecker(Employee checker) {
		this.checker = checker;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public void setGetNum(int getNum) {
		this.getNum = getNum;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setOperationPlan(OperationPlan operationPlan) {
		this.operationPlan = operationPlan;
		if(operationPlan!=null)
			this.plan=operationPlan.getPicPlan();
	}


	public void setPrepareEmployee(Employee prepareEmployee) {
		this.prepareEmployee = prepareEmployee;
	}

	public void setPrepareTime(float prepareTime) {
		this.prepareTime = prepareTime;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	public void setScrapNum(int scrapNum) {
		this.scrapNum = scrapNum;
	}

	public void setScrapReason(String scrapReason) {
		this.scrapReason = scrapReason;
	}

	public void setWorkTime(float workTime) {
		this.workTime = workTime;
	}

	public void setPlan(PicPlan plan) {
		this.plan = plan;
	}

	public String getCheckData() {
		return checkData;
	}

	public void setCheckData(String checkData) {
		this.checkData = checkData;
	}

	public String getPicid() {
		return getPlan().getPicId();
	}

}
