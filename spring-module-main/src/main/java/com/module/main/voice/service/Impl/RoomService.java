package com.module.main.voice.service.Impl;

import com.module.crud.structure.service.CrudServiceImpl;
import com.module.main.voice.dao.RoomDao;
import com.module.main.voice.entity.RoomEntity;
import com.module.main.voice.service.IRoomService;
import org.springframework.stereotype.Service;

@Service
public class RoomService extends CrudServiceImpl<RoomEntity, RoomDao> implements IRoomService {

}
