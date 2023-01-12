package com.module.crud.provider;

import com.module.crud.core.CrudRunTime;
import org.apache.ibatis.jdbc.SQL;

public class CrudUpdateProvider extends CrudRunTime {
    @Override
    public String run() {
        return new SQL() {{
            UPDATE(tableAttr.tableName);
            SET(set());
            WHERE(where());
        }}.toString();
    }

    public String[] set() {
        return columnAttrs.stream().filter(columnAttrs -> columnAttrs.isValueNull())
                .map(columnAttrs -> String.format("%s=%s", columnAttrs.column, columnAttrs.getParamProperty())).toArray(String[]::new);
    }

    public String[] where() {
        String[] _where =  columnAttrs.stream().filter(columnAttrs -> columnAttrs.isPrimary && columnAttrs.isValueNull())
                .map(columnAttrs -> String.format("%s=%s", columnAttrs.column, columnAttrs.getParamProperty())).toArray(String[]::new);
        if(_where.length == 0){
            throw new NullPointerException("UPDATE语法在是实体类中未发现有值的主键");
        }
        return _where;
    }
}
