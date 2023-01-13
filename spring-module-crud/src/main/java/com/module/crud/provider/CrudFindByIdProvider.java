package com.module.crud.provider;

import com.module.crud.core.CrudRunTime;
import org.apache.ibatis.jdbc.SQL;

public class CrudFindByIdProvider extends CrudRunTime {
    @Override
    public String run() {
        return new SQL(){{
            SELECT_DISTINCT(columns());
            FROM(tableAttr.getTableNameBySelect());
            if(joinList.size() > 0){

            }
            WHERE(where());
        }}.toString();
    }

    public String[] columns(){
       return columnAttrs.stream().map(columnAttrs -> columnAttrs.column).toArray(String[] ::new);
    }

    public String[] where(){
        return columnAttrs.stream().filter(columnAttrs -> columnAttrs.isPrimary && columnAttrs.isValueNotNull())
                .map(columnAttrs -> columnAttrs.getParamProperty()).toArray(String[] ::new);
    }


}
