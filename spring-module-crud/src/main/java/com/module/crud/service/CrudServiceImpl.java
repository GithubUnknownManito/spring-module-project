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
    public E Inset(E e) {
        return (E) dao.Inset(e);
    }

    @Override
    public E Find(E e) {
        return (E) dao.Find(e);
    }

    @Override
    public Long Update(E e) {
        return dao.Update(e);
    }

    @Override
    public Long Delete(E e) {
        return dao.Delete(e);
    }

    @Override
    public List<E> FindList(E e) {
        return dao.FindList(e);
    }

    @Override
    public E FindById(E e) {
        BaseEntity baseEntity =new BaseEntity();
        return (E) dao.FindById(e);
    }

    @Override
    public Long DeleteById(E e) {
        return dao.DeleteById(e);
    }

    @Override
    public <C> Long DeleteBatchById(Collection<C> array, Class<?> c) {
        return dao.DeleteBatchById(array, c);
    }
}
