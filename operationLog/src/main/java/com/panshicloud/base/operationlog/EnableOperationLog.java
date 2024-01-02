package com.panshicloud.base.operationlog;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 注入AOP切面到容器
 * </p>
 *
 * @author huangrankun
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({OperationLogAspect.class})
public @interface EnableOperationLog {

}