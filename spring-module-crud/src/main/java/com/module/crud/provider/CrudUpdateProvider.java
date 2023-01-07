package com.module.crud.provider;

import com.module.crud.enumerate.PrimaryType;
import com.module.crud.enumerate.QueryType;
import jdk.nashorn.internal.objects.annotations.Where;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CrudUpdateProvider extends CrudRunTime {

    private List<String> SetList = new ArrayList<>();
    private List<String> PrimaryList = new ArrayList<>();

    @Override
    String Run() {
        InitSetList();
        InitPrimary();
        return new SQL() {
            {
                UPDATE(tableName);
                SET(Join(SetList));
                PrimaryList.forEach(e -> {
                    WHERE(e);
                });
            }
        }.toString();
    }


    private void InitSetList() {
        columnAttrs.forEach(columnAttr -> {
            if (isValueNotBlank(columnAttr)) {
                SetList.add(String.format("%s = %s", columnAttr.column, getSqlParam(columnAttr)));
            }
        });
    }

    private void InitPrimary() {
        Stream<ColumnAttr> columnAttrStream = columnAttrs.stream().filter(columnAttr -> columnAttr.isPrimary && columnAttr.primaryType.equals(PrimaryType.PRIMARY_KEY));
        if (columnAttrStream.count() == 0) {
            throw new RuntimeException(String.format("实体类%s找不到有效的主键", targetClass.getName()));
        }
        columnAttrStream.forEach(columnAttr -> {
            if (isValueNotBlank(columnAttr)) {
                if (isValueNotBlank(columnAttr)) {
                    PrimaryList.add(QueryType.EQUAL.getText(columnAttr.column, getSqlParam(columnAttr)));
                }
            }
        });
        if (PrimaryList.size() == 0) {
            throw new RuntimeException(String.format("实体类%s找不到有效的主键", targetClass.getName()));
        }
    }


}
