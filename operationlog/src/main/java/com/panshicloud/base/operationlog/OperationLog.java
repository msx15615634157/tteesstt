package com.panshicloud.base.operationlog;

import java.lang.annotation.*;

/**
 * <p>
 * 打印数据日志，可用于监测各方法
 * </p>
 *
 * @author huangrankun
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {

    String value() default "";

    /**
     * 类型
     */
    String type() default "";

    /**
     * 操作
     */
    String operation() default "";

}