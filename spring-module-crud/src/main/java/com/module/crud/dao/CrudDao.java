package com.module.crud.dao;

import com.module.crud.api.CrudDaoInterface;
import com.module.crud.provider.CrudInsertProvider;
import com.module.crud.entity.ObjectEntity;
import com.module.crud.provider.CrudSelectFindProvider;
import com.module.crud.provider.CrudUpdateProvider;
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
    @SelectProvider(type = CrudSelectFindProvider.class, method = "initialize")
    E find(@Param(__JavaAlias) E e);

    @Override
    @DeleteProvider
    Long delete(@Param(__JavaAlias)E e);

    @Override
    @UpdateProvider(type = CrudUpdateProvider.class, method = "initialize")
    public Long update(@Param(__JavaAlias)E e);

    public List<E> findList(E e);

    public E findById(E e);

    public <C> Long deleteBatchById(Collection<C> array, Class<?> c);


}
