package com.module.crud.framework.core;

import java.util.stream.Stream;

public interface CrudProviderInterface {
    public <E> String initialize(E data);
    public Stream<CrudProviderColumn> columns();
    public Stream<CrudProviderJoin> joins();
    public boolean isJoin();
    public Stream<String> expandWhere();
    public String run();
}
