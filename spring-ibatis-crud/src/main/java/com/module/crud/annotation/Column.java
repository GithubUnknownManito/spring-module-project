package com.module.crud.annotation;

import com.module.crud.entity.ObjectEntity;
import com.module.crud.enumerate.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 列，用于标识
 * * 权重<br/>
 * *      * <b>注：</b> 数值越高权重越重，高权重覆低权重<br/>
 * *      * 默认权重为 0 <br/>
 * *      * <b>ANNOTATION</b> 模式下 表注解 Column -> 字段注解 <br/>
 * *      * <b>FIELD</b>      模式下 无权重仅使用 字段进行生成 <br/>
 * *      * <b>BLEND</b>      模式下 字段 -> 表注解 Column -> 字段注解 <br/>
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
    /**
     * 字段名
     */
    @AliasFor(attribute = "value")
    public String column() default "";

    /**
     * 字段名
     */
    @AliasFor("column")
    public String value() default "";

    /**
     * 是否为主鍵
     */
    public boolean isPrimary() default false;

    /**
     * 是否必填，仅添加时验证
     */
    public boolean isRequired() default false;

    /**
     * 主键类型
     */
    public PrimaryType primaryType() default PrimaryType.PRIMARY_KEY;

    /**
     * 主键为空生成规则
     */
    public PrimaryGenerate PrimaryRule() default PrimaryGenerate.UNSET;

    /**
     * 查询方式
     */
    public QueryType query() default QueryType.UNSET;

    /**
     * 查询关联形式
     *
     * @return
     */
    public QueryModel queryModel() default QueryModel.AND;

    /**
     * 属性名
     */
    public String property() default "";

    /**
     * 排序
     */
    public ColumnSort sort() default ColumnSort.UNSET;

    /**
     * 字段类型
     */
    public JdbcType jdbcType() default JdbcType.UNDEFINED;

    /**
     * 属性类型
     */
    public Class<?> javaType() default void.class;

    /**
     * 继承
     */
    public Class<?> inheritance() default void.class;

}
