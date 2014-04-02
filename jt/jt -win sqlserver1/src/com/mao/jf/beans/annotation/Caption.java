package com.mao.jf.beans.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ���ó�Ա��ʾ���⣬�ڱ�������ʾʱ�����Ա����
 * @author ë�ҷ�
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@Documented
public @interface Caption {
	int order() default 0;
    String value();
}