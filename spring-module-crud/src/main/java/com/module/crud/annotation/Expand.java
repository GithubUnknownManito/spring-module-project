package com.module.crud.annotation;

import com.module.crud.enumerate.ExpandType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 表示内部拓展参数
 */
public @interface Expand {
    public ExpandType value();

    public String param() default "";
}
