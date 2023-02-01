package com.module.crud.framework.provider;

import com.module.crud.common.CrudConfigure;
import com.module.crud.framework.core.CrudProviderInterface;
import com.module.crud.framework.core.CrudProviderRunTime;
import com.module.crud.framework.utils.SpringContextUtils;
import org.apache.ibatis.jdbc.SQL;

public class CrudDeleteProvider extends CrudProviderRunTime implements CrudProviderInterface {

    @Override
    public String run() {

        CrudConfigure configure =  SpringContextUtils.getBean(CrudConfigure.class);
        if(configure.isLogicalDeletion()){
            return new SQL(){{
                UPDATE(tableName);
                SET("status = 1");
                WHERE(columnPrimary());
            }}.toString();
        } else {

        }

        return null;
    }

    public String[] columnPrimary(){
        return columns().filter(column -> column.isPrimary && column.isNonNull())
                .map(column -> String.format("%s = %s",column.column, column.valueSql())).toArray(String[]::new);
    }
}
