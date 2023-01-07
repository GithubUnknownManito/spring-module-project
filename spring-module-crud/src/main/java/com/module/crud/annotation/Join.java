package com.module.crud.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Join {
    /**
     * 表名
     */
    public String name();

    /**
     * 别称
     */
    public String alias();

    /**
     * 关联SQL
     */
    public String on();

    /**
     * 列
     */
    public Column[] column() default {};
}
