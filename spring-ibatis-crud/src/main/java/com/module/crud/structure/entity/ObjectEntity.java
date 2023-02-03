package com.module.crud.structure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.module.crud.annotation.Expand;
import com.module.crud.annotation.Ignore;
import com.module.crud.framework.core.CrudPage;
import com.module.crud.framework.sql.CrudSqlWhereExtension;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ObjectEntity implements Serializable {
    @Ignore
    public static final String WHERE_KEY = "__SQL_WHERE__";
    @Ignore
    private static final String DISABLE_PRIMARY = "__SQL_DISABLE_PRIMARY__";
    @Ignore
    private CrudPage page;

    @Ignore
    @Expand.Where(WHERE_KEY)
    @JsonIgnore
    private Map<String,Object> SqlData = new HashMap<>();

    public CrudSqlWhereExtension Where(){
        CrudSqlWhereExtension where = null;
        if(SqlData.containsKey(WHERE_KEY)){
            where = (CrudSqlWhereExtension) SqlData.get(WHERE_KEY);
        }
        if(Objects.isNull(where)){
            where = new CrudSqlWhereExtension(SqlData);
        }
        SqlData.put(WHERE_KEY, where);
        return where;
    }

    public void DisablePrimary(){
        SqlData.put(DISABLE_PRIMARY, false);
    }

    @JsonIgnore
    public Map<String, Object> getSqlData() {
        return SqlData;
    }

    @JsonIgnore
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
