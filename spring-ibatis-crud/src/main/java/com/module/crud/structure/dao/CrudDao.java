package com.module.crud.structure.dao;

import com.module.crud.framework.provider.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CrudDao<E> {
    public static final String __JavaAlias = "param";
    @InsertProvider(type = CrudInsertProvider.class, method = "initialize")
    E inset(@Param(__JavaAlias) E e);

    @SelectProvider(type = CrudFindProvider.class, method = "initialize")
    E find(@Param(__JavaAlias) E e);

    @DeleteProvider(type = CrudDeleteProvider.class, method = "initialize")
    Long delete(@Param(__JavaAlias)E e);

    @UpdateProvider(type = CrudUpdateProvider.class, method = "initialize")
    public Long update(@Param(__JavaAlias)E e);

    @SelectProvider(type = CrudFindListProvider.class, method = "initialize")
    public List<E> findList(@Param(__JavaAlias)E e);

    @SelectProvider(type = CrudFindByIdProvider.class, method = "initialize")
    public E findById(@Param(__JavaAlias)E e);

}
