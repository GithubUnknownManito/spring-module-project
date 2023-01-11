package com.module.crud.dao;

import com.module.crud.api.CrudDaoInterface;
import com.module.crud.provider.*;
import com.module.crud.entity.ObjectEntity;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;

@Mapper
public interface CrudDao<E extends ObjectEntity> extends CrudDaoInterface<E> {

    public static final String __JavaAlias = "param";
    @Override
    @InsertProvider(type = CrudInsertProvider.class, method = "initialize")
    E inset(@Param(__JavaAlias) E e);

    @Override
    @SelectProvider(type = CrudFindProvider.class, method = "initialize")
    E find(@Param(__JavaAlias) E e);

    @Override
    @DeleteProvider(type = CrudDeleteProvider.class, method = "initialize")
    Long delete(@Param(__JavaAlias)E e);

    @Override
    @UpdateProvider(type = CrudUpdateProvider.class, method = "initialize")
    public Long update(@Param(__JavaAlias)E e);

    @Override
    @UpdateProvider(type = CrudFindListProvider.class, method = "initialize")
    public List<E> findList(E e);

    @UpdateProvider(type = CrudFindByIdProvider.class, method = "initialize")
    public E findById(E e);

}
