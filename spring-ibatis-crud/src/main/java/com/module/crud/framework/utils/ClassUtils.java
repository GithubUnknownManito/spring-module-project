package com.module.crud.framework.utils;

import com.module.crud.annotation.Column;
import com.module.crud.annotation.Ignore;
import com.module.crud.annotation.Table;
import com.module.crud.enumerate.Pattern;
import com.module.crud.framework.enumerate.MethodModel;
import com.module.crud.framework.core.CrudProviderColumn;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ClassUtils {
    public static List<Field> getFields(Class<?> aClass) {
        Table table = AnnotationUtils.findAnnotation(aClass, Table.class);
        List<Field> fields = Arrays.asList(aClass.getDeclaredFields());
        if (!table.inheritance().equals(void.class)) {
            fields.addAll(getFields(table.inheritance()));
        }
        return fields.stream().filter(field -> !field.isAnnotationPresent(Ignore.class)).collect(Collectors.toList());
    }

    public static Map<String, CrudProviderColumn> getColumnMap(Class<?> targetClass) {
        Map<String, CrudProviderColumn> CrudColumnMap = new HashMap<>();

        Table table = AnnotationUtils.findAnnotation(targetClass, Table.class);
        Column[] columns = table.columns();
        for (int i = 0; i < columns.length; i++) {
            Column column = columns[i];
            if (column.inheritance().equals(void.class)) {
                CrudColumnMap.put(column.property(), CrudProviderColumn.create(columns[i]));
            } else {
                CrudColumnMap.putAll(getColumnInheritance(column));
            }
        }

        List<Field> fields = getFields(targetClass);

        fields.forEach(field -> {
            Column column = AnnotationUtils.findAnnotation(field, Column.class);
            if (column.inheritance().equals(void.class)) {
                CrudProviderColumn.replace(CrudColumnMap.get(field.getName()), column, field);
            } else {
                throw new RuntimeException(String.format("%s.%s @Column 不明白意义的注解", targetClass, field.getName()));
            }
        });
        return CrudColumnMap;
    }

    public static Map<String, CrudProviderColumn> getColumnInheritance(Column column){
        if(column.inheritance().equals(void.class)){
            return getColumnMap(column.inheritance());
        }
        return new HashMap<>();
    }

    public static Field getFieldNoPrivacy(Class<?> targetClass, String fieldName) throws NoSuchFieldException {
        try {
            return targetClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> Superclass = targetClass.getSuperclass();
            if(Objects.nonNull(Superclass)){
                return getFieldNoPrivacy(Superclass, fieldName);
            } else {
                throw new NoSuchFieldException();
            }
        }
    }

    public static Method getMethod(Class<?> targetClass, String fieldName, MethodModel model, boolean required) {
        String UpChat = fieldName.substring(0, 1).toUpperCase();
        String YYName = fieldName.substring(1);
        Method method = null;
        String methodName = "";
        try {
            Field field = getFieldNoPrivacy(targetClass, fieldName);
            switch (model) {
                case GET:{
                    if(field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)){
                        methodName = "is%s%s";
                    } else {
                        methodName = "get%s%s";
                    }
                    method = targetClass.getMethod(String.format(methodName, UpChat, YYName));
                    break;
                }
                case SET:{
                    methodName = "set%s%s";
                    method = targetClass.getMethod(String.format(methodName, UpChat, YYName), field.getType());
                    break;
                }
            }
        } catch (NoSuchMethodException e) {
            if(required){
                throw new RuntimeException(String.format("在实体%s中未能找到%s方法", targetClass.getName(),methodName), e);
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(String.format("在实体%s中未能找到%s属性", targetClass.getName(),fieldName), e);
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

    public static <T> void setValue(Object target, String fieldName, Object value) {
        Method method = getMethod(target.getClass(), fieldName, MethodModel.SET, true);
        try {
            method.invoke(target, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }





//    public static Object GeneratePrimary(CrudColumnAttr attr) {
//        if (PrimaryType.FOREIGN_KEY.equals(attr.primaryType)) {
//            return null;
//        }
//        switch (attr.PrimaryRule) {
//            case UUID: {
//                return UUID.randomUUID().toString();
//            }
//            case STAMP: {
//                return System.currentTimeMillis();
//            }
//        }
//        return null;
//    }

}
