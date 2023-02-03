package com.module.crud.structure.service;

import com.module.crud.structure.dao.CrudDao;
import com.module.crud.structure.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CrudServiceImpl <E extends BaseEntity, D extends CrudDao<E>> implements ICrudService<E,D> {

    @Autowired
    public D dao;
    @Override
    public E inset(E e) {
        return dao.inset(e);
    }

    @Override
    public E find(E e) {
        return dao.find(e);
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
        return dao.findById(e);
    }
}
