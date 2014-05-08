package com.mao.layout.system;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;

import com.mao.customLayout.bean.DbSearch;
import com.mao.tool.Datasource;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;


public class CreateSearchXmlPnl extends VerticalLayout {
	private TextArea sqlArea=new TextArea("Sqlִ�����:");
	private Button exeButton=new Button("ִ��");
	private TextArea fromArea=new TextArea("FROM:");
	private TextField whereArea=new TextField("WHERE:");
	private TextField groupArea =new TextField("GROUP:");
	private TextField orderArea=new TextField("ORDER:");
	private TextField fileName=new TextField("�ļ���:");
	private TextArea xmlArea=new TextArea("���嵥");
	private DbSearch dbSearch=new DbSearch();
	private FieldGroup fieldGroup=new FieldGroup();
	public CreateSearchXmlPnl() {
		super();
		setSizeFull();
		sqlArea.setHeight("100px");
		fromArea.setHeight("50px");
		Button saveButton=new Button("����");
		sqlArea.setWidth("100%"); 
		fromArea.setWidth("100%");  
		whereArea.setWidth("100%"); 
		groupArea.setWidth("100%"); 
		orderArea.setWidth("100%"); 
		fileName.setWidth("100%");  
		xmlArea.setSizeFull();
		addComponent(sqlArea);
		addComponent(fromArea);
		addComponent(whereArea);
		addComponent(groupArea);
		addComponent(orderArea);
		addComponent(fileName);
		addComponent(exeButton);
		addComponent(xmlArea);
		addComponent(saveButton);
		setExpandRatio(xmlArea, 10.f);
		setComponentAlignment(exeButton, Alignment.MIDDLE_CENTER);
		setComponentAlignment(saveButton, Alignment.MIDDLE_CENTER);
		BeanItem<DbSearch> beanItem = new BeanItem<DbSearch>(dbSearch);
		fieldGroup.setItemDataSource(beanItem);
		fieldGroup.bind(fromArea, "fromString");
		fieldGroup.bind(whereArea, "whereSql");
		fieldGroup.bind(groupArea, "groupSql");
		fieldGroup.bind(orderArea, "orderSql");
		sqlArea.setRequired(true);
		exeButton.addClickListener(new ClickListener() {
			

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					fieldGroup.commit();
					try(
					Connection conn = Datasource.getDataSource("core").getConnection();
					Statement st = conn.createStatement();	
							){
						ResultSet rs = st.executeQuery(sqlArea.getValue());
						dbSearch.getColumns(rs.getMetaData());
						xmlArea.setValue(dbSearch.saveAsXml());
					} catch (Exception e) {
						Notification.show("����",e.getMessage(),Notification.Type.ERROR_MESSAGE);
						
					}
				} catch (CommitException e) {
					Notification.show("����",e.getMessage(),Notification.Type.ERROR_MESSAGE);
					
				}
				
				
			}
		});
		saveButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(StringUtils.isBlank(xmlArea.getValue())){
					Notification.show("��������XML");
					return;
				}
				try {
					FileWriter writer=new FileWriter(VaadinSession.getCurrent().getService().getBaseDirectory().getCanonicalPath()+"/ds/"+fileName.getValue()+".xml");
					writer.append(xmlArea.getValue());
					writer.flush();
					writer.close();
				
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				
				
				
			}
		});
	}
	
}
