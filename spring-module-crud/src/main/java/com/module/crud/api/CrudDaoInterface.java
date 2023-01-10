package com.module.crud.api;

import java.util.Collection;
import java.util.List;

public interface CrudDaoInterface<E> {
    /**
     * 查找，如果多条返回第一条
     * @param e 实体
     * @return
     */
    public E find(E e);

    /**
     * 查找列表，对存在的字段进行查询
     * @param e
     * @return
     */
    public List<E> findList(E e);

    /**
     * 查找，根据主键查找
     * @param e 实体
     * @return 实体
     */
    public E findById(E e);

    /**
     * 新增
     * @param e 实体
     * @return
     */
    public E inset(E e);

    /**
     * 更新，根据ID查询
     * @param e 实体
     * @return
     */
    public Long update(E e);

    /**
     * 删除，根据主键删除，包含主键外键
     * @param e
     * @return
     */
    public Long delete(E e);

    /**
     * 批量删除，根据主键
     * @param array 删除主键集合
     * @param c 主键 类型
     * @return 删除条数
     * @param <C> 主键 类型
     */
    public <C> Long deleteBatchById(Collection<C> array, Class<?> c);
}
