package com.module.crud.enumerate;

public enum PrimaryGenerate {
    /**
     * 不处理
     */
    UNSET,
    /**
     * 生成UUID
     */
    UUID,
    /**
     * 使用时间戳+随机数
     */
    STAMP,

}
