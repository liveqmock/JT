package com.mao.jf.beans;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class BillTime extends BeanMao {
	private String custom;
	private String billid;
	private String picid;
	private int bill;
	private Date produceDate;

	private Vector<OperationTime> operationTimes;

	public int getId() {
		return id;
	}

	public String getCustom() {
		return custom;
	}

	public String getBillid() {
		return billid;
	}

	public String getPicid() {
		return picid;
	}

	public Date getProduceDate() {
		return produceDate;
	}
	
	public Vector<OperationTime> getOperationTimes() {
		if(operationTimes==null){
			operationTimes=new Vector<OperationTime>();
			try {
				operationTimes=loadAll(OperationTime.class,"select bill,operationName,plantime from operationTime where bill=" + this.bill);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException
					| IntrospectionException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return operationTimes;
	}
	public OperationTime getOperationTimeByName(String operationName) {
		for(int row=0 ;row<getOperationTimes().size();row++){
			OperationTime operationTime=	operationTimes.get(row);
			if(operationTime.getOperationName().equals(operationName))
				return operationTime;
		}
		return null;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public void setBillid(String billid) {
		this.billid = billid;
	}

	public void setPicid(String picid) {
		this.picid = picid;
	}

	public void setProduceDate(Date produceDate) {
		this.produceDate = produceDate;
	}

	public void setOperationTimes(Vector<OperationTime> operationTimes) {
		this.operationTimes = operationTimes;
	}

	public float getTotalPlanTime() {
		float t=0;
		for(OperationTime operationTime: operationTimes)
			t+=operationTime.getPlantime();
		return t;
	}

	public int getBill() {
		return bill;
	}

	public void setBill(int bill) {
		this.bill = bill;
	}


}
