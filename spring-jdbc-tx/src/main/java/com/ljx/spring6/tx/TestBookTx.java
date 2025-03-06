package com.ljx.spring6.tx;

import com.ljx.spring6.tx.controller.BookController;
import com.ljx.spring6.tx.service.CheckoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = "classpath:bean.xml")
public class TestBookTx {

    /*@Autowired
    private BookController bookController;*/

    @Autowired
    private CheckoutService checkoutService;

    @Test
    public void testByBook(){
        Integer[] bookIds = {1,2};
        Integer userId = 1;
        checkoutService.checkout(bookIds,userId);
    }
}
