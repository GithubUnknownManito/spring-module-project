package com.module.crud.sql;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Target;
import java.util.*;
import java.util.function.Function;

public class CrudWhere {
    private List<String> where;
    private Map<String, Object> data;
    private WhereQueryField whereQueryField;
    private WhereQueryType whereQueryType;
    private CrudWhere crudWhere;
    private int ColumnIndex = 0;
    private int WhereIndex = 0;
    private boolean isEnd = false;

    public CrudWhere(Map<String, Object> data) {
        this.data = data;
        this.where = new ArrayList<>();
        this.crudWhere = this;
        this.iniWhereQueryType();
        this.initWhereQueryField();
    }

    private void iniWhereQueryType(){
        this.whereQueryType = new WhereQueryType<CrudWhere>() {
            @Override
            public CrudWhere In(List<?> value) {
                where.add(String.format("IN (%s)", InEE(value)));
                return crudWhere;
            }

            @Override
            public CrudWhere Equal(Object value) {
                String key = where.get(where.size() - 1);
                Objects.requireNonNull(value, String.format("扩展查询%s进行Equal的参数为空", key));
                where.add(String.format("= (%s)", getParam(value, null)));
                return crudWhere;
            }

            @Override
            public CrudWhere Like(Object value, boolean isBefore, boolean isAfter) {
                Objects.requireNonNull(value, String.format("扩展查询%s进行Like的参数为空", where.get(where.size() - 1)));
                StringBuilder sb =new StringBuilder("LIKE CONCAT(");
                return LikeEE(value, isBefore, isAfter, sb);
            }

            @Override
            public CrudWhere NotIn(List<?> value) {
                where.add(String.format("NOT IN (%s)",  InEE(value)));
                return crudWhere;
            }

            private String InEE(List<?> value) {
                String key = where.get(where.size() - 1);

                Objects.requireNonNull(value, String.format("扩展查询%s进行In的参数为空", key));
                StringBuilder sb =new StringBuilder();
                if(value.size() == 0){
                    throw new RuntimeException(String.format("扩展查询%s进行In的参数长度为0", key));
                }

                for (int i = 0; i < value.size(); i++) {
                    if(sb.length() > 0){
                        sb.append(",");
                    }
                    sb.append(getParam(value.get(i), i));
                }
                return sb.toString();
            }

            @Override
            public CrudWhere NotEqual(Object value) {
                String key = where.get(where.size() - 1);
                Objects.requireNonNull(value, String.format("扩展查询%s进行Equal的参数为空", key));
                where.add(String.format("!= (%s)", getParam(value, null)));
                return crudWhere;
            }

            @Override
            public CrudWhere NotLike(Object value, boolean isBefore, boolean isAfter) {
                Objects.requireNonNull(value, String.format("扩展查询%s进行Like的参数为空", where.get(where.size() - 1)));
                StringBuilder sb =new StringBuilder("NOT LIKE CONCAT(");
                return LikeEE(value, isBefore, isAfter, sb);
            }

            private CrudWhere LikeEE(Object value, boolean isBefore, boolean isAfter, StringBuilder sb) {
                if(isBefore){
                    sb.append("'%%',");
                }
                sb.append("%s,");
                if(isAfter){
                    sb.append("'%%')");
                }
                where.add(String.format(sb.toString(), getParam(value, null)));
                return crudWhere;
            }

            private String getParam(Object value, Integer index){
                String colName = where.get(where.size() - 1);
                String key = Objects.isNull(index) ? colName : String.format("%s_%s", colName, index);
                data.put(key, value);
                WhereIndex ++;
                return String.format("$.%s", key);
            }

        };
    }

    public WhereQueryField And() {
        where.add("AND");
        return whereQueryField;
    }

    public WhereQueryField Or() {
        where.add("OR");
        return whereQueryField;
    }

    public WhereQueryField UnSet(){
        return whereQueryField;
    }

    public void End() {
        isEnd = true;
    }

    private void initWhereQueryField(){
        this.whereQueryField = new WhereQueryField() {
            @Override
            public WhereQueryType addField(String col) {
                if(StringUtils.isNotBlank(col)){
                    throw new NullPointerException("扩展查询字段为空");
                }
                where.add(col);
                ColumnIndex ++ ;
                return whereQueryType;
            }
        };
    }

    public interface WhereQueryField {
        public WhereQueryType addField(String col);
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
