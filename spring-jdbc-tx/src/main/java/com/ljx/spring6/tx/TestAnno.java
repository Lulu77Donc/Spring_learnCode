package com.ljx.spring6.tx;

import com.ljx.spring6.tx.config.SpringConfig;
import com.ljx.spring6.tx.controller.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestAnno {

    @Test
    public void testTxAllAnnoatation(){
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(SpringConfig.class);
        BookController accountService = applicationContext.getBean(BookController.class);
        Integer[] bookIds = {1,2};
        Integer userId = 1;
        accountService.checkout(bookIds,userId);
    }
}
