package com.module.crud.api;

import com.github.pagehelper.PageInfo;
import com.module.crud.structure.entity.BaseEntity;

public interface CrudServiceInterface<E extends BaseEntity> extends CrudDaoInterface<E> {
    /**
     * 查找列表，对存在的字段进行查询
     * @param e
     * @return
     */
    public PageInfo<E> findPage(E e);
}
