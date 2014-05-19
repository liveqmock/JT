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

import org.apache.commons.collections.bag.TreeBag;

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
		long days = realtime/86400000;
		long hour = realtime%86400000;
		freeTime=hour+days*equipment.getWorkTime();
		Calendar calendarStart=Calendar.getInstance();
		calendarStart.setTime(startDate);
		if(calendarStart.get(Calendar.HOUR_OF_DAY)<8){
			calendarStart.set(Calendar.HOUR_OF_DAY, 8);
			calendarStart.set(Calendar.MINUTE, 0);
			calendarStart.set(Calendar.SECOND, 0);
			freeTime-=(calendarStart.getTimeInMillis()-startDate.getTime());
		}
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(endDate);
		if(calendarEnd.get(Calendar.HOUR_OF_DAY)<8){
			calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
			calendarEnd.set(Calendar.MINUTE, 0);
			calendarEnd.set(Calendar.SECOND, 0);
			freeTime-=(endDate.getTime()-calendarEnd.getTimeInMillis());
		}
		
		if(getDayTime(calendarStart)>getDayTime(calendarEnd)
				&&calendarEnd.get(Calendar.HOUR_OF_DAY)>8){
			
			freeTime-=8*3600000l;
		}
		if(freeTime<0)freeTime=0;
		
	}
	
	public long getDayTime(Calendar calendar) {
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute=calendar.get(Calendar.MINUTE);
		int second=calendar.get(Calendar.SECOND);
		int miSecond=calendar.get(Calendar.MILLISECOND);
		return hour*3600000l+minute*60000l+second*1000+miSecond;
	}

}
