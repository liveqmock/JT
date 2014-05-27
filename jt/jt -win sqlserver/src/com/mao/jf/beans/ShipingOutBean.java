package com.mao.jf.beans;

import static javax.persistence.GenerationType.AUTO;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class ShipingOutBean {
	@Id
	@GeneratedValue(strategy = AUTO)
	private int id;
	@ManyToOne
	@JoinColumn(name = "pic", referencedColumnName = "id")
	private PicBean pic;
	
	@Caption("送货单号")
	private String shipingNo;
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
	public PicBean getPic() {
		return pic;
	}
	public void setPic(PicBean pic) {
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

	public String getShipingNo() {
		return shipingNo;
	}
	public void setShipingNo(String shipingNo) {
		this.shipingNo = shipingNo;
	}
	@PostPersist
	public void refreshPic(){
		BeanMao.beanManager.refresh(pic);
	}
}
