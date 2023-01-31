package com.module.crud.entity;

import com.module.crud.annotation.Expand;
import com.module.crud.annotation.Ignore;
import com.module.crud.framework.core.CrudPage;
import com.module.crud.enumerate.ExpandType;
import com.module.crud.framework.sql.CrudWhere;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ObjectEntity implements Serializable {
    @Ignore
    private static final String WHERE_KEY = "__SQL_WHERE__";
    @Ignore
    private static final String DISABLE_PRIMARY = "__SQL_DISABLE_PRIMARY__";
    @Ignore
    private CrudPage page;

    @Expand(Where = {
        @Expand.Where(WHERE_KEY)
    }, param = "SqlData")
    @Ignore
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
//        where.And().addField("").Equal("").End();
//        where.Or().addField("").Equal("").End();
//        where.Or().addField("").Equal("").End();
        return where;
    }

    public void DisablePrimary(){
        SqlData.put(DISABLE_PRIMARY, false);
    }

    public Map<String, Object> getSqlData() {
        return SqlData;
    }

    public CrudPage getPage() {
        return page;
    }

    public void setPage(CrudPage page) {
        this.page = page;
    }

    public void setPage(int pageNum, int pageSize) {
        this.page = new CrudPage(pageNum, pageSize);
    }
}
