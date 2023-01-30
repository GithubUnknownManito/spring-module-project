package com.module.crud.entity;

import com.module.crud.annotation.*;
import lombok.Data;

import java.util.Date;

@Table(
        columns = {
                @Column(column = "create_date", property = "createDate"),
                @Column(column = "create_by", property = "createBy"),
                @Column(column = "update_date", property = "updateDate"),
                @Column(column = "update_by", property = "updateBy"),
                @Column(column = "remark", property = "remark"),
                @Column(column = "status", property = "status")
        },
        oneList = {
                @One(quote = ObjectEntity.class, property = "", alias = "b", on = "a.xxx = b.xxx",  columns = {
                        @Column(inheritance = BaseEntity.class)
                })
        },
        manyList = {
                @Many(quote = ObjectEntity.class, property = "", alias = "c", on = "", columns = {
                        @Column(inheritance = ObjectEntity.class)
                })
        },
        joinList = {
                @Join(quote = ObjectEntity.class, alias = "", on = "", columns = {
                        @Column(inheritance = ObjectEntity.class)
                })
        },
        alias = "a"
)
@Data
public class BaseEntity extends ObjectEntity {
    private Date createDate;
    private String createBy;
    private Date updateDate;
    private String updateBy;
    private String remark;
    private int status;
}
