package com.module.crud.core;

import com.module.crud.annotation.Join;
import com.module.crud.annotation.Many;
import com.module.crud.annotation.One;
import com.module.crud.annotation.Table;
import com.module.crud.enumerate.JoinType;

import java.util.List;

public class CrudJoinAttr extends CrudTableAttr {
    /**
     * 关联SQL
     */
    public String on;
    /**
     * 列
     */
    public List<CrudColumnAttr> columns;
    /**
     * 关联方式
     */
    public JoinType joinType = JoinType.LEFT;

    public CrudJoinAttr(Join join, Table table, List<CrudColumnAttr> columns ) {
        super(table);
        this.alias = join.alias();
        this.on = join.on();
        this.columns = columns;
    }




}
