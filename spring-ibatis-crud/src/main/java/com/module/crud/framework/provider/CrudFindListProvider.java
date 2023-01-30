package com.module.crud.framework.provider;

import com.module.crud.framework.core.CrudProviderInterface;
import org.apache.ibatis.jdbc.SQL;

import java.util.concurrent.atomic.AtomicInteger;

public class CrudFindListProvider extends CrudFindProvider implements CrudProviderInterface {
    @Override
    public String run() {
        return new SQL(){
            {
                AtomicInteger where = new AtomicInteger();
                SELECT(columnsSql());
                FROM(tableNameSelect());
                if(isJoin()){
                    LEFT_OUTER_JOIN(joinsSql());
                }
                where().forEach(column-> {
                    if(where.get() != 0){
                        switch(column.queryModel){
                            case AND:{
                                AND();
                                break;
                            }
                            case OR:{
                                OR();
                                break;
                            }
                        }

                    }
                    WHERE(column.where());
                    where.getAndIncrement();
                });
                order().forEach(column -> {
                    switch (column.sort) {
                        case ASC:{
                            ORDER_BY(String.format("%s ASC", column.columnSql()));
                            break;
                        }
                        case DESC:{
                            ORDER_BY(String.format("%s DESC", column.columnSql()));
                            break;
                        }
                    }
                });
            }
        }.toString();
    }
}
