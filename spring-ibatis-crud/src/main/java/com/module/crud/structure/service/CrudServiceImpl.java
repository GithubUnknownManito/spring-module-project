package com.module.crud.structure.service;

import com.module.crud.structure.dao.CrudDao;
import com.module.crud.structure.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CrudServiceImpl <E extends BaseEntity, D extends CrudDao<E>> implements ICrudService<E,D> {

    @Autowired
    private D crudDao;
    @Override
    public E inset(E e) {
        return crudDao.inset(e);
    }

    @Override
    public E find(E e) {
        return crudDao.find(e);
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
        return crudDao.findById(e);
    }
}
