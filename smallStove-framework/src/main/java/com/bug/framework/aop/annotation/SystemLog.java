package com.bug.framework.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统日志注解类, 用于标识aop的切点
 */
// 此注解存活到什么阶段: 运行时
@Retention(RetentionPolicy.RUNTIME)
// 此注解作用在方法上
@Target(ElementType.METHOD)
public @interface SystemLog {

    String business();
}
