package com.module.crud.service;

import com.github.pagehelper.PageInfo;
import com.module.crud.api.CrudServiceInterface;
import com.module.crud.entity.ObjectEntity;

import java.util.Arrays;

public interface ICrudService<E extends ObjectEntity> extends CrudServiceInterface<E> {
    default PageInfo<E> findPage(E e){
        return new PageInfo<>(findList(e));
    }
}
