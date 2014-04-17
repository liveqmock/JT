package com.jxjtjm;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jxjtjm.beanPanels.BeansTable;
import com.jxjtjm.beans.BeanManager;
import com.jxjtjm.beans.Sms;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class SmsHistory extends VerticalLayout{

	private BeansTable<Sms> table;

	public SmsHistory() {
		HorizontalLayout formLayout=new HorizontalLayout();
		final DateField startDateField=new DateField("最早时间");
		final DateField endDateField=new DateField("最晚时间");
		final TextField nameField=new TextField("联系人");
		final TextField telField=new TextField("电话号码");
		final TextField contentField=new TextField("发送的内容");
		nameField.setNullRepresentation("");
		telField.setNullRepresentation("");
		contentField.setNullRepresentation("");
		startDateField.setRequired(true);
		endDateField.setRequired(true);
		startDateField.setResolution( Resolution.MINUTE);
		endDateField.setResolution( Resolution.MINUTE);
		startDateField.setDateFormat("yyyy-MM-dd HH:mm");
		endDateField.setDateFormat("yyyy-MM-dd HH:mm");
		
		final FieldGroup fieldGroup=new FieldGroup();
		fieldGroup.bind( startDateField,"s");
		fieldGroup.bind( endDateField,"e");
		Button button=new Button("查询");
		formLayout.addComponent(nameField);
		formLayout.addComponent(telField);
		formLayout.addComponent(contentField);
		formLayout.addComponent(startDateField);
		formLayout.addComponent(endDateField);
		formLayout.addComponent(button);
		formLayout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
		button.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(fieldGroup.isValid()){
					String name="%%";
					if(nameField.getValue()!=null)
					  name="%"+nameField.getValue()+"%";
					String telNo="%%";
					if(telField.getValue()!=null)
						telNo="%"+telField.getValue()+"%";
					String content="%%";
					if(contentField.getValue()!=null)
						content="%"+contentField.getValue()+"%";
					List<Sms> beans = (List<Sms>) BeanManager.BM.queryNativeList( 
							"select * from sms where receiveName like ?1 and telNo like ?2 and sendDate between ?3 and ?4  and content like ?5 order by sendDate desc",Sms.class,name,telNo,startDateField.getValue(),endDateField.getValue(),content);
					table.setBeans(beans);
				}else{
					Notification.show("发送日期必须输入", Notification.Type.ERROR_MESSAGE);
				}
				
			}
		});
		table=new BeansTable<>(Sms.class, null);
		table.setSizeFull();
		table.setSelectable(true);
		table.setMultiSelect(true);
		addComponent(formLayout);
		addComponent(table);
		setExpandRatio(table, 1.0f);
		setCaption("发送历史查看");
		setSizeFull();
	}

}
