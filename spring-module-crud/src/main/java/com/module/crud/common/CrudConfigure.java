package com.module.crud.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrudConfigure {

    @Value("crud.logicalDeletion")
    private boolean logicalDeletion;


    public boolean isLogicalDeletion() {
        return logicalDeletion;
    }

    public void setLogicalDeletion(boolean logicalDeletion) {
        this.logicalDeletion = logicalDeletion;
    }
}
