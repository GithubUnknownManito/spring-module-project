package com.module.crud.structure.entity;

import com.module.crud.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

@Table(
        columns = {
                @Column(column = "create_date", property = "createDate"),
                @Column(column = "create_by", property = "createBy"),
                @Column(column = "update_date", property = "updateDate"),
                @Column(column = "update_by", property = "updateBy"),
                @Column(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
                @Column(column = "status", property = "status")
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
    private int status = 0;
}
