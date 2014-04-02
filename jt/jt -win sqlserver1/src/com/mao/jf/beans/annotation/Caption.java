package com.mao.jf.beans.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 设置成员显示标题，在表单或表格显示时代替成员名称
 * @author 毛忠方
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@Documented
public @interface Caption {
	int order() default 0;
    String value();
}