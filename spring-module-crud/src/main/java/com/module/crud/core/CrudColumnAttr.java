package com.module.crud.core;

import com.module.crud.annotation.Column;
import com.module.crud.dao.CrudDao;
import com.module.crud.enumerate.*;
import com.module.crud.utils.ClassUtils;
import com.module.crud.utils.StringUtils;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Field;
import java.util.Objects;

public class CrudColumnAttr {

    public CrudColumnAttr(Column column) {
        this.column = column.column();
        this.isPrimary = column.isPrimary();
        this.PrimaryRule = column.PrimaryRule();
        this.query = column.query();
        this.property = column.property();
        this.sort = column.sort();
        this.jdbcType = column.jdbcType();
        this.javaType = column.javaType();
        this.primaryType = column.primaryType();
        this.queryModel = column.queryModel();
        this.weight = 1;
    }

    public CrudColumnAttr(Column column, Field field) {
        this(column);
        this.column = StringUtils.isBlank(column.column()) ? field.getName() : column.column();
        this.property = StringUtils.isBlank(column.property()) ? field.getName() : column.property();
        this.javaType = column.javaType().equals(void.class) ? field.getType() : column.javaType();
        this.weight = 3;
    }

    public CrudColumnAttr(Field field) {
        this.column = field.getName();
        this.property = field.getName();
        this.javaType = field.getType();
        this.weight = 0;
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

    public void merge(CrudColumnAttr column) {
        this.column = column.column;
        this.isPrimary = column.isPrimary;
        this.PrimaryRule = column.PrimaryRule;
        this.query = column.query;
        this.property = column.property;
        this.sort = column.sort;
        this.jdbcType = column.jdbcType;
        this.javaType = column.javaType;
        this.primaryType = column.primaryType;
        this.queryModel = column.queryModel;
        this.weight = column.weight;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public Object getValue() {
        return ClassUtils.getValue(targetObject, property);
    }

    public void setValue(Object value) {
        ClassUtils.setValue(targetObject, property, value, javaType);
    }

    public boolean isValueNull(){
        Object value = getValue();
        if(Objects.nonNull(value)){
            if(value instanceof String){
                return StringUtils.isNotBlank(value.toString());
            }
            return true;
        }
        return false;
    }

    public boolean isValueNotNull(){
        return !isValueNull();
    }

    public String getParamProperty(){
        return String.format("#{%s.%s}", CrudDao.__JavaAlias, property);
    }
    public String getSelectColumn(){
        return String.format("#{%s.%s}", CrudDao.__JavaAlias, property);
    }

    public void createPrimary() {
        setValue(ClassUtils.GeneratePrimary(this));
    }


}
