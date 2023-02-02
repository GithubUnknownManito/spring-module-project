package com.module.crud.framework.core;

import com.module.crud.annotation.Column;
import com.module.crud.dao.CrudDao;
import com.module.crud.enumerate.*;
import com.module.crud.framework.utils.ClassUtils;
import com.module.crud.framework.utils.StringUtils;
import org.apache.ibatis.type.JdbcType;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.UUID;

public class CrudProviderColumn {
    private Object targetObject;
    private String tableName;
    public String property;
    public String column;
    public boolean isPrimary;
    public boolean isRequired = false;
    public PrimaryType primaryType;
    public PrimaryGenerate PrimaryRule;
    public QueryType query;
    public QueryModel queryModel;
    public ColumnSort sort;
    public JdbcType jdbcType;
    public Class<?> javaType;
    public int weight = 0;

    public String where(){
        if(isPrimary){
            return String.format("%s = %s", columnSql(), valueSql());
        }

        switch (this.query){
            case EQUAL:
            case UNSET: {
                return String.format("%s = %s", columnSql(), valueSql());
            }
            case LIKE:{
                return String.format("%s LIKE CONCAT('%%',%s,'%%')", columnSql(),valueSql());
            }
            case AFTER_LIKE:{
                return String.format("%s LIKE CONCAT(%s,'%%')", columnSql(), valueSql());
            }
            case BEFORE_LIKE:{
                return String.format("%s LIKE CONCAT('%%',%s)", columnSql(),valueSql());
            }
        }
        return "";
    }

    public String valueSql(){
        return String.format("#{%s.%s}", CrudDao.__JavaAlias,this.property);
    }

    public String columnSql(){
        return String.format("%s.%s", this.tableName, this.column);
    }

    public void replace(CrudProviderColumn result){
        if(result.weight > this.weight){
            this.column = result.column;
            this.isPrimary = result.isPrimary;
            this.PrimaryRule = result.PrimaryRule;
            this.query = result.query;
            this.property = result.property;
            this.sort = result.sort;
            this.jdbcType = result.jdbcType;
            this.javaType = result.javaType;
            this.primaryType = result.primaryType;
            this.queryModel = result.queryModel;
            this.weight = result.weight;
        }
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Object get(){
        return ClassUtils.getValue(targetObject, property);
    }

    public void set(Object value){
        ClassUtils.setValue(targetObject, property,value);
    }

    public void createPrimary() throws NoSuchFieldException,RuntimeException {
        Class<?> targetClass = targetObject.getClass();
        Field field =  ClassUtils.getFieldNoPrivacy(targetClass, property);
        Object primary = null;
        if (!PrimaryType.FOREIGN_KEY.equals(this.primaryType)) {
            switch (this.PrimaryRule) {
                case UUID: {
                    if(field.getType().equals(String.class)){
                        primary = UUID.randomUUID().toString();
                    } else {
                        throw new RuntimeException(String.format("%s类中字段%s为%s类型，当前仅能使用java.lang.String类型", targetClass, field.getName(), field.getType()));
                    }
                    break;
                }
                case STAMP: {
                    if(field.getType().equals(Long.class) || field.getType().equals(long.class)){
                        primary = System.currentTimeMillis();
                    } else {
                        throw new RuntimeException(String.format("%s类中字段%s为%s类型，当前仅能使用java.lang.Long类型", targetClass, field.getName(), field.getType()));
                    }
                    break;
                }
            }
        }
        if(Objects.nonNull(primary)){
            set(primary);
        }
    }

    public boolean isNull(){
        return Objects.isNull(get());
    }

    public boolean isNonNull(){
        return !isNull();
    }

    public boolean requireNonNull(){
        if(!isRequired){
            return isNonNull();
        }
        Objects.requireNonNull(get(), String.format("%s类中字段为必填", targetObject.getClass(), property));
        return true;
    }

    public static void replace(CrudProviderColumn target, CrudProviderColumn result){
        target.replace(result);
    }

    public static void replace(CrudProviderColumn target, Column column){
        replace(target,create(column));
    }

    public static void replace(CrudProviderColumn target, Column column, Field field){
        replace(target,create(column, field));
    }

    public static CrudProviderColumn create(Column column) {
        CrudProviderColumn crudProviderColumn = new CrudProviderColumn();
        crudProviderColumn.column = column.column();
        crudProviderColumn.isPrimary = column.isPrimary();
        crudProviderColumn.PrimaryRule = column.PrimaryRule();
        crudProviderColumn.query = column.query();
        crudProviderColumn.property = column.property();
        crudProviderColumn.sort = column.sort();
        crudProviderColumn.jdbcType = column.jdbcType();
        crudProviderColumn.javaType = column.javaType();
        crudProviderColumn.primaryType = column.primaryType();
        crudProviderColumn.queryModel = column.queryModel();
        crudProviderColumn.isRequired = column.isRequired();
        crudProviderColumn.weight = 1;
        return crudProviderColumn;
    }

    public static CrudProviderColumn create(Column column, Field field) {
        CrudProviderColumn crudProviderColumn = create(column);
        crudProviderColumn.column = StringUtils.isBlank(column.column()) ? field.getName() : column.column();
        crudProviderColumn.property = StringUtils.isBlank(column.property()) ? field.getName() : column.property();
        crudProviderColumn.javaType = column.javaType().equals(void.class) ? field.getType() : column.javaType();
        crudProviderColumn.weight = 3;
        return crudProviderColumn;
    }

    public static CrudProviderColumn create(Field field) {
        CrudProviderColumn crudProviderColumn = new CrudProviderColumn();
        crudProviderColumn.column = field.getName();
        crudProviderColumn.property = field.getName();
        crudProviderColumn.javaType = field.getType();
        crudProviderColumn.weight = 0;
        return crudProviderColumn;
    }
}
