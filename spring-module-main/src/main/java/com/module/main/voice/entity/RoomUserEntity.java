package com.module.main.voice.entity;

import com.module.crud.structure.entity.BaseEntity;
import lombok.Data;

@Data
public class RoomUserEntity extends BaseEntity {
    Long id;
    Long account;
    String name;
    String room;
    int onLine;
}
