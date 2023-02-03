package com.module.crud.framework.sql;

import com.module.crud.structure.dao.CrudDao;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

public class CrudSqlWhereExtension implements Serializable {
    private CrudWhereColumn currColumn;
    private List<CrudWhereColumn> crudWhereColumns = new ArrayList<>();
    private Map<String, Object> data = new HashMap<>();
    private long dataDeep;

    public void forEach(Consumer<CrudWhereColumn> action) {
        Objects.requireNonNull(action);
        for (CrudWhereColumn t : crudWhereColumns) {
            action.accept(t);
        }
    }

    public CrudSqlWhereExtension(Map<String, Object> data) {
        this.data = data;
    }

    public CrudSqlWhereColumn and(){
        currColumn = new CrudWhereColumn();
        currColumn.model = "AND";
        return crudSqlWhereColumn;
    }

    public CrudSqlWhereColumn or(){
        currColumn = new CrudWhereColumn();
        currColumn.model = "OR";
        return crudSqlWhereColumn;
    }


    private CrudSqlWhereColumn crudSqlWhereColumn = new CrudSqlWhereColumn(){

        @Override
        public CrudSqlQueryType addColumn(String column) {
            currColumn.column = column;
            return crudSqlQueryType;
        }
    };

    private String put(Object value){
        String key = String.format("%s_%s",currColumn, dataDeep);
        data.put(key, value);
        return String.format("#{%s.$param.%s}", CrudDao.__JavaAlias, key);
    }

    private List<String> putList(List<?> value){
        List<String> array = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            String key = String.format("%s_%s%s",currColumn, dataDeep, i);
            data.put(key, value);
            array.add(String.format("#{%s.$param.%s}", CrudDao.__JavaAlias, key));
        }
        return array;
    }

    private CrudSqlWhereExtension end(){
        crudWhereColumns.add(currColumn);
        return this;
    }

    private CrudSqlQueryType crudSqlQueryType = new CrudSqlQueryType() {
        @Override
        public CrudSqlWhereExtension In(List<?> value) {
            currColumn.where = String.format("%s IN (%s)", currColumn, Strings.join(putList(value), ','));
            return end();
        }

        @Override
        public CrudSqlWhereExtension Equal(Object value) {
            currColumn.where =  String.format("%s = %s", currColumn, put(value));
            return end();
        }

        @Override
        public CrudSqlWhereExtension Like(Object value, boolean isBefore, boolean isAfter) {
            String format = "%s LIKE CONCAT(";
            if(isBefore){
                format+="'%%',";
            }
            format+="%s,";
            if(isAfter){
                format+="'%%'";
            }
            currColumn.where =  String.format(format, currColumn, put(value));
            return end();
        }

        @Override
        public CrudSqlWhereExtension NotIn(List<?> value) {
            currColumn.where = String.format("%s NOT IN (%s)", currColumn, Strings.join(putList(value), ','));
            return end();
        }

        @Override
        public CrudSqlWhereExtension NotEqual(Object value) {
            currColumn.where =  String.format("%s != %s", currColumn, put(value));
            return end();
        }

        @Override
        public CrudSqlWhereExtension NotLike(Object value, boolean isBefore, boolean isAfter) {
            String format = "%s NOT LIKE CONCAT(";
            if(isBefore){
                format+="'%%',";
            }
            format+="%s,";
            if(isAfter){
                format+="'%%'";
            }
            currColumn.where =  String.format(format, currColumn, put(value));
            return end();
        }
    };
    public interface CrudSqlWhereColumn {
        public CrudSqlQueryType addColumn(String column);
    }
    public interface CrudSqlQueryType {
        CrudSqlWhereExtension In(List<?> value);
        CrudSqlWhereExtension Equal(Object value);
        CrudSqlWhereExtension Like(Object value,boolean isBefore, boolean isAfter);
        CrudSqlWhereExtension NotIn(List<?> value);
        CrudSqlWhereExtension NotEqual(Object value);
        CrudSqlWhereExtension NotLike(Object value,boolean isBefore, boolean isAfter);
        default CrudSqlWhereExtension Like(Object value) {
            return Like(value, true, true);
        }
        default CrudSqlWhereExtension BeforeLike(Object value) {
            return Like(value, true, false);
        }
        default CrudSqlWhereExtension AfterLike(Object value) {
            return Like(value, false, true);
        }
        default CrudSqlWhereExtension BeforeNotLike(Object value) {
            return NotLike(value, true, false);
        }
        default CrudSqlWhereExtension AfterNotLike(Object value) {
            return NotLike(value, false, true);
        }
    }

    public class CrudWhereColumn{
        public String model;
        public String column;
        public String where;
    }
}
