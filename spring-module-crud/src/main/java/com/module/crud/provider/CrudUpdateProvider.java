package com.module.crud.provider;

import com.module.crud.core.CrudRunTime;
import org.apache.ibatis.jdbc.SQL;

public class CrudUpdateProvider extends CrudRunTime {
    @Override
    public String run() {
        return new SQL(){{
            UPDATE(tableAttr.tableName);

        }}.toString();
    }

    public String[] set(){
         return columnAttrs.stream().filter(columnAttrs -> columnAttrs.isValueNull()).map(columnAttrs-> String.format("%s=%s", columnAttrs.column, columnAttrs.getParamProperty()) ).toArray(String[]::new);
    }

    public String[] where(){
        columnAttrs.stream().filter(columnAttrs -> columnAttrs.isPrimary);
        return new String[0];
    }
}
