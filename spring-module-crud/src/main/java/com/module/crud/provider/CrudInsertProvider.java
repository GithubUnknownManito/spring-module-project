//package com.module.crud.provider;
//
//import com.module.crud.dao.CrudDao;
//import com.module.crud.enumerate.PrimaryType;
//import com.module.crud.enumerate.QueryType;
//import org.apache.ibatis.jdbc.SQL;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class CrudInsertProvider extends CrudRunTime {
//
//    private List<String> columnList = new ArrayList<>();
//    private List<String> valueList = new ArrayList<>();
//    @Override
//    String Run() {
//        InitPrimary();
//        InitColumns();
//        InitValues();
//        return new SQL() {{
//            INSERT_INTO(tableName);
//            VALUES(Join(columnList), Join(valueList));
//        }}.toString();
//    }
//
//    private void InitColumns(){
//        columnList = columnAttrs.stream().map(column -> column.column).collect(Collectors.toList());
//    }
//
//    private void InitValues(){
//        valueList =  columnAttrs.stream().map(column -> getSqlParam(column)).collect(Collectors.toList());
//    }
//
//    private void InitPrimary() {
//        Stream<CrudColumnAttr> PrimaryList = columnAttrs.stream().filter(column -> column.isPrimary);
//        PrimaryList.forEach(column -> {
//            Object PrimaryKey = GeneratePrimary(column);
//            if (Objects.nonNull(PrimaryKey)) {
//                setValue(column, PrimaryKey);
//            }
//        });
//    }
//}
