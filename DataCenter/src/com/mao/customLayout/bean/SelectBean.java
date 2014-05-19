package com.mao.customLayout.bean;

public class SelectBean<T> {
	private String caption;
	private T value;
	
	
	public SelectBean(T value,String caption ) {
		super();
		this.caption = caption;
		this.value = value;
	}
	public String getCaption() {
		return caption==null?value.toString():caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
}
