package com.jxjtjm;

import java.util.Collection;

import com.jxjtjm.beanPanels.BeansEditPanel;
import com.jxjtjm.beans.BeanManager;
import com.jxjtjm.beans.Contact;
import com.sun.org.apache.bcel.internal.generic.Select;
import com.vaadin.data.fieldgroup.FieldGroup.BindException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;

public class ManagerContact extends BeansEditPanel<Contact> {

	public ManagerContact() {
		super(Contact.class, BeanManager.BM.getBeans(Contact.class), new Contact());
		setCaption("管理通讯簿");
		try {
			getForm().autoBinder();
		} catch (NoSuchFieldException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (BindException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
