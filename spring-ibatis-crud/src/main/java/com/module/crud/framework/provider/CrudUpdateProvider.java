package com.module.crud.framework.provider;

import com.module.crud.framework.core.CrudProviderInterface;
import com.module.crud.framework.core.CrudProviderRunTime;
import org.apache.ibatis.jdbc.SQL;

public class CrudUpdateProvider extends CrudProviderRunTime implements CrudProviderInterface {

    @Override
    public String run() {
        String[] where = columnPrimary();
        if(where.length == 0){
            throw new RuntimeException(String.format("%s在更新时未指定主键，当前功能被限制，禁止无主键更新", getTargetClass()));
        }
        return new SQL(){
            {
                UPDATE(tableName);
                SET(columnsSql());
                WHERE(where);
            }
        }.toString();
    }

    public String[] columnPrimary(){
        return columns().filter(column -> column.isPrimary && column.isNonNull())
                .map(column -> String.format("%s = %s",column.column, column.valueSql())).toArray(String[]::new);
    }

    public String[] columnsSql(){
        return columns().filter(column -> !column.isPrimary && column.isNonNull())
                .map(column -> String.format("%s = %s",column.column, column.valueSql())).toArray(String[]::new);
    }
}
