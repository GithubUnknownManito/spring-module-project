package com.module.crud.enumerate;

public enum Pattern {
    /**
     * 注解模式<br/>
     * 只读取类和字段的注解，进行生成
     */
    ANNOTATION,
    /**
     * 字段模式<br/>
     * 无视注解，使用字段进行生成
     */
    FIELD
}
