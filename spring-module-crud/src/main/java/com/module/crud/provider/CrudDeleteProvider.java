package com.module.crud.provider;

import com.module.crud.common.CrudConfigure;
import com.module.crud.core.CrudRunTime;
import com.module.crud.utils.SpringContextUtils;
import org.apache.ibatis.jdbc.SQL;

public class CrudDeleteProvider extends CrudRunTime {
    @Override
    public String run() {
        CrudConfigure configure = SpringContextUtils.getBean(CrudConfigure.class);

        if(configure.isLogicalDeletion()){
            return  new SQL(){{
                UPDATE(tableAttr.tableName);
                SET("status = 1");
                WHERE(where());
            }}.toString();
        } else {
            return new SQL(){{
                DELETE_FROM(tableAttr.tableName);
                WHERE(where());
            }}.toString();
        }


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
