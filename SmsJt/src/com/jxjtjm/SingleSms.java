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
		setCaption("���˷���");
		contactBox=new ComboBox("��ϵ��");
		BeanItemContainer<Contact> container =
		        new BeanItemContainer<Contact>(Contact.class);
		container.addAll(BeanManager.BM.getBeans(Contact.class));
		Button addSign=new Button("�ӹ�˾ǩ��");
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
				smsContentField.setValue(smsContentField.getValue()+"\n�����˽��ﾫ�ܻ�е�������޹�˾��");
				
			}
		});
		telNoField=new TextField("�绰����");
		nameField=new TextField("����������");

		smsContentField=new TextArea("��������");
		smsContentField.setWidth("400px");
		final Button bt=new Button("����");

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
		telNoField.addValidator(new RegexpValidator("[1][0-9]{10}","������ȷ�ĵ绰����"));
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
						Notification.show("���ͳɹ�");
						
					} catch (CommitException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}

					bt.setEnabled(true);
			}
		});
	}

}
