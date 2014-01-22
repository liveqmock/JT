package com.mao.jf.beans;

import java.io.Serializable;
import java.util.List;

public class BeanMao implements Serializable {
	public static BeanManager beanManager=new BeanManager();

	public void remove()  {
		beanManager.removeBean(this); 
	}

	public void save() {
		beanManager.saveBean(this);

	}
 
	public static void removeBean(Object object)  {
		beanManager.removeBean(object); 
	}

	public static <T> List<T> loadAll(Class<T> cls)  {
		return beanManager.getBeans(cls);
	}
	public static <T> List<T> loadAll(Class<T> cls,String sql){
		return beanManager.getBeans(cls, sql);
	}
	public static <T> T load(Class<T> cls,String sql) {
		return beanManager.getBean(cls, sql);
	}

	public static void saveBean(Object bean) {
		beanManager.saveBean(bean);
		
	}
}
