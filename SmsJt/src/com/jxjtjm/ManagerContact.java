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
		setCaption("����ͨѶ��");
		try {
			getForm().autoBinder();
		} catch (NoSuchFieldException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (BindException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

}
