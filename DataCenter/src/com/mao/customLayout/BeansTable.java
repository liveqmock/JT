package com.mao.customLayout;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.mao.annotations.Caption;
import com.mao.annotations.Hidden;
import com.mao.annotations.Password;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class BeansTable<T> extends Table {
	private SimpleDateFormat dateDf= new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat timeDf= new SimpleDateFormat("HH:mm:ss");
	protected Collection<T> beans;
	protected BeanItemContainer<T> container;
	protected boolean showSuper;
	public BeansTable(Class<T> class1, Collection<T> beans,boolean showSuper) {
		super();
		this.beans = beans;
		this.showSuper=showSuper;
		initTable(class1);
	}
	public BeansTable(Class<T> class1, Collection<T> beans) {
		this(class1, beans, true);
	}
	public void initTable(Class<T> class1) {
		container=new BeanItemContainer<T>(class1,beans);
		setContainerDataSource(container);
		ArrayList<String> headerList=new ArrayList<String>();
		headerList=getHeader(class1,headerList);		
		setVisibleColumns(headerList.toArray());
		addStyleName("small striped");

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
		if(showSuper&& class1.getSuperclass()!=Object.class){
			 getHeader(class1.getSuperclass(),headerList);			
		}
		for(Field fld: class1.getDeclaredFields()){
			if(fld.getAnnotation(Hidden.class)==null){
				Caption captionAn =fld.getAnnotation(Caption.class);
				if(captionAn!=null&&fld.getAnnotation(Password.class)==null){
					setColumnHeader(fld.getName(), captionAn.value());
					headerList.add(fld.getName());

				}
			}
		}
		return headerList;
	}
	public Collection<T> getBeans() {
		return beans;
	}
	public void setBeans(Collection<T> beans) {
		this.beans = beans;
		container.removeAllItems();
		container.addAll(beans);
	}
	public  BeanItemContainer<T> getDs() {
		// TODO 自动生成的方法存根
		return container;
	}
}
