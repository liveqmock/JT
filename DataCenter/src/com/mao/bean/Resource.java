package com.mao.bean;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mao.annotations.Caption;
import com.mao.annotations.Hidden;
@Entity
@Table(schema = "dataCenter")
public class Resource implements Comparable<Resource> {
	@Hidden
	@Id
	@GeneratedValue(strategy = AUTO)
	private  int id;


	@Caption("资源名")
	@Column(name="resource_Name")
	private String resourceName;

	@Caption("上层")
	@OneToOne
	@JoinColumn(name = "parent", referencedColumnName = "id")
	private Resource parentRe;

	@Caption("操作界面")
	private String resourcePnl;

	@Caption("状态")
	private int status;
	
	@Caption("顺序")
	private int iorder;

	@Caption("备注")
	private String remark;

	@Transient
	@Hidden
	private MenuBean menuBean;
	public MenuBean getMenuBean() {
		if(menuBean!=null) return menuBean;
		menuBean=new MenuBean(this);
		if(parentRe!=null){
			parentRe.getMenuBean().getChilds().add(menuBean);
		}
		return menuBean;
	}
	public MenuBean getTopMenu() {
		getMenuBean();
		if(parentRe!=null)
			return parentRe.getTopMenu();
		else
			return getMenuBean();
	}
	public Resource getParentRe() {
		return parentRe;
	}

	public void setParentRe(Resource parentRe) {
		this.parentRe = parentRe;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	
	public String getResourcePnl() {
		return resourcePnl;
	}

	public void setResourcePnl(String resourcePnl) {
		this.resourcePnl = resourcePnl;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIorder() {
		return iorder;
	}

	public void setIorder(int iorder) {
		this.iorder = iorder;
	}

	@Override
	public String toString() {
		return  resourceName;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resource other = (Resource) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(Resource o) {

		if(getParentRe()==null){
			if(o.getParentRe()==null)
				return getIorder()==o.getIorder()?((Integer)id).compareTo(o.getId()): ((Integer)getIorder()).compareTo(o.getIorder());
				else {
					if(id== o.getParentRe().getId())
						return -1;
					else 
						return  getIorder()==o.getParentRe().getIorder()?((Integer)id).compareTo(o.getParentRe().getId()): ((Integer)getIorder()).compareTo(o.getParentRe().getIorder());
				}
		}else{
			if(o.getParentRe()==null){
				if(getParentRe().getId()== o.getId())
					return 1;
				else 
					return getParentRe().getIorder()==o.getIorder()?((Integer)getParentRe().getId()).compareTo(o.getId()): ((Integer)getParentRe().getIorder()).compareTo(o.getIorder());
			}else{
				if(getParentRe().getId()==o.getParentRe().getId())
					return getIorder()==o.getIorder()?((Integer)id).compareTo(o.getId()): ((Integer)getIorder()).compareTo(o.getIorder());
				else
					return getParentRe().compareTo(o.getParentRe());
			}
		}
	}




}
