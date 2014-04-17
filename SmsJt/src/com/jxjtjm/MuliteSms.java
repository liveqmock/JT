package com.jxjtjm;

import java.util.Collection;

import com.jxjtjm.beans.BeanManager;
import com.jxjtjm.beans.Contact;
import com.jxjtjm.beans.LoginUser;
import com.jxjtjm.beans.Sms;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;

public class MuliteSms extends FormLayout {
	private FieldGroup fieldGroup;
	private OptionGroup optionGroup;
	private TextArea smsContentField;
	public MuliteSms() {

		setCaption("���˷���");
		final BeanItemContainer<Contact> container =
				new BeanItemContainer<Contact>(Contact.class);
		container.addAll(BeanManager.BM.getBeans(Contact.class));
		optionGroup=new OptionGroup();
		optionGroup.setNullSelectionAllowed(false);
		optionGroup.setContainerDataSource(container);
		optionGroup.setMultiSelect(true);
		Panel panel=new Panel("������Ҫ���ܶ��ŵ���");
		panel.setContent(optionGroup);		
		panel.setHeight("200px");
		panel.setWidth("200px");
		Button addSign=new Button("��ӹ�˾ǩ��");
		Button allSelectBt = new Button("ȫѡ");
		Button allNoSelectBt = new Button("ȫ��ѡ");
		smsContentField=new TextArea("��������");
		smsContentField.setWidth("400px");
		smsContentField.setNullRepresentation("");
		final Button bt=new Button("����");
		HorizontalLayout horizontalLayout=new HorizontalLayout();
		horizontalLayout.addComponent(allSelectBt);
		horizontalLayout.addComponent(allNoSelectBt);
		Sms sms = new Sms();
		BeanItem<Sms> item=new BeanItem<Sms>(sms);
		fieldGroup=new FieldGroup(item);
		fieldGroup.bind(smsContentField, "content");

		Button refreshButton=new Button("ˢ����ϵ��");
		horizontalLayout.addComponent(refreshButton);
		addComponent(smsContentField);
		addComponent(addSign);
		addComponent(panel);
		addComponent(horizontalLayout);
		addComponent(bt);
		smsContentField.setRequired(true);
		refreshButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				container.removeAllItems();
				container.addAll(BeanManager.BM.getBeans(Contact.class));

			}
		});
		addSign.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				smsContentField.setValue(smsContentField.getValue()+"\n�����˽��ﾫ�ܻ�е�������޹�˾��");

			}
		});
		allSelectBt.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				for( Object item: optionGroup.getItemIds())
					optionGroup.select(item);
			}
		});
		allNoSelectBt.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				for( Object item: optionGroup.getItemIds())
					optionGroup.setValue(null);
			}
		});
		bt.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if(fieldGroup.isValid()){
					bt.setEnabled(false);
					Collection<Contact> contacts=  (Collection<Contact>) optionGroup.getValue();
					for(Contact contact:contacts){
						try{

							Sms sms1 = new Sms();
							sms1.setSender((LoginUser) getSession().getAttribute("loginUser"));
							sms1.setContent(smsContentField.getValue());
							sms1.setReceiveName(contact.getName());
							sms1.setTelNo(contact.getTelNo());
							sms1.send();
						}catch (Exception e) {
							e.printStackTrace();
						}

					}

					smsContentField.setValue("");
					Notification.show("���ͳɹ�");

					bt.setEnabled(true);
				}
			}
		});
	}

}
