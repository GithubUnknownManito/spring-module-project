package com.module.crud.core;

import com.module.crud.annotation.Column;
import com.module.crud.annotation.Table;
import com.module.crud.enumerate.Pattern;
import com.module.crud.utils.ClassUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CrudRunTime {

    private Class<?> targetClass;
    private Object targetObject;
    private CrudTableAttr tableAttr;
    private List<CrudColumnAttr> columnAttrs = new ArrayList<>();

    public <E> String initialize(E data) {
        this.targetObject = data;
        this.targetClass = data.getClass();
        initializeAttr();
        return run();
    }

    private void initializeAttr() {
        Table table = AnnotationUtils.findAnnotation(targetClass, Table.class);
        tableAttr = new CrudTableAttr(table);
        List<Column> columns = Arrays.asList(table.column());
        List<Field> fieldList = ClassUtils.getFields(targetClass);
        if(Pattern.ANNOTATION.equals(table.pattern())){
            fieldList.forEach(field -> {
                if(field.isAnnotationPresent(Column.class)){

                }
                columns.stream().filter(column -> column.property().equals(field.getName()))
            });
        }


    }

    public abstract String run();


}
