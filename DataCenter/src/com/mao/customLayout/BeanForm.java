package com.mao.customLayout;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.mao.annotations.Caption;
import com.mao.annotations.Hidden;
import com.mao.annotations.Password;
import com.mao.tool.BeanManager;
import com.mao.tool.MaoLogger;
import com.sun.istack.internal.NotNull;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.BindException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class BeanForm<T> extends GridLayout{
	private T bean;
	private BeanItem<T> beanItem;
	private FieldGroup fieldGroup=new FieldGroup();

	public BeanForm(T bean) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		super();
		setSpacing(true);
		setMargin(true);
		setColumns(6);

		setBean(bean);

	}
	public void addComponent(String caption,Component component) {
		Label label=new Label(caption+":");
		addComponent(label);
		setComponentAlignment(label, Alignment.MIDDLE_RIGHT);
		addComponent(component);
	}

	public void autoBinder() throws NoSuchFieldException, SecurityException, BindException {
		for(java.lang.reflect.Field fld:bean.getClass().getDeclaredFields()){
			if(fld.getAnnotation(Hidden.class)==null){

				Caption captionAn =fld.getAnnotation(Caption.class);
				Field<?> fieldComponent =fieldGroup.getField(fld.getName());
				if(fieldComponent==null&&fld.getAnnotation(Password.class)!=null){
					fieldComponent=new PasswordField();
					fieldGroup.bind(fieldComponent,fld.getName());
				}else if(fieldComponent==null){
					fieldComponent = fieldGroup.buildAndBind(null,fld.getName());
				}

				if(captionAn!=null)
					addComponent(captionAn.value(),fieldComponent);
				else
					addComponent(fld.getName(),fieldComponent);
				if(fld.getAnnotation(NotNull.class)!=null)
					fieldComponent.setRequired(true);
				if(fieldComponent instanceof TextField)
					((TextField)fieldComponent).setNullRepresentation("");
			}
		}

	}

	public  void binder(Field<?> field, String propertyId) {
		fieldGroup.bind(field, propertyId);
	}

	public T getBean() {

		return this.bean;
	}
	@SuppressWarnings("unchecked")
	public void setBean(T bean) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		this.bean = bean;
		beanItem = new BeanItem<T>(bean);
		fieldGroup.setItemDataSource(beanItem);

	}
	public void commit() throws CommitException {
		try {
			fieldGroup.commit();

		} catch (CommitException e) {
			Notification.show("表单中有不符要求的值",Notification.Type.ERROR_MESSAGE);
			throw e;
		}

	}
	public void cancel() {
		fieldGroup.discard();

	}
	public FieldGroup getFieldGroup() {
		return fieldGroup;
	}
	public void setFieldGroup(FieldGroup fieldGroup) {
		this.fieldGroup = fieldGroup;
	}
	public void saveBean() throws Exception {
		commit();
		BeanManager.BM.saveBean(bean);
	}
}
