package com.module.main.voice.dao;

import com.module.crud.structure.dao.CrudDao;
import com.module.main.voice.entity.RoomEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoomDao extends CrudDao<RoomEntity> {
    List<RoomEntity> dddd(RoomEntity entity);
}
