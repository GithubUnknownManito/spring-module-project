package com.module.crud;

import com.module.crud.entity.testEntity;
import com.module.crud.framework.provider.CrudFindByIdProvider;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)//spring运行器
@TestPropertySource("classpath:application.properties")
public class Test1 {
    @Test
    public void emd(){
        testEntity test = new testEntity();
        test.setId("111");
        CrudFindByIdProvider findByIdProvider = new CrudFindByIdProvider();
        System.out.println(findByIdProvider.initialize(test));
    }
}
