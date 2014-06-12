package com.mao.jf.beans;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


@Entity
public class EquipmentFreeTime {
	@Id
	@GeneratedValue
	private int id;
	@OneToOne
	@JoinColumn(name = "equipment", referencedColumnName = "id")
	private Equipment equipment;
	private Date startDate;
	private Date endDate;
	private long freeTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public long getFreeTime() {
		return freeTime;
	}
	public void setFreeTime(long freeTime) {
		this.freeTime = freeTime;
	}
	
	@PreUpdate
	@PrePersist
	public void computeFreeTime(){
		long realtime = getEndDate().getTime()-getStartDate().getTime();
		long days = realtime/86400000l;
		long dayTime = realtime%86400000l;
		freeTime=dayTime+days*equipment.getWorkTime()*60000l;
		if(getEndDate().getTime()<getStartDate().getTime()){
			freeTime-=86400000l-equipment.getWorkTime()*60000l;
		}else{ 
			long workTime = equipment.getWorkTime()*60000l-getDayTime(getEndDate());		
			if(workTime<0)freeTime-=workTime;
		}
		
	}
	public static long getDayTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute=calendar.get(Calendar.MINUTE);
		int second=calendar.get(Calendar.SECOND);
		int miSecond=calendar.get(Calendar.MILLISECOND);
		return hour*3600000l+minute*60000l+second*1000+miSecond;
	}
	public static long getDayTime(Calendar calendar) {
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute=calendar.get(Calendar.MINUTE);
		int second=calendar.get(Calendar.SECOND);
		int miSecond=calendar.get(Calendar.MILLISECOND);
		return hour*3600000l+minute*60000l+second*1000+miSecond;
	}

}
