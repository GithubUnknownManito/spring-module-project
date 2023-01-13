package com.module.crud.utils;

import com.module.crud.annotation.*;
import com.module.crud.core.CrudColumnAttr;
import com.module.crud.core.CrudExpandAttr;
import com.module.crud.core.CrudJoinAttr;
import com.module.crud.core.CrudTableAttr;
import com.module.crud.enumerate.ExpandType;
import com.module.crud.enumerate.MethodModel;
import com.module.crud.enumerate.PrimaryType;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ClassUtils {

    public static CrudTableAttr getTableAttr(Class<?> targetClass) {
        Table table = AnnotationUtils.findAnnotation(targetClass, Table.class);
        return new CrudTableAttr(table);
    }

    public static List<CrudJoinAttr> getJoin(Class<?> targetClass) {
        List<CrudJoinAttr> joinAttrList = new ArrayList<>();
        Table table = AnnotationUtils.findAnnotation(targetClass, Table.class);
        Join[] joins = table.joinList();
        for (int i = 0; i < joins.length; i++) {
            List<CrudColumnAttr> columnAttrList =new ArrayList<>();
            Join join = joins[i];
            Column[] columns = join.columns();
            if(join.columns().length < 0){
                for (int j = 0; j < columns.length; j++) {
                    if(columns[j].inheritance().equals(void.class)){
                        columnAttrList.add(new CrudColumnAttr(columns[i]));
                    } else {
                        columnAttrList.addAll(getColumnList(columns[j].inheritance()));
                    }
                }
            } else {
                columnAttrList.addAll(getColumnList(targetClass));
            }
            joinAttrList.add(new CrudJoinAttr(join, AnnotationUtils.findAnnotation(join.quote(), Table.class), columnAttrList));
        }

        return joinAttrList;
    }



    public static List<CrudColumnAttr> getColumnList(Class<?> targetClass) {
        List<CrudColumnAttr> columns = new ArrayList<>();
        Table table = AnnotationUtils.findAnnotation(targetClass, Table.class);
        Column[] columnsArray = table.columns();
        for (int i = 0; i < columnsArray.length; i++) {
            if (columnsArray[i].inheritance().equals(void.class)) {
                columns.add(new CrudColumnAttr(columnsArray[i]));
            } else {
                columns.addAll(getColumnList(targetClass));
            }
        }

        List<Field> fields = getFields(targetClass);
        fields.forEach(field -> {
            Column column = AnnotationUtils.findAnnotation(targetClass, Column.class);
            if (column.inheritance().equals(void.class)) {
                columns.add(new CrudColumnAttr(column));
            } else {
                throw new RuntimeException(String.format("%s.%s @Column 不明白意义的注解", targetClass, field.getName()));
            }
        });

        return columns.stream().map(column -> column.property).distinct().map(property -> {
            return columns.stream().filter(column -> column.property == property).sorted((a, b) -> b.weight - a.weight).findAny().get();
        }).collect(Collectors.toList());
    }

    public static List<Field> getFields(Class<?> aClass) {
        Table table = AnnotationUtils.findAnnotation(aClass, Table.class);
        List<Field> fields = Arrays.asList(aClass.getDeclaredFields());
        if (!table.inheritance().equals(void.class)) {
            fields.addAll(getFields(table.inheritance()));
        }
        return fields.stream().filter(field -> !field.isAnnotationPresent(Ignore.class)).collect(Collectors.toList());
    }

    public static CrudExpandAttr getExpand(Object target, ExpandType expandType){
        List<Field> fields = getFields(target.getClass());
        Optional<Field> expandOptional = fields.stream().filter(field -> field.isAnnotationPresent(Expand.class) && field.getAnnotation(Expand.class).value().equals(expandType)).findAny();
        if(expandOptional.isPresent()){
            Field field = expandOptional.get();
            return new CrudExpandAttr(field.getAnnotation(Expand.class), field, getValue(target, field.getName()));
        }
        return null;
    }

    public static Method getMethod(Class<?> targetClass, String fieldName, MethodModel model, boolean required) {
        String UpChat = fieldName.substring(0, 1).toUpperCase();
        String YYName = fieldName.substring(1);
        Method method = null;
        try {
            method = targetClass.getMethod(model.getText() + UpChat + YYName);
        } catch (NoSuchMethodException e) {
            if(required){
                throw new RuntimeException(String.format("在实体%s中未能找到%s方法"), e);
            }
        }
        method.setAccessible(true);
        return method;
    }

    public static Object getValue(Object target, String fieldName) {
        Method method = getMethod(target.getClass(), fieldName, MethodModel.GET, true);
        try {
            return method.invoke(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void setValue(Object target, String fieldName, Object value, Class<T> aClass) {
        Method method = getMethod(target.getClass(), fieldName, MethodModel.SET, true);
        try {
            method.invoke(target, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object GeneratePrimary(CrudColumnAttr attr) {
        if (PrimaryType.FOREIGN_KEY.equals(attr.primaryType)) {
            return null;
        }
        switch (attr.PrimaryRule) {
            case UUID: {
                return UUID.randomUUID().toString();
            }
            case STAMP: {
                return System.currentTimeMillis();
            }
        }
        return null;
    }
}
