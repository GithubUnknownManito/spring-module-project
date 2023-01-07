package com.module.crud.enumerate;

public enum ColumnSort {
    DESC("DESC"),
    ASC("ASC"),
    UNSET("");

    private String sort;
    ColumnSort(String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return sort;
    }
}
