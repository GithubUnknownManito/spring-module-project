package com.module.crud.service;

import com.module.crud.api.CrudServiceInterface;
import com.module.crud.entity.ObjectEntity;

import java.util.Arrays;

public interface ICrudService<E extends ObjectEntity> extends CrudServiceInterface<E> {
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
}
