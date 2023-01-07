package com.module.crud.api;

import com.module.crud.entity.ObjectEntity;

import java.util.Collection;
import java.util.List;

public interface CrudInterface<E extends ObjectEntity> {
    /**
     * 新增
     * @param e 实体
     * @return
     */
    public E Inset(E e);

    /**
     * 查找，如果多条返回第一条
     * @param e 实体
     * @return
     */
    public E Find(E e);

    /**
     * 更新，根据ID查询
     * @param e 实体
     * @return
     */
    public Long Update(E e);

    /**
     * 删除，根据主键删除，包含主键外键
     * @param e
     * @return
     */
    public Long Delete(E e);

    /**
     * 查找列表，对存在的字段进行查询
     * @param e
     * @return
     */
    public List<E> FindList(E e);

    /**
     * 查找，根据主键查找
     * @param e 实体
     * @return 实体
     */
    public E FindById(E e);

    /**
     * 删除，根据主键
     * @param e 实体
     * @return 删除条数
     */
    public Long DeleteById(E e);

    /**
     * 批量删除，根据主键
     * @param array 删除主键集合
     * @param c 主键 类型
     * @return 删除条数
     * @param <C> 主键 类型
     */
    public <C> Long DeleteBatchById(Collection<C> array, Class<?> c);


}
