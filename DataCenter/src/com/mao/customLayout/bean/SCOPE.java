package com.mao.customLayout.bean;

public enum SCOPE {
	EQUAL("����"),GT("����"),LT("С��"),GE("���ڵ���"),LE("С�ڵ���"),NEQUAL("������"),CONTAIN("����"),NCONTAIN("������"),BETWEEN("����");
	
	private final String scope;
	SCOPE(String scope) {
		this.scope=scope;
	}
	@Override
	public String toString() {
		// TODO �Զ����ɵķ������
		return scope;
	}
	
}
