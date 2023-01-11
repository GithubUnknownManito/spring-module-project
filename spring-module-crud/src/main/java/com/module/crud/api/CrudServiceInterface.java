package com.module.crud.api;

import com.github.pagehelper.PageInfo;

public interface CrudServiceInterface<E> extends CrudDaoInterface<E> {
    /**
     * 查找列表，对存在的字段进行查询
     * @param e
     * @return
     */
    public PageInfo<E> findPage(E e);
}
