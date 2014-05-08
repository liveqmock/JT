package com.mao.bean;

import static javax.persistence.GenerationType.AUTO;

import java.util.Collection;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.mao.annotations.Caption;
import com.mao.annotations.Hidden;

@Table(schema = "dataCenter")
@Entity
public class Role {
	@Hidden
	@Id
	@GeneratedValue(strategy = AUTO)
	private int id;

	@Column(name="role_Name")
	@Caption("Ãû³Æ")
	private String roleName;
	@Caption("±¸×¢")
	private String remark;
	@Caption("×´Ì¬")
	private int status;

	@Hidden
	@ManyToMany
	@JoinTable(name = "ROLE_RESOURCE", catalog = "datacenter", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))

	private Collection<Resource> resources;
	
	public TreeSet<Object> getMenuBeans() {
		TreeSet<Object> menuBeans = new TreeSet<>();
		for(Resource resource:resources)
			menuBeans.add(resource.getTopMenu());
		return menuBeans;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return  roleName ;
	}
	public Collection<Resource> getResources() {
		return resources;

	}
	public void setResources(Collection<Resource> resources) {
		this.resources = resources;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Role other = (Role) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
}
