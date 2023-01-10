package com.module.crud.core;

import com.module.crud.annotation.*;
import com.module.crud.enumerate.Pattern;
import com.module.crud.utils.StringUtils;

import java.util.Arrays;
import java.util.List;

public class CrudTableAttr {
    public String tableName;
    public String alias;
    public Pattern pattern;
    public List<One> oneList;
    public List<Many> manyList;
    public List<Join> joinList;

    public CrudTableAttr(Table table) {
        this.pattern = table.pattern();
        this.alias = table.alias();
        this.tableName = StringUtils.isBlank(table.name()) ? table.value() : table.name();
        this.oneList = Arrays.asList(table.one());
        this.manyList = Arrays.asList(table.many());
        this.joinList = Arrays.asList(table.join());
    }

    public String getTableNameBySelect(){
        if(StringUtils.isNotBlank(alias)){
            return String.format("%s AS %s",alias , tableName);
        }
        return tableName;
    }
}
