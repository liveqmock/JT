package com.mao.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mao.tool.BeanManager;


@Entity
@Table(schema = "dataCenter")
public class User {

	@Id
	private String id;
	@OneToOne
	@JoinColumn(name = "gyh", referencedColumnName = "STCASTAF")
	private Employee employee;
	private String password;
	/**
	 * 0-系统管理
	 * 1-总行用户
	 * 2-支行用户
	 * 3-网点用户
	 */
	private int level;
	private Date loginDate;
	@com.mao.annotations.Hidden
	@OneToMany
	@JoinTable(name = "USER_ROLE", schema = "datacenter", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;
	private int status;
	@Transient
	private Collection<Department> contralDepartments;
	public Collection<Department> getContralDepartments() {
		if(contralDepartments==null)
			if(level<=1){
				contralDepartments= BeanManager.BM.getBeans(Department.class);
			}else if(level==2){
				contralDepartments=employee.getDepartment().getTopDepartment().getDepartments();
			}else if(level==3){
				contralDepartments=new ArrayList<>();
				contralDepartments.add(employee.getDepartment());
			}
		return contralDepartments;

	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}


	public Date getLoginDate() {
		return loginDate;
	}
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return employee.toString();
	}

}
