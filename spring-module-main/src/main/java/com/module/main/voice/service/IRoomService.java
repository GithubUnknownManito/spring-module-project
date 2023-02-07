package com.module.main.voice.service;

import com.module.crud.structure.service.ICrudService;
import com.module.main.voice.dao.RoomDao;
import com.module.main.voice.entity.RoomEntity;

import java.util.List;

public interface IRoomService extends ICrudService<RoomEntity, RoomDao> {
    public List<RoomEntity> ddd();
}
