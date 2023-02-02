package com.module.crud.framework.provider;

import com.module.crud.enumerate.ColumnSort;
import com.module.crud.enumerate.QueryModel;
import com.module.crud.enumerate.QueryType;
import com.module.crud.framework.core.CrudProviderColumn;
import com.module.crud.framework.core.CrudProviderInterface;
import com.module.crud.framework.core.CrudProviderJoin;
import com.module.crud.framework.core.CrudProviderRunTime;
import com.module.crud.framework.utils.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrudFindProvider extends CrudProviderRunTime implements CrudProviderInterface {
    @Override
    public String run() {
        StringBuilder sb =new StringBuilder();
        sb.append(new SQL(){
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

                expandWhere().forEach(expand -> {
                    expand.forEach(_where -> {
                        if(where().count() != 0){
                            if(_where.model.equals("AND")){
                                AND();
                            } else if ((_where.model.equals("OR"))){
                                OR();
                            }
                        }
                        WHERE(String.format("%s %s", _where.column, _where.model));
                    });
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
        });
        sb.insert(5, " TOP 1 ");
        return sb.toString();
    }

    @Override
    public Stream<CrudProviderColumn> columns() {
        List<CrudProviderColumn> tmp = super.columns().collect(Collectors.toList());
        if(isJoin()){
            List<CrudProviderJoin> joins = joins().collect(Collectors.toList());
            for (int i = 0; i < joins.size(); i++) {
                CrudProviderJoin providerJoin = joins.get(i);
                for (int j = 0; j < providerJoin.columns().size(); j++) {
                    CrudProviderColumn joinProviderColumn = providerJoin.columns().get(j);
                    if(!tmp.stream().anyMatch(crudProviderColumn -> crudProviderColumn.property.equals(joinProviderColumn.property))){
                        tmp.add(joinProviderColumn);
                    }
                }
            }
        }
        return tmp.stream();
    }

    public String tableNameSelect(){
        if(StringUtils.isNotBlank(this.alias)){
            return String.format("%s AS %s", this.tableName, this.alias);
        }
        return this.tableName;
    }

    public String[] columnsSql(){
        return columns().map(column -> String.format("%s.%s AS %s",  column.getTableName(),  column.column, column.property)).toArray(String[]::new);
    }

    public String[] joinsSql(){
        return joins().map(join -> String.format("%s AS %s ON %s", join.tableName, join.alias, join.on)).toArray(String[]::new);
    }

    public Stream<CrudProviderColumn> where(){
        return columns().filter(column-> !QueryType.UNSET.equals(column.query) || column.isPrimary == true );
    }

    public Stream<CrudProviderColumn> order(){
        return columns().filter(column-> !ColumnSort.UNSET.equals(column.sort));
    }
}
