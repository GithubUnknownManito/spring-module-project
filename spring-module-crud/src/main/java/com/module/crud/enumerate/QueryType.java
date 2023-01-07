package com.module.crud.enumerate;

public enum QueryType {
    EQUAL("$ = ?"),
    LIKE("$ LIKE CONCAT('%', ?, '%')"),
    BEFORE_LIKE("$ LIKE CONCAT('%', ?)"),
    AFTER_LIKE("$ LIKE CONCAT(?, '%')"),
    UNSET("");

    private String text;

    QueryType(String text) {
        this.text = text;
    }

    public String getText(String column, String value) {
        return text.replace("$", column).replace("?", value);
    }
}
