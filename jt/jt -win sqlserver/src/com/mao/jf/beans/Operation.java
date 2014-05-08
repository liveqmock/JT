package com.mao.jf.beans;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class Operation extends BeanMao {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	@Caption( "设备类型")
	private String name;  
	@Caption("费用")
	private float cost ;
	@Caption("外协")
	private boolean out ;
	public boolean isOut() {
		return out;
	}

	public void setOut(boolean out) {
		this.out = out;
	}

	@Caption( "备注")
	private String note  ;
	@OneToMany(mappedBy = "operation", fetch = EAGER)
	@OrderBy("code")
	private Collection<Equipment> equipments;

	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Caption( "设备数量")
	public int getNum() {
		return  getEquipments().size();
	}
	public float getCost() {
		return cost;
	}
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
		if(equipments==null)equipments=new ArrayList<Equipment>();
		return equipments;
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

	
	
	
}
