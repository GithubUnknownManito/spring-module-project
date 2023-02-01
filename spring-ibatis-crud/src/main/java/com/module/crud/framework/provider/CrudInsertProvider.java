package com.module.crud.framework.provider;

import com.module.crud.enumerate.PrimaryType;
import com.module.crud.framework.core.CrudProviderColumn;
import com.module.crud.framework.core.CrudProviderInterface;
import com.module.crud.framework.core.CrudProviderRunTime;
import org.apache.ibatis.jdbc.SQL;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrudInsertProvider extends CrudProviderRunTime implements CrudProviderInterface {

    @Override
    public String run() {
        columnPrimary();
        return new SQL(){
            {
                INSERT_INTO(tableName);
                INTO_COLUMNS(columnsSql());
                INTO_VALUES(valuesSql());
            }
        }.toString();
    }

    public void columnPrimary(){
        columns().filter(crudProviderColumn -> crudProviderColumn.isPrimary).forEach(crudProviderColumn -> {
            try {
                crudProviderColumn.createPrimary();
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public String[] columnsSql(){
        return columns().filter(column -> column.requireNonNull()).map(column -> column.column).toArray(String[]::new);
    }

    public String[] valuesSql(){
        return columns().filter(column -> column.requireNonNull()).map(column -> column.valueSql()).toArray(String[]::new);
    }
}
