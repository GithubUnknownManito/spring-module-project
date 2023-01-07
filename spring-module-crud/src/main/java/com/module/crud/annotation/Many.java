package com.module.crud.annotation;

import com.module.crud.entity.ObjectEntity;
import com.module.crud.enumerate.Pattern;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Many {
    /**
     * 引用住类
     */
    public Class<?> quote();
    /**
     * 别称
     */
    public String alias() default "";

    /**
     * 列
     */
    public Column[] column() default {};
}
