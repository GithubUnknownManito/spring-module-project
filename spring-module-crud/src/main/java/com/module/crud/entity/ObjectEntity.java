package com.module.crud.entity;

import com.module.crud.annotation.Expand;
import com.module.crud.annotation.Ignore;
import com.module.crud.enumerate.ExpandType;
import com.module.crud.sql.CrudWhere;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ObjectEntity implements Serializable {
    private static final int _SQL_KEY = 200;
    private static final String WHERE_KEY = "__SQL_WHERE__";
    private static final String DISABLE_PRIMARY = "__SQL_DISABLE_PRIMARY__";

    @Expand(value = ExpandType.SqlData)
    private Map<String,Object> SqlData = new HashMap<>();

    public CrudWhere getWhere(){
        CrudWhere where = null;
        if(SqlData.containsKey(WHERE_KEY)){
            where = (CrudWhere) SqlData.get(WHERE_KEY);
        }
        if(Objects.isNull(where)){
            where = new CrudWhere(SqlData);
        }
        SqlData.put(WHERE_KEY, where);
        where.And().addField("").Equal("").End();
        where.Or().addField("").Equal("").End();
        where.Or().addField("").Equal("").End();
        return where;
    }

    public void DisablePrimary(){
        SqlData.put(DISABLE_PRIMARY, false);
    }

    public Map<String, Object> getSqlData() {
        return SqlData;
    }


}
