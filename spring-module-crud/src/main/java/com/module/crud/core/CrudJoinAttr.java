package com.module.crud.core;

import com.module.crud.annotation.Join;
import com.module.crud.annotation.Many;
import com.module.crud.annotation.One;
import com.module.crud.enumerate.JoinType;

import java.util.List;

public class CrudJoinAttr {
    /**
     * 表明
     */
    public String name;
    /**
     * 属性（字段）
     */
    public String property;
    /**
     * 别称
     */
    public String alias;
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

    public CrudJoinAttr(Join join, String name, List<CrudColumnAttr> columns ) {
        this.name = name;
        this.alias = join.alias();
        this.on = join.on();
        this.joinType = join.joinType();
        this.columns = columns;
    }

    public CrudJoinAttr(One one, String name, List<CrudColumnAttr> columns) {
        this.name = name;
        this.property = one.property();
        this.alias = one.alias();
        this.on = one.on();
        this.columns = columns;
    }

    public CrudJoinAttr(Many many, String name, List<CrudColumnAttr> columns) {
        this.name = name;
        this.property = many.property();
        this.alias = many.alias();
        this.on = many.on();
        this.columns = columns;
    }




}
