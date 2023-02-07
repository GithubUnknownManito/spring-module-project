package com.module.main.voice.service.Impl;

import com.module.crud.structure.service.CrudServiceImpl;
import com.module.main.voice.dao.RoomDao;
import com.module.main.voice.entity.RoomEntity;
import com.module.main.voice.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService extends CrudServiceImpl<RoomEntity, RoomDao> implements IRoomService {
    @Autowired
    private RoomDao dao;
    @Override
    public List<RoomEntity> ddd() {
        return dao.dddd(new RoomEntity());
    }
}
