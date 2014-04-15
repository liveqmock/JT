package com.mao.jf.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.PostPersist;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class ShipingBean {
	@Id
	@GeneratedValue(strategy = AUTO)
	private int id;
	@ManyToOne
	@JoinColumn(name = "pic", referencedColumnName = "id")
	private Bill pic;
	@Caption("发货日期")
	private Date shipingDate;
	@Caption("发货数量")
	private int num;
	@Caption("备注")
	private String note;
	@OneToOne
	@JoinColumn(name = "createUser", referencedColumnName = "id")
	private Userman createUser;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Bill getPic() {
		return pic;
	}
	public void setPic(Bill pic) {
		this.pic = pic;
	}
	public Date getShipingDate() {
		return shipingDate;
	}
	public void setShipingDate(Date shipingDate) {
		this.shipingDate = shipingDate;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Userman getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Userman createUser) {
		this.createUser = createUser;
	}

	@PostPersist
	public void refreshPic(){
		BeanMao.beanManager.refresh(pic);
	}
}
