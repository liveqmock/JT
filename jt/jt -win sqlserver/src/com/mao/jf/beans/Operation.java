package com.mao.jf.beans;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.mao.jf.beans.annotation.Caption;

import javax.persistence.OrderBy;

@Entity
public class Operation extends BeanMao {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	private String name;  
	private float cost ;
	private String note  ;
	private int num  ;
	@OneToMany(mappedBy = "operation", fetch = EAGER)
	@OrderBy("code")
	private Collection<Equipment> equipments;


	
	@Caption(order = 1, value= "��������")
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Caption(order = 3, value= "�豸����")
	public int getNum() {
		return num;
	}
	@Caption(order = 5, value= "����")
	public float getCost() {
		return cost;
	}
	@Caption(order = 6, value= "��ע")
	public String getNote() {
		return note;
	}
	

	public void setCost(float cost) {
		this.cost = cost;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Collection<Equipment> getEquipments() {
		return equipments;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setEquipments(Collection<Equipment> equipments) {
		this.equipments = equipments;
	}

	@Override
	public String toString() {
		return name;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@PrePersist
	@PreUpdate
	public void prePersist() {
		if(equipments==null )equipments=new ArrayList<>();
		if(num>equipments.size()){
			for(int i=equipments.size();i<num;i++){
				Equipment equipment=new Equipment(this);
				equipment.setCode(i+1);
				BeanMao.saveBean(equipment);
				equipments.add(equipment);
			}
				
		}
		if(num<equipments.size()){
			ArrayList<Equipment> newEquipments = new ArrayList<>();
			int i=0;
			Iterator<Equipment> it = equipments.iterator();
			while(i<num){
				newEquipments.add(it.next());
				i++;
			}
			for(Equipment equipment:equipments){
				if(!newEquipments.contains(equipment)){
					BeanMao.removeBean(equipment);
				}
					
			}
			equipments.clear();
			equipments.addAll(newEquipments);
		}
	}
	
	
}
