package com.module.crud.service;

import com.module.crud.dao.CrudDao;
import com.module.crud.entity.ObjectEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CrudServiceImpl <E extends ObjectEntity> implements ICrudService<E> {

    @Autowired
    private CrudDao crudDao;
    @Override
    public E inset(E e) {
        return (E) crudDao.inset(e);
    }

    @Override
    public E find(E e) {
        return (E) crudDao.find(e);
    }

    @Override
    public Long update(E e) {
        return crudDao.update(e);
    }

    @Override
    public Long delete(E e) {
        return crudDao.delete(e);
    }

    @Override
    public List<E> findList(E e) {
        return crudDao.findList(e);
    }

    @Override
    public E findById(E e) {
        return (E) crudDao.findById(e);
    }
}
