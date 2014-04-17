package com.mao.bean;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.AUTO;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "dataCenter")
public class BusinessType {

	@Id
	@GeneratedValue(strategy = AUTO)
	private int id;
	private String name;
	private int weight;
	@OneToMany(mappedBy = "business")
	private Collection<MarketInfo> marketInfos;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return name;
	}
	
	
}
