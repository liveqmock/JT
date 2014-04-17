package com.jxjtjm.beans;

import static javax.persistence.GenerationType.AUTO;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.jxjtjm.beanPanels.Caption;
import com.jxjtjm.smsSystem.SmsSystem;
import com.vaadin.ui.Notification;

@Entity
public class Sms {
	@Id
	@GeneratedValue(strategy = AUTO)
	private int id;
	@Caption("�ֻ���")
	private String telNo;
	@Caption("��ϵ��")
	private String receiveName;
	@Caption("��������")
	private String content;
	@OneToOne
	@JoinColumn(name = "sender_id", referencedColumnName = "id")
	@Caption("������")
	private LoginUser sender;
	@Caption("����ʱ��")
	private Date sendDate;
	private int isSend;
	@Transient
	@Caption("����")
	private String sendStr;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LoginUser getSender() {
		return sender;
	}
	public void setSender(LoginUser sender) {
		this.sender = sender;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public int getIsSend() {
		return isSend;
	}
	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}
	public String getSendStr() {
		return isSend==0?"����":"����";
	}
	public void send() {

		try{
			setSendDate(new Date());
			SmsSystem.system.sendSms(telNo, content);
			BeanManager.BM.saveBean(this);
			Contact contact= BeanManager.BM.getBean(Contact.class, " Name=?1 and telNo=?2",receiveName,telNo);
			if(contact==null&&StringUtils.isNotBlank(receiveName)){
				
				contact=new Contact();
				contact.setName(receiveName);
				contact.setTelNo(telNo);
				BeanManager.BM.saveBean(contact);
			}
		}catch(Exception e){
			Notification.show("����ʧ�ܣ�"+e.getLocalizedMessage());
		}
		
	}
	
}
