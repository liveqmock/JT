package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class Material  extends BeanMao{
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "bill", referencedColumnName = "id")
	private PicBean pic; 
	
	@Caption("材料名称")
	private String name ;
	@Caption("计价单位")
	private String unitName ;
	@Caption("单价")
	private float unitCost ;
	@Caption("数量")
	private float num ;
	@Caption("输入员")
	@OneToOne
	@JoinColumn(name = "enterEmployee", referencedColumnName = "id")
	private Userman enterEmployee ;
	
	public Material() {
	}
	public Material(PicBean pic) {
		super();
		this.pic = pic;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getUnitName() {
		return unitName;
	}
	public float getUnitCost() {
		return unitCost;
	}
	public float getNum() {
		return num;
	}
	public Userman getEnterEmployee() {
		return enterEmployee;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}
	public void setNum(float num) {
		this.num = num;
	}
	public void setEnterEmployee(Userman enterEmployee) {
		this.enterEmployee = enterEmployee;
	}
	public PicBean getPic() {
		return pic;
	}
	public void setPic(PicBean pic) {
		this.pic = pic;
	}
}
