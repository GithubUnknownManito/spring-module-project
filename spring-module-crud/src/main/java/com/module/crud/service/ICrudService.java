package com.module.crud.service;

import com.module.crud.api.CrudInterface;
import com.module.crud.dao.CrudDao;
import com.module.crud.entity.ObjectEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public interface ICrudService<E extends ObjectEntity> extends CrudInterface<E> {
    /**
     * 批量删除，根据主键
     * @param array 删除主键集合
     * @param c 主键 类型
     * @return 删除条数
     * @param <C> 主键 类型
     */
    default <C> Long DeleteBatchById(C[] array, Class<?> c) {
        return DeleteBatchById(Arrays.asList(array), c);
    }
    /**
     * 删除，根据条件
     * @param e 实体
     * @return 删除条数
     */
    default Long DeleteWhere(E e){
        e.DisablePrimary();
        return Delete(e);
    }
}
