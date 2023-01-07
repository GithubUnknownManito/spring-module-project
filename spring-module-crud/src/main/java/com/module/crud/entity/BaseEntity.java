package com.module.crud.entity;

import com.module.crud.annotation.Column;
import com.module.crud.annotation.Table;
import lombok.Data;

import java.util.Date;

@Table(column = {
        @Column(column = "create_date", property = "createDate"),
        @Column(column = "create_by", property = "createBy"),
        @Column(column = "update_date", property = "updateDate"),
        @Column(column = "update_by", property = "updateBy"),
        @Column(column = "remark", property = "remark")
})
@Data
public class BaseEntity extends ObjectEntity {
    private Date createDate;
    private String createBy;
    @Column(column = "update_date", property = "updateDate")
    private Date updateDate;
    private String updateBy;
    private String remark;
}
