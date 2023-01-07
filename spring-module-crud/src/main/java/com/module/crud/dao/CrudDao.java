package com.module.crud.dao;

import com.module.crud.provider.CrudInsertProvider;
import com.module.crud.api.CrudInterface;
import com.module.crud.entity.ObjectEntity;
import com.module.crud.provider.CrudSelectFindProvider;
import com.module.crud.provider.CrudUpdateProvider;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CrudDao<E extends ObjectEntity> extends CrudInterface<E> {

    public static final String __JavaAlias = "param";
    @Override
    @InsertProvider(type = CrudInsertProvider.class, method = "initialize")
    E Inset(@Param(__JavaAlias) E e);

    @Override
    @SelectProvider(type = CrudSelectFindProvider.class, method = "initialize")
    E Find(@Param(__JavaAlias) E e);

    @Override
    @DeleteProvider
    Long Delete(@Param(__JavaAlias)E e);

    @Override
    @UpdateProvider(type = CrudUpdateProvider.class, method = "initialize")
    public Long Update(@Param(__JavaAlias)E e);

}
