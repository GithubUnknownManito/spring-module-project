package com.module.crud;

import com.module.crud.annotation.*;
import com.module.crud.structure.entity.BaseEntity;
import lombok.Data;

@Table(
        name = "test",
        columns = {
                @Column(column = "id", property = "id", isPrimary = true),
                @Column(inheritance = BaseEntity.class),
        },
        joinList = {
                @Join(quote = test1Rntity.class, alias = "b", on = "a.id = b.id", columns = {
                        @Column(inheritance = test1Rntity.class)
                })
        },
        alias = "a",
        inheritance = test1Rntity.class
)
@Data
public class testEntity extends test1Rntity {
    private String id;
}
