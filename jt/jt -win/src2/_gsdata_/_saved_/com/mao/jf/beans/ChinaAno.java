package com.mao.jf.beans;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
// ��ʹ��@interface�󣬱������Զ��̳���annotation��
public @interface ChinaAno {
	// ֻ��һ������������ֱ������Ϊvalue����ʹ��ʱ�����Բ�ָ����������ֱ�Ӹ�ֵ��
	// ���ң�value���������Ʒ�������ʵ���Զ���������һ��������ʹ��ʱ��ֱ�Ӹ�ֵ�ͻ� ȡ�ġ�������Bean��getter��setter������
	// default�����������Ĭ��ֵ
	int order();

	String str();
}