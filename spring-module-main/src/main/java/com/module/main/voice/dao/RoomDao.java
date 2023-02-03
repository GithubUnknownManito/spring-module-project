package com.module.main.voice.dao;

import com.module.crud.structure.dao.CrudDao;
import com.module.main.voice.entity.RoomEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoomDao extends CrudDao<RoomEntity> {

}
