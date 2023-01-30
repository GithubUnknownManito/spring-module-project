package com.module.crud.framework.sql;

import com.module.crud.dao.CrudDao;
import org.apache.logging.log4j.util.Strings;

import java.util.*;

public class CrudSeWhere {
    private CrudSeWhere crudSeWhere = this;
    private List<String> where = new ArrayList<>();
    private Map<String,Object> data = new HashMap<>();
    private String currColumn;
    private int dataDeep = 0;

    public CrudSeWhere(Map<String, Object> data) {
        this.data = data;
    }

    private WhereQueryField whereQueryField = new WhereQueryField() {
        @Override
        public WhereQueryType addColumn(String column) {
//            where.add(currColumn = column);
            dataDeep++;
            return whereQueryType;
        }
    };

    private WhereQueryType whereQueryType = new WhereQueryType() {
        @Override
        public CrudWhere In(List value) {
            where.add(String.format("%s IN (%s)", currColumn, Strings.join(putList(value), ',')));
            return crudSeWhere;
        }

        @Override
        public CrudWhere Equal(Object value) {
            where.add(String.format("%s = %s", currColumn, put(value));
            return null;
        }

        @Override
        public CrudWhere Like(Object value, boolean isBefore, boolean isAfter) {
            return where.add(String.format("%s = %s", currColumn, put(value));;
        }

        @Override
        public CrudWhere NotIn(List value) {
            return null;
        }

        @Override
        public CrudWhere NotEqual(Object value) {
            return null;
        }

        @Override
        public CrudWhere NotLike(Object value, boolean isBefore, boolean isAfter) {
            return null;
        }

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
    };

    public WhereQueryField and(){
        where.add("AND");
        return whereQueryField;
    }

    public WhereQueryField or(){
        where.add("OR");
        return whereQueryField;
    }


    public interface WhereQueryField {
        public WhereQueryType addColumn(String column);
    }

    public interface WhereQueryType<T extends CrudWhere> {
        T In(List<?> value);
        T Equal(Object value);
        T Like(Object value,boolean isBefore, boolean isAfter);
        T NotIn(List<?> value);
        T NotEqual(Object value);
        T NotLike(Object value,boolean isBefore, boolean isAfter);
        default T Like(Object value) {
            return Like(value, true, true);
        }
        default T BeforeLike(Object value) {
            return Like(value, true, false);
        }
        default T AfterLike(Object value) {
            return Like(value, false, true);
        }
        default T BeforeNotLike(Object value) {
            return NotLike(value, true, false);
        }
        default T AfterNotLike(Object value) {
            return NotLike(value, false, true);
        }

    }

}
