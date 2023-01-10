package com.module.crud.core;

import com.module.crud.annotation.Column;
import com.module.crud.enumerate.*;
import com.module.crud.utils.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Field;

public class CrudColumnAttr {

    private CrudColumnAttr(Column column, Object targetObject) {
        this.targetObject = targetObject;
        this.column = column.column();
        this.isPrimary = column.isPrimary();
        this.PrimaryRule = column.PrimaryRule();
        this.query = column.query();
        this.property = column.property();
        this.sort = column.sort();
        this.jdbcType = column.jdbcType();
        this.javaType = column.javaType();
        this.weight = column.weight();
        this.primaryType = column.primaryType();
        this.queryModel = column.queryModel();
    }

    public CrudColumnAttr(Column column, Field field, Object targetObject) {
        this(column,targetObject);
        this.column = StringUtils.isBlank(column.column()) ? field.getName() : column.column();
        this.property = StringUtils.isBlank(column.property()) ? field.getName() : column.property();
        this.javaType = column.javaType().equals(void.class) ? field.getType() : column.javaType();
    }

    private Object targetObject;

    public String column;
    public String property;
    public boolean isPrimary;
    public PrimaryType primaryType;
    public PrimaryGenerate PrimaryRule;
    public QueryType query;
    public QueryModel queryModel;
    public ColumnSort sort;
    public JdbcType jdbcType;
    public Class<?> javaType;
    public int weight = 0;

    public Object getValue(){
        return ClassUtils.getValue(targetObject, property);
    }
    public void setValue(Object value){
        ClassUtils.setValue(targetObject, property, value, javaType);
    }

    public void GeneratePrimary(){
        setValue(ClassUtils.GeneratePrimary(this));
    }


}
