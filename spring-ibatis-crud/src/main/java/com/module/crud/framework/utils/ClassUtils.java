package com.module.crud.framework.utils;

import com.module.crud.annotation.Column;
import com.module.crud.annotation.Expand;
import com.module.crud.annotation.Ignore;
import com.module.crud.annotation.Table;
import com.module.crud.dao.CrudDao;
import com.module.crud.entity.ObjectEntity;
import com.module.crud.framework.enumerate.MethodModel;
import com.module.crud.framework.core.CrudProviderColumn;
import com.module.crud.framework.sql.CrudSqlWhereExtension;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ClassUtils {

    public static FieldArray getFields(Class<?> aClass){
        FieldArray fields = new FieldArray();
        Table table = AnnotationUtils.findAnnotation(aClass, Table.class);
        fields.addAll(aClass.getDeclaredFields());
        if (!table.inheritance().equals(void.class)) {
            System.out.println(table.inheritance());
            fields.concat(getFields(table.inheritance()));
        }
        return fields;
    }

    public static class FieldArray {
        private Field[] fields = new Field[0];

        public void add(Field field){
            addAll(new Field[]{field});
        }

        public void addAll(Field ...array) {
            int repeatLength = 0;
            Field[] result= new Field[fields.length + array.length];
            for (int i = 0; i < fields.length; i++) {
                result[i] = fields[i];
            }
            for (int i = 0; i < array.length; i++) {
                Field field = array[i];
                boolean isRepeat = false;
                for (int j = 0; j < fields.length; j++) {
                    if(fields[j].getName().equals(field.getName())){
                        repeatLength++;
                        isRepeat = true;
                        break;
                    }
                }
                if(!isRepeat){
                    result[fields.length + i] = field;
                }
            }
            if(repeatLength != 0){
                Field[] root= new Field[fields.length + array.length - repeatLength];
                int deep = 0;
                for (int i = 0; i < result.length; i++) {
                    if(Objects.nonNull(result[i])){
                        root[deep++] = result[i];
                    }
                }
                fields = root;
            } else {
                fields = result;
            }
        }

        public void addList(List<Field> fieldList){
            Field[] result= new Field[fieldList.size()];
            fieldList.toArray(result);
            addAll(result);
        }

        public void concat(FieldArray fieldArray){
            addAll(fieldArray.toArray());
        }

        public int size(){
            return fields.length;
        }

        public Field get(int index) {
            return fields[index];
        }

        public Field[] toArray(){
            return fields;
        }

        public void forEach(Consumer<Field> action) {
            Objects.requireNonNull(action);
            for (Field t : fields) {
                action.accept(t);
            }
        }
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
                Map<String, CrudProviderColumn> providerColumnMap = getColumnInheritance(column);
                for (String name: providerColumnMap.keySet()) {
                    if(!CrudColumnMap.containsKey(name)){
                        CrudColumnMap.put(name, CrudColumnMap.get(name));
                    }
                }
//                CrudColumnMap.putAll();
            }
        }

        FieldArray fields = getFields(targetClass);

        fields.forEach(field -> {
            System.out.println(field);
            Column column = AnnotationUtils.findAnnotation(field, Column.class);
            System.out.println(column);
            if(Objects.isNull(column)){ return;}
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

    public static Map<String, CrudSqlWhereExtension> getExpandWhere(Object target){
        Table table = AnnotationUtils.findAnnotation(target.getClass(), Table.class);
        Map<String,CrudSqlWhereExtension> whereMap = new HashMap<>();
        FieldArray fields = getFields(target.getClass());
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            Expand.Where expand = AnnotationUtils.findAnnotation(field, Expand.Where.class);
            if(Objects.nonNull(expand)){
                Map<String,Object> data = (Map<String,Object>) getValue(target,field.getName());
                if(data.containsKey(ObjectEntity.WHERE_KEY)){
                    whereMap.put(expand.value(), (CrudSqlWhereExtension) data.get(ObjectEntity.WHERE_KEY));
                }
            }
        }
        return whereMap;
    }
}
