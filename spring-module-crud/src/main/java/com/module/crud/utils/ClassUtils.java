package com.module.crud.utils;

import com.module.crud.annotation.Column;
import com.module.crud.annotation.Table;
import com.module.crud.core.CrudColumnAttr;
import com.module.crud.core.CrudTableAttr;
import com.module.crud.enumerate.PrimaryType;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ClassUtils {
    public static List<Field> getFields(Class<?> aClass) {
        Table table = AnnotationUtils.findAnnotation(aClass, Table.class);
        List<Field> fields = Arrays.asList(aClass.getDeclaredFields());
        if (!table.inheritance().equals(void.class)) {
            fields.addAll(getFields(table.inheritance()));
        }
        return fields;
    }

    public static Object getValue(Object target, String fieldName) {
        Class<?> targetClass = target.getClass();
        String MethodName = String.format("get%s%s", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));
        Method method = null;
        try {
            method = targetClass.getMethod(MethodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("在实体%s中未能找到%s方法"), e);
        }
        method.setAccessible(true);
        try {
            return method.invoke(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void setValue(Object target, String fieldName, Object value, Class<T> aClass) {
        Class<?> targetClass = target.getClass();
        String MethodName = String.format("set%s%s", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));
        Method method = null;
        try {
            if (aClass.equals(void.class)) {
                method = targetClass.getMethod(MethodName, null);
            } else {
                method = targetClass.getMethod(MethodName, aClass);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("在实体%s中未能找到%s方法"), e);
        }
        method.setAccessible(true);
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
            case UNSET: {
                return null;
            }
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
