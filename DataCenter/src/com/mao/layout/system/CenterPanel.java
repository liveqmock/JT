package com.mao.layout.system;

import java.sql.SQLException;

import javax.naming.NamingException;

import com.mao.bean.User;
import com.mao.layout.resource.SystemResource;
import com.mao.layout.resource.SystemRole;
import com.mao.layout.resource.SystemRoleAuth;
import com.mao.layout.resource.SystemUser;
import com.mao.layout.resource.SystemUserAuth;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class CenterPanel extends VerticalLayout{

	public void createMenu(User user) {
		setSizeFull();
		TabSheet sheet=new TabSheet();
		sheet.setSizeFull();
		
		try {
			sheet.addTab(new SystemResource());
			sheet.addTab(new SystemRole());
			sheet.addTab(new SystemRoleAuth());
			sheet.addTab(new SystemUser());
			sheet.addTab(new SystemUserAuth());
			addComponent(sheet);
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
