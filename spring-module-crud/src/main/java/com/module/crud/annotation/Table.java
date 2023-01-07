package com.module.crud.annotation;

import com.module.crud.entity.ObjectEntity;
import com.module.crud.enumerate.Pattern;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    /**
     * 表名
     */
    @AliasFor("value")
    public String name() default "";

    /**
     * 表名
     */
    @AliasFor("name")
    public String value() default "";

    /**
     * 别称
     */
    public String alias() default "";

    /**
     * 运算模式<br/>
     * 从属关系不会使用 extends 关键字的继承<br/>
     * 仅使用 inheritance属性 进行关联
     */
    public Pattern pattern() default Pattern.ANNOTATION;

    /**
     * 列
     */
    public Column[] column() default {};

    /**
     * 关联
     */
    public Join[] join() default {};

    /**
     * 继承
     */
    public Class<?> inheritance() default void.class;

}
