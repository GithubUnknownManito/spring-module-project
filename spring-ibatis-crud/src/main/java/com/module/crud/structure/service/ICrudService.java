package com.module.crud.structure.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.module.crud.api.CrudServiceInterface;
import com.module.crud.structure.dao.CrudDao;
import com.module.crud.structure.entity.BaseEntity;

import java.util.Objects;

public interface ICrudService<E extends BaseEntity, D extends CrudDao<E>> extends CrudServiceInterface<E> {
    default PageInfo<E> findPage(E e){
        if(Objects.isNull(e.getPage())){
            e.setPage(0,20);
        }
        PageHelper.startPage(e.getPage().getPageNum(), e.getPage().getPageSize());
        return new PageInfo<E>(findList(e));
    }
}
