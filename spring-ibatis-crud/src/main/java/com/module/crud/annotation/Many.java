package com.module.crud.annotation;

import com.module.crud.entity.ObjectEntity;
import com.module.crud.enumerate.Pattern;

import java.lang.annotation.*;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Many {
    /**
     * 引用住类
     */
    public Class<?> quote();
    /**
     * 属性（字段）
     */
    public String property();
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
    public Column[] columns() default {};
}
