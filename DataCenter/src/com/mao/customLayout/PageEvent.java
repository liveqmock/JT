package com.mao.customLayout;

import java.util.EventObject;

public class PageEvent extends EventObject {

	private Object source;
	private int page;
	public PageEvent(Object source, int page) {
		super(source);
		this.source=source;
		this.page=page;
	}
	public void setPage(int page) {
		this.page=page;
	}
	public int getPage() {
		return page;
	}
	public Object getSource() {
		return source;
	}
}