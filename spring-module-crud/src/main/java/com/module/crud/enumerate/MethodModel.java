package com.module.crud.enumerate;

public enum MethodModel {
    GET("get"),
    SET("set");

    private String text = "";
    MethodModel(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
