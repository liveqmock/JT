package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

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

	@ManyToOne
	@JoinColumn(name = "operationPlan", referencedColumnName = "id")
	@Caption(value="������")
	@NotFound(action=NotFoundAction.IGNORE)
    private OperationPlan operationPlan;

	@OneToOne
	@JoinColumn(name = "employee", referencedColumnName = "id")

	@Caption(value="����Ա")
	@NotFound(action=NotFoundAction.IGNORE)
	
	private Employee employee;

	@Caption(value="��������")
	private Date finishDate;

	@Caption(value="Ͷ����")
	private int getNum;

	@Caption(value="�ϸ���")
	private int productNum;

	@Caption(value="������")
	private int scrapNum;

	@Caption(value="��������")
	private String scrapReason;

	@Caption( value= "�ӹ���ʱ")
	private float workTime;

	@OneToOne
	@JoinColumn(name = "superintendent", referencedColumnName = "id")
	@Caption( value= "����")
	private Employee superintendent;

	@OneToOne
	@JoinColumn(name = "firstChecker", referencedColumnName = "id")
	@Caption( value= "�׼���")
	private Employee firstChecker;

	@OneToOne
	@JoinColumn(name = "checker", referencedColumnName = "id")
	@Caption(value="����Ա")
	private Employee checker;

	@OneToOne
	@JoinColumn(name = "prepareEmployee", referencedColumnName = "id")
	@Caption(value="������")
	private Employee prepareEmployee;

	@Caption(value="����ʱ��")
	private float prepareTime;
	@Caption(value="��������ʱ��")
	private float programTime;
	

	@Caption( value= "�׼�����")
	private String	checkData;

	@Caption( value= "��ע")
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

	@Caption(value="δ�깤����")
	public int getUncompletedNum() {
		return getNum-productNum-scrapNum;
	}





	public float getWorkTime() {
		return workTime;
	}
	public float getProgramTime() {
		return programTime;
	}

	public void setProgramTime(float programTime) {
		this.programTime = programTime;
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

}
