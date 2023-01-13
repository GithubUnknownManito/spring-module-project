package com.module.crud.annotation;

import com.module.crud.enumerate.JoinType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Join {
    /**
     * 引用住类
     */
    public Class<?> quote();

    /**
     * 别称
     */
    public String alias();

    /**
     * 关联SQL
     */
    public String on();

    /**
     * 关联方式
     */
//    public JoinType joinType() default JoinType.LEFT;

    /**
     * 列
     */
    public Column[] columns() default {};
}
