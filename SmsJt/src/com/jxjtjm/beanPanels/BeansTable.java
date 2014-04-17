package com.jxjtjm.beanPanels;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class BeansTable<T> extends Table {
	private SimpleDateFormat dateDf= new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat timeDf= new SimpleDateFormat("HH:mm:ss");
	protected Collection<T> beans;
	protected BeanItemContainer<T> container;
	
	public BeansTable(Class<T> class1, Collection<T> beans) {
		super();
		this.beans = beans;
		initTable(class1);
	}
	public void initTable(Class<T> class1) {
		container=new BeanItemContainer<T>(class1,beans);
		setContainerDataSource(container);
		ArrayList<String> headerList=new ArrayList<String>();
		headerList=getHeader(class1,headerList);		
		setVisibleColumns(headerList.toArray());
		

	}
	


	@Override
	protected String formatPropertyValue(Object rowId, Object colId,
			Property<?> property) {
		if (property.getType() == Date.class&&property.getValue()!=null) 
            return dateDf.format((Date)property.getValue());
		else if (property.getType() == Time.class&&property.getValue()!=null) 
            return timeDf.format((Time)property.getValue());
        
		return super.formatPropertyValue(rowId, colId, property);
	}
	private ArrayList<String> getHeader(Class<?> class1,ArrayList<String> headerList){
		for(Field fld: class1.getDeclaredFields()){
			if(fld.getAnnotation(Hidden.class)==null){
				Caption captionAn =fld.getAnnotation(Caption.class);
				if(captionAn!=null&&fld.getAnnotation(Password.class)==null){
					setColumnHeader(fld.getName(), captionAn.value());
					headerList.add(fld.getName());

				}
			}
		}
		if(class1.getSuperclass()!=Object.class){
			return getHeader(class1.getSuperclass(),headerList);			
		}else
			return headerList;
	}
	public Collection<T> getBeans() {
		return beans;
	}
	public void setBeans(Collection<T> beans) {
		this.beans = beans;
		container.removeAllItems();
		container.addAll(beans);
		this.refreshRowCache();
	}
	public  BeanItemContainer<T> getDs() {
		// TODO 自动生成的方法存根
		return container;
	}
}
