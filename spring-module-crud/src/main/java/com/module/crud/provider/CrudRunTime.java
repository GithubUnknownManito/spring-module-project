package com.module.crud.provider;

import com.module.crud.annotation.Column;
import com.module.crud.annotation.Table;
import com.module.crud.dao.CrudDao;
import com.module.crud.enumerate.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CrudRunTime {
    public Class<?> targetClass;
    public Object targetObject;
    public String tableName;
    public Table table;
    public List<Field> fieldList;
    public Map<String, List<ColumnAttr>> fieldColumn = new HashMap<>();
    public List<ColumnAttr> columnAttrs = new ArrayList<>();

    public <E> String initialize(E data) {
        targetObject = data;
        targetClass = data.getClass();
        table = AnnotationUtils.findAnnotation(targetClass, Table.class);
        Objects.requireNonNull(table, "仅能执行实体类存在 com.module.crud.annotation.Table 注解的类");
        tableName = table.name();
        fieldList = getFields();

        switch (table.pattern()) {
            case FIELD: {
                fieldList.forEach(field -> {
                    List<ColumnAttr> columnList = new ArrayList<>();
                    if (fieldColumn.containsKey(field.getName())) {
                        columnList = fieldColumn.get(field.getName());
                    }
                    columnList.add(new ColumnAttr(field));
                    fieldColumn.put(field.getName(), columnList);
                });
                if (!table.pattern().equals(Pattern.BLEND)) {
                    break;
                }
            }
            case ANNOTATION: {
                Arrays.asList(table.column()).forEach(column -> {
                    List<ColumnAttr> columnList = new ArrayList<>();
                    if (fieldColumn.containsKey(column.property())) {
                        columnList = fieldColumn.get(column.property());
                    }
                    columnList.add(new ColumnAttr(column));
                    fieldColumn.put(column.property(), columnList);
                });

                fieldList.forEach(field -> {
                    List<ColumnAttr> columnList = new ArrayList<>();
                    Column column = AnnotationUtils.findAnnotation(field, Column.class);
                    if (Objects.isNull(column)) {
                        return;
                    }
                    if (fieldColumn.containsKey(field.getName())) {

                        columnList = fieldColumn.get(field.getName());
                    }
                    columnList.add(new ColumnAttr(column, field));
                    fieldColumn.put(field.getName(), columnList);
                });
            }
        }
        fieldColumn.values().forEach(array -> {
            Optional<ColumnAttr> columnAttrOptional = array.stream().sorted((t1, t2) -> t2.weight - t1.weight).findAny();
            if (columnAttrOptional.isPresent()) {
                columnAttrs.add(columnAttrOptional.get());
            }
        });
        return Run();
    }

    public List<Field> getFields() {
        return CrudRunTime.getFields(targetClass);
    }

    abstract String Run();

    public static List<Field> getFields(Class<?> aClass) {
        Table table = AnnotationUtils.findAnnotation(aClass, Table.class);
        List<Field> fields = Arrays.asList(aClass.getDeclaredFields());
        if (!table.inheritance().equals(void.class)) {
            fields.addAll(getFields(table.inheritance()));
        }
        return fields;
    }

    public Object getValue(ColumnAttr column) {
        return getValue(targetObject, column.property);
    }

    public String getSqlTableName(){
        if (StringUtils.isNoneBlank(table.alias())) {
            return tableName;
        } else {
            return String.format("%s AS %s", tableName, table.alias());
        }
    }

    public String getSqlColumnAlias(ColumnAttr column){
        if (StringUtils.isNoneBlank(table.alias())) {
            return String.format("%s.%s AS %s", table.alias(), column.column, column.property);
        }
        return String.format("%s AS %s", column.column, column.property);
    }

    public String getSqlSelectColumnAlias(ColumnAttr column){
        if (StringUtils.isNoneBlank(table.alias())) {
            return String.format("%s.%s AS %s", table.alias(), column.column, column.property);
        }
        return String.format("%s AS %s", column.column, column.property);
    }

    public String getSqlParam(ColumnAttr column) {
        if (StringUtils.isNoneBlank(CrudDao.__JavaAlias)) {
            return String.format("#{%s.%s}", CrudDao.__JavaAlias ,column.property);
        }
        return String.format("#{%s}", column.property);
    }

    public static Object getValue(Object obj, String fieldName) {
        Class<?> targetClass = obj.getClass();
        String MethodName = String.format("get%s%s", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));
        Method method = null;
        try {
            method = targetClass.getMethod(MethodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("在实体%s中未能找到%s方法"), e);
        }
        method.setAccessible(true);
        try {
            return method.invoke(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void setValue(ColumnAttr column, Object value) {
        setValue(targetObject, column.property, value, column.javaType);
    }

    public static <T> void setValue(Object obj, String fieldName, Object value, Class<T> aClass) {
        Class<?> targetClass = obj.getClass();
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
            method.invoke(obj, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean isValueNotBlank(ColumnAttr column) {
        return !isValueBlank(column);
    }

    public boolean isValueBlank(ColumnAttr column) {
        Object data = getValue(column);
        if (Objects.isNull(data)) {
            return true;
        }
        if (column.javaType.equals(String.class)) {
            return StringUtils.isBlank(data.toString());
        }
        return false;
    }

    public boolean isValueNotNull(ColumnAttr column) {
        return !isValueNull(column);
    }

    public static boolean isValueNotNull(Object obj, String fieldName) {
        return !isValueNull(obj, fieldName);
    }

    public boolean isValueNull(ColumnAttr column) {
        return Objects.isNull(getValue(column));
    }

    public static boolean isValueNull(Object obj, String fieldName) {
        return Objects.isNull(getValue(obj, fieldName));
    }

    public static String Join(Collection<String> collection) {
        return String.join(",", collection);
    }

    public static String Join(Stream<String> collection) {
        return Join(collection.collect(Collectors.toList()));
    }

    public static Object GeneratePrimary(ColumnAttr attr) {
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

    public static Object GeneratePrimary(Column column) {
        return GeneratePrimary(new ColumnAttr(column));
    }

    static class ColumnAttr {
        public ColumnAttr(Field field) {
            this.column = field.getName();
            this.property = field.getName();
            this.javaType = field.getType();
            this.weight = 0;
            initializeWeight(1);
        }

        public ColumnAttr(Column column) {
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
            initializeWeight(2);
        }

        public ColumnAttr(Column column, Field field) {
            this(column);
            this.column = StringUtils.isBlank(column.column()) ? field.getName() : column.column();
            this.property = StringUtils.isBlank(column.column()) ? field.getName() : column.column();
            this.javaType = column.javaType().equals(void.class) ? field.getType() : column.javaType();
            initializeWeight(3);
        }

        private void initializeWeight(int weight) {
            if (this.weight == 0) {
                this.weight += weight;
            }
        }

        public String column;
        public boolean isPrimary = false;
        public PrimaryType primaryType = PrimaryType.PRIMARY_KEY;
        public PrimaryGenerate PrimaryRule = PrimaryGenerate.UNSET;
        public QueryType query = QueryType.UNSET;
        public String property;
        public ColumnSort sort = ColumnSort.UNSET;
        public JdbcType jdbcType = JdbcType.UNDEFINED;
        public Class<?> javaType = void.class;
        public QueryModel queryModel = QueryModel.AND;
        public int weight = 0;

    }
}
