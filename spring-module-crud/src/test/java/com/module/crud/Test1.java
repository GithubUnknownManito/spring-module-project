package com.module.crud;

import com.module.crud.sql.CrudWhere;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)//spring运行器
@TestPropertySource("classpath:application.properties")
public class Test1 {
    @Test
    public void emd(){
        System.out.println(String.format("\\% %s", 1, 2));
    }
}
