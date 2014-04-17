package com.jxjtjm;

import com.jxjtjm.beans.BeanManager;
import com.jxjtjm.beans.Contact;
import com.jxjtjm.beans.LoginUser;
import com.jxjtjm.beans.Sms;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class SingleSms extends FormLayout {
	private TextField telNoField;
	private TextField nameField;
	private ComboBox contactBox;
	private Sms sms;
	private FieldGroup fieldGroup;
	private TextArea smsContentField;
	public SingleSms() {
		setCaption("单人发送");
		contactBox=new ComboBox("联系人");
		BeanItemContainer<Contact> container =
		        new BeanItemContainer<Contact>(Contact.class);
		container.addAll(BeanManager.BM.getBeans(Contact.class));
		Button addSign=new Button("加公司签名");
		contactBox.setContainerDataSource(container);
		contactBox.setImmediate(true);
		contactBox.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Contact contact=	(Contact) contactBox.getValue();
				if(contact!=null){
					telNoField.setValue(contact.getTelNo());
					nameField.setValue(contact.getName());
				}

			}
		});
		addSign.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				smsContentField.setValue(smsContentField.getValue()+"\n【嘉兴津田精密机械制造有限公司】");
				
			}
		});
		telNoField=new TextField("电话号码");
		nameField=new TextField("接受人姓名");

		smsContentField=new TextArea("短信内容");
		smsContentField.setWidth("400px");
		final Button bt=new Button("发送");

		sms=new Sms();
		BeanItem<Sms> item=new BeanItem<Sms>(sms);
		fieldGroup=new FieldGroup(item);
		fieldGroup.bind(nameField, "receiveName");
		fieldGroup.bind(telNoField, "telNo");
		fieldGroup.bind(smsContentField, "content");

		telNoField.setNullRepresentation("");
		nameField.setNullRepresentation("");
		smsContentField.setNullRepresentation("");
		addComponent(contactBox);
		addComponent(telNoField);
		addComponent(nameField);
		addComponent(smsContentField);
		addComponent(addSign);
		addComponent(bt);
		telNoField.addValidator(new RegexpValidator("[1][0-9]{10}","不是正确的电话号码"));
		telNoField.setRequired(true);
		smsContentField.setRequired(true);
		bt.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if(fieldGroup.isValid())
					bt.setEnabled(false);
					try {
						fieldGroup.commit();
						sms.setSender((LoginUser) getSession().getAttribute("loginUser"));
						sms.send();
						smsContentField.setValue("");
						Notification.show("发送成功");
						
					} catch (CommitException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}

					bt.setEnabled(true);
			}
		});
	}

}
