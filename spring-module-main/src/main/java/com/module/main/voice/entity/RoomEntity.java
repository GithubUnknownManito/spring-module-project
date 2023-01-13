package com.module.main.voice.entity;

import com.module.crud.annotation.Column;
import com.module.crud.annotation.Table;
import com.module.crud.entity.BaseEntity;
import com.module.crud.enumerate.QueryType;

import java.util.Date;

@Table(name = "internal_room", columns = {
        @Column(column = "id", property = "id", isPrimary = true),
        @Column(column = "name", property = "name", query = QueryType.EQUAL),
        @Column(column = "create_date", property = "createDate"),
        @Column(column = "create_by", property = "createBy"),
        @Column(column = "update_date", property = "updateDate"),
        @Column(column = "update_by", property = "updateBy"),
        @Column(column = "remark", property = "remark"),
        @Column(inheritance = BaseEntity.class)
}, inheritance = BaseEntity.class)
public class RoomEntity extends BaseEntity {
    private Long id;
    private String name;

    public RoomEntity() {
    }

    public RoomEntity(Long id) {
        this.id = id;
    }
}
