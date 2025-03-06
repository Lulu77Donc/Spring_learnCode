package com.ljx.spring6.tx.service;

public interface CheckoutService {

    //买多本书方法
    void checkout(Integer[] bookIds,Integer userId);
}
