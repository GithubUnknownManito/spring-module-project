package com.module.crud.annotation;

import com.module.crud.entity.ObjectEntity;
import com.module.crud.enumerate.Pattern;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface One {
    /**
     * 属性（字段）
     */
    public String property();
    /**
     * 引用住类
     */
    public Class<?> quote();
    /**
     * 别称
     */
    public String alias();
    /**
     * 列
     */
    public Column[] columns() default {};
    /**
     * 连接内容
     */
    public String on();
}
