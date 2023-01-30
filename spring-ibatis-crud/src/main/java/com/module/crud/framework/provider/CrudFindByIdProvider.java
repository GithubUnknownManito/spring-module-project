package com.module.crud.framework.provider;

import com.module.crud.enumerate.QueryType;
import com.module.crud.framework.core.CrudProviderColumn;
import com.module.crud.framework.core.CrudProviderInterface;
import org.apache.ibatis.jdbc.SQL;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class CrudFindByIdProvider extends CrudFindProvider implements CrudProviderInterface {

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
            }
        }.toString();
    }

    public Stream<CrudProviderColumn> where(){
        return columns().filter(column-> column.isPrimary);
    }
}
