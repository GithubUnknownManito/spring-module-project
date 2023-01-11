package com.module.crud.provider;

import com.module.crud.core.CrudRunTime;
import com.module.crud.dao.CrudDao;
import com.module.crud.enumerate.PrimaryType;
import org.apache.ibatis.jdbc.SQL;

import java.util.stream.Collectors;

public class CrudInsertProvider extends CrudRunTime {
    @Override
    public String run() {
        return new SQL(){{
            INSERT_INTO(tableAttr.tableName);
            INTO_COLUMNS(columns());
            INTO_VALUES(values());
        }}.toString();
    }

    public String[] columns(){
        columnAttrs.stream().filter(columnAttrs -> columnAttrs.isPrimary && columnAttrs.primaryType.equals(PrimaryType.PRIMARY_KEY)).forEach(columnAttrs -> {
            columnAttrs.createPrimary();
        });
        return columnAttrs.stream().map(columnAttrs -> columnAttrs.column).toArray(String[]::new);
    }

    public String[] values(){
        return columnAttrs.stream().map(columnAttrs -> columnAttrs.getParamProperty()).toArray(String[]::new);
    }
}
