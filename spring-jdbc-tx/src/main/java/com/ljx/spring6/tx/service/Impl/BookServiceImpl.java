package com.ljx.spring6.tx.service.Impl;

import com.ljx.spring6.tx.dao.BookDao;
import com.ljx.spring6.tx.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//@Transactional(propagation = Propagation.REQUIRED)//一个事务运行
@Transactional(propagation = Propagation.REQUIRES_NEW)//开启一个新事务
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public void buyBook(Integer bookId, Integer userId) {
        //根据图书id查询图书价格
        Integer price = bookDao.getBookPriceById(bookId);

        //更新图书表库存数量-1
        bookDao.updateStock(bookId);

        //更新用户表用户余额-图书价格
        bookDao.updateUserBalance(userId,price);

    }
}
