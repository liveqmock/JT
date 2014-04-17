package com.mao.jf.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mao.jf.beans.annotation.Caption;

@Entity
public class BackRepair extends BeanMao {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "billid", referencedColumnName = "id")
	private PicBean pic ;
	@Caption("返修数据")
	private long backNum ;
	@Caption("返修日期")
	private Date backDate;
	@Caption("返修交货日期")
	private Date getDate ;
	@Caption("返修原因")
	private String note ;
	
	
	
	public Date getBackDate() {
		return backDate;
	}
	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}
	public long getBackNum() {
		return backNum;
	}
	public void setBackNum(long backNum) {
		this.backNum = backNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Date getGetDate() {
		return getDate;
	}
	public void setGetDate(Date getDate) {
		this.getDate = getDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public PicBean getPic() {
		return pic;
	}
	public void setPic(PicBean pic) {
		this.pic = pic;
	}

	
}
