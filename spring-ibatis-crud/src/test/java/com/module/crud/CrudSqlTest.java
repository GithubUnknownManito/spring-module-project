package com.module.crud;

import com.module.crud.framework.provider.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)//spring运行器
@TestPropertySource("classpath:application.yml")
public class CrudSqlTest {
    @Test
    public void emd(){
        testEntity test = new testEntity();
        test.setId("111");

        System.out.println("========================================");
        System.out.println("CrudFindByIdProvider====================");

        CrudFindByIdProvider findByIdProvider = new CrudFindByIdProvider();
        System.out.println(findByIdProvider.initialize(test));

        System.out.println("========================================");
        System.out.println("CrudFindByIdProvider====================");

        CrudFindListProvider findListProvider = new CrudFindListProvider();
        System.out.println(findListProvider.initialize(test));

        System.out.println("========================================");
        System.out.println("CrudFindProvider========================");

        CrudFindProvider findProvider = new CrudFindProvider();
        System.out.println(findProvider.initialize(test));

        System.out.println("========================================");
        System.out.println("CrudInsertProvider======================");

        CrudInsertProvider insertProvider = new CrudInsertProvider();
        System.out.println(insertProvider.initialize(test));

        System.out.println("========================================");
        System.out.println("CrudUpdateProvider======================");

        CrudUpdateProvider updateProvider = new CrudUpdateProvider();
        System.out.println(updateProvider.initialize(test));

        System.out.println("========================================");
        System.out.println("CrudDeleteProvider======================");

        CrudDeleteProvider deleteProvider =new CrudDeleteProvider();
        System.out.println(deleteProvider.initialize(test));

    }
}
