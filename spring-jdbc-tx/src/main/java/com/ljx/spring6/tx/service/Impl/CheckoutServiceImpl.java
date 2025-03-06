package com.ljx.spring6.tx.service.Impl;

import com.ljx.spring6.tx.service.BookService;
import com.ljx.spring6.tx.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CheckoutServiceImpl implements CheckoutService {

    //注入
    @Autowired
    private BookService bookService;

    @Override
    public void checkout(Integer[] bookIds, Integer userId) {
        for (Integer bookId : bookIds) {
            //调用seivice方法
            bookService.buyBook(bookId,userId);
        }
    }
}
