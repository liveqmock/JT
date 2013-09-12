package com.mao.jf.beans;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
// 在使用@interface后，表明是自动继承子annotation类
public @interface ChinaAno {
	// 只有一个参数，可以直接命名为value，在使用时，可以不指定参数名而直接赋值；
	// 而且，value（）是类似方法，其实是自定义声明的一个参数，使用时是直接赋值和获 取的。类似与Bean的getter和setter方法。
	// default：定义参数的默认值
	int order();

	String str();
}