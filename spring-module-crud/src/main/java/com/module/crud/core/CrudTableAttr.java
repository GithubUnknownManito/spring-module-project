package com.module.crud.core;

import com.module.crud.annotation.Join;
import com.module.crud.annotation.Table;
import com.module.crud.utils.StringUtils;

public class CrudTableAttr {
    public String name;
    public String alias;
    public Join[] join;


    public CrudTableAttr(Table table) {
        this.name = table.name();
        this.alias = table.alias();
        this.join = table.join();
    }

    public String getTableNameBySelect(){
        if(StringUtils.isNotBlank(alias)){
            return String.format("%s AS %s");
        }
        return name;
    }
}
