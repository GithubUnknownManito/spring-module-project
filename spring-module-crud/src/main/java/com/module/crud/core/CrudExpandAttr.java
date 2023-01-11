package com.module.crud.core;

import com.module.crud.annotation.Expand;
import com.module.crud.enumerate.ExpandType;

import java.lang.reflect.Field;

public class CrudExpandAttr {
    public ExpandType name;
    public String param;
    public Field field;
    public Object data;

    public CrudExpandAttr(Expand expand, Field field, Object data) {
        this.name = expand.value();
        this.param = expand.param();
        this.field = field;
        this.data = data;
    }
}
