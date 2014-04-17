package com.mao.customLayout.bean;

public enum SCOPE {
	EQUAL("等于"),GT("大于"),LT("小于"),GE("大于等于"),LE("小于等于"),NEQUAL("不等于"),CONTAIN("包含"),NCONTAIN("不包含"),BETWEEN("区间");
	
	private final String scope;
	SCOPE(String scope) {
		this.scope=scope;
	}
	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return scope;
	}
	
}
