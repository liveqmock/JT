package com.mao.layout.resource;

import java.sql.SQLException;

import javax.naming.NamingException;

import com.mao.bean.User;
import com.mao.customLayout.BeansEditPanel;
import com.mao.tool.BeanManager;
import com.vaadin.ui.ComboBox;


public class SystemUser extends BeansEditPanel<User>{

	public SystemUser() throws ClassNotFoundException, SQLException, NamingException, Exception {
		super(User.class,BeanManager.BM.getBeans(User.class),new User());
		ComboBox statusSelect = new ComboBox();
		statusSelect.addItem(0);
		statusSelect.addItem(1);
		statusSelect.setItemCaption(0, "∆Ù”√");
		statusSelect.setItemCaption(1, "Õ£”√");
		getForm().binder(statusSelect, "status");
		getForm().autoBinder();
	}

	
}
