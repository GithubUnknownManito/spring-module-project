package com.module.crud.entity;

import com.module.crud.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

@Table(
        columns = {
                @Column(column = "id", property = "id", isPrimary = true),
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
