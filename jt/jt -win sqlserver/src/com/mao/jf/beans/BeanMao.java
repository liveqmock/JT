package com.mao.jf.beans;

import java.io.Serializable;
import java.util.List;

public class BeanMao implements Serializable {
	public static BeanManager beanManager=new BeanManager();

	public void remove()  {
		removeBean(this);
	}

	public void save() {
		saveBean(this);
	}
 
	public static void removeBean(Object object)  {
		
		beanManager.removeBean(object); 
	}
	public static void saveBean(Object bean) {

		beanManager.saveBean(bean);
		
	}
	
	
	
	public static <T> List<T> getBeans(Class<T> cls)  {
		return beanManager.getBeans(cls);
	}
	public static <T> List<T> getBeans(Class<T> cls,String sql){
		return beanManager.getBeans(cls, sql);
	}
	public static <T> List<T> getBeans(Class<T> cls,String sql,Object... parms ){
		return beanManager.getBeans(cls, sql,parms);
	}
	public static <T> T getBean(Class<T> cls,String sql) {
		return beanManager.getBean(cls, sql);
	}
	public static <T> T getBean(Class<T> cls,String sql,Object... objects) {
		return beanManager.getBean(cls, sql,objects);
	}


	
	
}
