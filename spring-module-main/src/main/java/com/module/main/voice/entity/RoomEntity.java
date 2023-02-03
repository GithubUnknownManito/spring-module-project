package com.module.main.voice.entity;

import com.module.crud.annotation.Column;
import com.module.crud.annotation.Table;
import com.module.crud.structure.entity.BaseEntity;
import com.module.crud.enumerate.QueryType;
import lombok.Data;

@Table(name = "internal_room", alias = "a", columns = {
        @Column(column = "id", property = "id", isPrimary = true),
        @Column(column = "name", property = "name", query = QueryType.EQUAL),
        @Column(inheritance = BaseEntity.class)
}, inheritance = BaseEntity.class)
@Data
public class RoomEntity extends BaseEntity {
    private Long id;
    private String name;

    public RoomEntity() {
    }

    public RoomEntity(Long id) {
        this.id = id;
    }
}
