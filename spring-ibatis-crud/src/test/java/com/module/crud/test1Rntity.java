package com.module.crud;

import com.module.crud.annotation.Column;
import com.module.crud.annotation.Table;
import com.module.crud.structure.entity.BaseEntity;
import lombok.Data;

@Table(
        name = "test1Rntity",
        columns = {
                @Column(column = "id", property = "id"),
                @Column(column = "name", property = "name"),
                @Column(column = "isOK", property = "isOK"),
                @Column(inheritance = BaseEntity.class),
        },
        alias = "a",
        inheritance = BaseEntity.class
)
@Data
public class test1Rntity extends BaseEntity {
    private String name;
    private boolean isOK;
    private String id;
}
