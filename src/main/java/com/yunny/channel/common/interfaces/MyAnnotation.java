package com.yunny.channel.common.interfaces;

import java.lang.annotation.*;

/**
 * 自定义注解
 */
//Target 注解作用域的定义
@Target(value = {ElementType.METHOD,ElementType.TYPE}) //METHOD类型是定义在方法上的注解,TYPE 可放在类上
//运行级别定义，什么时候有效
@Retention(value = RetentionPolicy.SOURCE)// SOURCE 源码级别，RUNTIME运行时有效，runtime>class>source
//@Documented表示注解是否生在在JAVADOC文档中
@Documented
//@Inherited 表示子类可以继承父类的注解
@Inherited
public @interface MyAnnotation {

    //注解参数: 类型 名称()
    int value();
    String name();
}
