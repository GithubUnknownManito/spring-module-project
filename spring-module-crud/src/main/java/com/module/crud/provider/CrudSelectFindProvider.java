//package com.module.crud.provider;
//
//import com.module.crud.enumerate.ColumnSort;
//import com.module.crud.enumerate.QueryModel;
//import com.module.crud.enumerate.QueryType;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.jdbc.SQL;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class CrudSelectFindProvider extends CrudRunTime {
//    private boolean isWhereFirstAdd = false;
//    private List<String> columnList = new ArrayList<>();
//    @Override
//    String Run() {
//        InitColumns();
//        return new SQL() {{
//            SELECT(String.format("TOP 1 %s", columnList));
//            FROM(getSqlTableName());
//
//            columnAttrs.forEach(column -> {
//                if(column.query.equals(QueryType.UNSET)){
//                    return;
//                }
//                if (isValueNotBlank(column)) {
//                    if(isWhereFirstAdd){
//                        if(column.queryModel.equals(QueryModel.AND)){
//                            AND();
//                        }
//
//                        if(column.queryModel.equals(QueryModel.OR)){
//                            OR();
//                        }
//                    }
//                    WHERE(GetWhere(column));
//                    isWhereFirstAdd = true;
//                }
//                if(!column.sort.equals(ColumnSort.UNSET)){
//                    ORDER_BY(getSqlColumnAlias(column));
//                }
//            });
//        }}.toString();
//    }
//
//    private void InitColumns(){
//        columnList = columnAttrs.stream().map(column -> getSqlSelectColumnAlias(column)).collect(Collectors.toList());
//    }
//
//    private String GetWhere(CrudColumnAttr column){
//        return column.query.getText(column.column, getSqlParam(column));
//    }
//
//
//
//}
