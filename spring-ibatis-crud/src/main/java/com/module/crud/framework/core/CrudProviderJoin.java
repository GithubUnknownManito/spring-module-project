package com.module.crud.framework.core;

import com.module.crud.annotation.Column;
import com.module.crud.annotation.Join;
import com.module.crud.annotation.Table;
import com.module.crud.framework.utils.ClassUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrudProviderJoin {
    public CrudProviderJoin(Join join){
        Table table = AnnotationUtils.findAnnotation(join.quote(), Table.class);
        tableName = table.name();
        alias = Strings.isBlank(join.alias())?table.name():join.alias();
        on = join.on();

        Column[] columns = join.columns();
        for (int i = 0; i < columns.length; i++) {
            Column column = columns[i];
            if(column.inheritance().equals(void.class)){
                CrudColumnMap.put(column.property(), CrudProviderColumn.create(column));
            } else {
                CrudColumnMap.putAll(ClassUtils.getColumnInheritance(column));
            }
        }
    }
    public String tableName;
    public String alias;
    public String on;
    private Map<String, CrudProviderColumn> CrudColumnMap = new HashMap<>();

    public List<CrudProviderColumn> columns() {
        return CrudColumnMap.values().stream().map(item -> {
            item.setTableName(alias);
            return item;
        }).collect(Collectors.toList());
    }
}
