package com.module.crud.core;

import com.module.crud.enumerate.*;
import com.module.crud.utils.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class CrudRunTime {

    protected Class<?> targetClass;
    protected Object targetObject;
    protected CrudTableAttr tableAttr;
    protected List<CrudColumnAttr> columnAttrs = new ArrayList<>();
    protected CrudExpandAttr sqlExpandData;
    protected boolean isExpandWhere;

    public <E> String initialize(E data) {
        this.targetObject = data;
        this.targetClass = data.getClass();
        initializeAttr();
        return run();
    }

    private void initializeAttr() {
        sqlExpandData = ClassUtils.getExpand(targetObject, ExpandType.SqlData);
        isExpandWhere = Objects.nonNull(sqlExpandData);
        tableAttr = ClassUtils.getTableAttr(targetClass);
        List<CrudColumnAttr> columnAttrList = new ArrayList<>();
        List<Field> fieldList = ClassUtils.getFields(targetClass);
        boolean isBlend = !Pattern.BLEND.equals(tableAttr.pattern);
        switch (tableAttr.pattern) {
            case FIELD: {
                for (int i = 0; i < fieldList.size(); i++) {
                    columnAttrList.add(new CrudColumnAttr(fieldList.get(i)));
                }
                if (isBlend) {
                    break;
                }
            }
            case ANNOTATION: {
                columnAttrList.addAll(ClassUtils.getColumnList(targetClass));
                if (isBlend) {
                    break;
                }
            }
        }

        columnAttrs = columnAttrList.stream().map(column -> column.property).distinct().map(property -> {
            return columnAttrList.stream().filter(column -> column.property == property).sorted((a, b) -> b.weight - a.weight).findAny().get();
        }).collect(Collectors.toList());

        columnAttrs.stream().filter(c-> c.isPrimary && c.primaryType.equals(PrimaryType.PRIMARY_KEY) && !c.PrimaryRule.equals(PrimaryGenerate.UNSET) && c.javaType.equals(void.class) ).forEach(column -> {
             try {
                 Method method = ClassUtils.getMethod(targetClass, column.property, MethodModel.GET, true);
                 column.javaType = method.getReturnType();
             }catch (Throwable throwable){
                 throw  new RuntimeException("查找到未定义Java字段类型的主键，将在尝试补充时发生异常，详情查看内部原因", throwable);
             }
        });

        columnAttrs.forEach(column -> {
            column.setTargetObject(targetObject);
        });
    }

    public abstract String run();


}
