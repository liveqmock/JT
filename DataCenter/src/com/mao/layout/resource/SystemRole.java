package com.mao.layout.resource;

import java.sql.SQLException;

import javax.naming.NamingException;

import com.mao.bean.Role;
import com.mao.customLayout.BeansEditPanel;
import com.mao.tool.BeanManager;
import com.vaadin.ui.ComboBox;


public class SystemRole  extends BeansEditPanel<Role>{
	public SystemRole() throws ClassNotFoundException, SQLException, NamingException, Exception {
		super(Role.class,BeanManager.BM.getBeans( Role.class),new Role());
		ComboBox statusSelect = new ComboBox();
		statusSelect.addItem(0);
		statusSelect.addItem(1);
		statusSelect.setItemCaption(0, "∆Ù”√");
		statusSelect.setItemCaption(1, "Õ£”√");
		getForm().binder(statusSelect, "status");
		getForm().autoBinder();
	}
	
}
