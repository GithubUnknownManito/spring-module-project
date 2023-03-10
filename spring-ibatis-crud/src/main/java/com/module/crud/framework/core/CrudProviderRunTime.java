package com.module.crud.framework.core;

import com.module.crud.annotation.Join;
import com.module.crud.annotation.Table;
import com.module.crud.structure.dao.CrudDao;
import com.module.crud.framework.sql.CrudSqlWhereExtension;
import com.module.crud.framework.utils.ClassUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public abstract class CrudProviderRunTime implements CrudProviderInterface {
    private Map<String, CrudProviderColumn> CrudColumnMap = new HashMap<>();
    private Map<String, CrudProviderJoin> CrudJoinMap = new HashMap<>();
    private Map<String, CrudSqlWhereExtension> CrudExtension = new HashMap<>();
    protected Object targetObject;
    protected Class<?> targetClass;
    public String tableName;
    public String alias;

    public <E> String initialize(E map) {
        if(map instanceof MapperMethod.ParamMap){
            targetObject = ((MapperMethod.ParamMap<?>) map).get(CrudDao.__JavaAlias);
        } else {
            targetObject = map;
        }
        Table table = AnnotationUtils.findAnnotation(targetClass = targetObject.getClass(), Table.class);
        this.tableName = table.name();
        this.alias = Strings.isBlank(table.alias())?table.name():table.alias();
        switch (table.pattern()) {
            case ANNOTATION:{
                CrudColumnMap =  ClassUtils.getColumnMap(targetClass);
                break;
            }
            case FIELD:{
                ClassUtils.getFields(targetClass).forEach(item -> {
                    CrudColumnMap.put(item.getName(), CrudProviderColumn.create(item));
                });
                break;
            }
        }

        columns().forEach(columns -> {
            columns.setTargetObject(targetObject);
            columns.setTableName(alias);
        });

        Join[] joins = table.joinList();
        for (int i = 0; i < joins.length; i++) {
            CrudJoinMap.put(joins[i].alias(), new CrudProviderJoin(joins[i]));
        }
        CrudExtension = ClassUtils.getExpandWhere(targetObject);
        return run();
    }


    @Override
    public Stream<CrudProviderJoin> joins() {
        return CrudJoinMap.values().stream();
    }

    @Override
    public boolean isJoin() {
        return !CrudJoinMap.isEmpty();
    }

    @Override
    public Stream<CrudProviderColumn> columns() {
        return CrudColumnMap.values().stream();
    }

    @Override
    public Stream<CrudSqlWhereExtension> expandWhere() {
        return CrudExtension.values().stream();
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }
}
