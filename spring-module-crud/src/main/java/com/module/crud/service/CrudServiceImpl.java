package com.module.crud.service;

import com.module.crud.dao.CrudDao;
import com.module.crud.entity.BaseEntity;
import com.module.crud.entity.ObjectEntity;
import com.module.crud.service.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

public class CrudServiceImpl <E extends ObjectEntity> implements ICrudService<E> {

    @Autowired
    private CrudDao dao;
    @Override
    public E inset(E e) {
        return (E) dao.inset(e);
    }

    @Override
    public E find(E e) {
        return (E) dao.find(e);
    }

    @Override
    public Long update(E e) {
        return dao.update(e);
    }

    @Override
    public Long delete(E e) {
        return dao.delete(e);
    }

    @Override
    public List<E> findList(E e) {
        return dao.findList(e);
    }

    @Override
    public E findById(E e) {
        BaseEntity baseEntity =new BaseEntity();
        return (E) dao.findById(e);
    }

    @Override
    public <C> Long deleteBatchById(Collection<C> array, Class<?> c) {
        return dao.deleteBatchById(array, c);
    }
}
